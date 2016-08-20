package uit.se.recsys.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import uit.se.recsys.bean.TaskBean;
import uit.se.recsys.bo.MetricBO;
import uit.se.recsys.bo.TaskBO;
import uit.se.recsys.bo.UserBO;
import uit.se.recsys.utils.DatasetUtil;
import uit.se.recsys.utils.SecurityUtil;
import uit.se.recsys.utils.StripAccentUtil;

@Controller
@PropertySource("classpath:/config/datasetLocation.properties")
public class EvaluationController {

    @Value("${Dataset.Location}")
    String ROOT_PATH;
    @Value("${JobRecAlgComparer.Location}")
    String jRACLocation;

    @Autowired
    UserBO userService;
    @Autowired
    TaskBO taskBO;
    @Autowired
    MetricBO metricBO;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
	binder.setAllowedFields(new String[] { "taskName", "algorithm",
			"dataset", "testSize" });
    }

    @RequestMapping(value = "/danh-gia-thuat-toan", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {

	/* Check logged in user */
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	}

	bindingData(model);
	return "evaluation";
    }

    @RequestMapping(value = {"danh-gia-thuat-toan" }, method = RequestMethod.POST)
    public String createTask(@ModelAttribute("task") TaskBean task,@RequestParam("config") MultipartFile config,
			     BindingResult result, Model model,
			     HttpSession session) {

	/* Check logged in user */
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	}

	/* Set task info and save it */
	task.setStatus("running");
	task.setType("eval");
	task.setTimeCreate(new Timestamp(new Date().getTime()));
	task.setUserId(SecurityUtil.getInstance().getUserId());
	taskBO.addTask(task);

	/* Save config file */
	String path = ROOT_PATH + task.getUserId() + File.separator
			+ task.getDataset() + File.separator;
	int taskId = taskBO.generateId();
	String taskName = new StripAccentUtil().convert(task.getTaskName().replaceAll(" ", "-"));
	if(!config.isEmpty()){
	    try {
		byte[] cBytes = config.getBytes();		
		File dir = new File(path + "evaluation\\" + taskId + "_" + taskName);
		if(!dir.exists()){
		    dir.mkdirs();
		}
		File fileConfig = new File(dir.getAbsolutePath() + File.separator + "config.properties");
		BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(fileConfig));
		outStream.write(cBytes);
		outStream.close();		
	    } catch (IOException e) {
		e.printStackTrace();
	    }	    
	}
	
	/* execute algorithm*/
	executeAlgorithm(task.getAlgorithm(), config.isEmpty(),
			task.getTestSize(),
			path + "input\\",
			path + "evaluation\\" + taskId + "_" 
			+ taskName + File.separator,
			taskId);

	bindingData(model);
	return "evaluation";
    }

    private void bindingData(Model model) {

	/* Binding new TaskBean and dataset to view */
	model.addAttribute("task", new TaskBean());
	model.addAttribute("datasets", DatasetUtil.getInstance().getDatasets(
			ROOT_PATH + SecurityUtil.getInstance().getUserId()));

	/* Binding list of task to view */
	model.addAttribute("listTask", taskBO.getAllEvaluationTasks());
	/* Binding list of metric to view */
	model.addAttribute("listMetric", metricBO.getAllMetrics());
    }

    private void executeAlgorithm(String algorithm, boolean useConfig, int testSize, String input,
				  String output, int taskId) {
	try {

	    /* Create directory to save output files */
	    File dOut = new File(output);
	    if (!dOut.exists()) {
		dOut.mkdirs();
	    }

	    /* Create command file to execute .jar file */
	    File commandFile = new File(output + "command.bat");
	    if (!commandFile.exists()) {
		commandFile.createNewFile();
	    }
	    FileWriter fw = new FileWriter(commandFile.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write("java -jar " + jRACLocation + " eval " + algorithm + " " + useConfig
			    + " " + testSize + " " + input + " " + output + " "
			    + taskId);
	    bw.write("\n exit");
	    bw.close();
	    fw.close();

	    /* Run command file */
	    Runtime.getRuntime().exec("cmd /c start " + output + "command.bat");

	} catch (IOException e1) {
	    e1.printStackTrace();
	}
    }
}
