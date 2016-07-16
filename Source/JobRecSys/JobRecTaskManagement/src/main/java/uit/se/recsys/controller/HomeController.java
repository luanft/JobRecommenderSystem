package uit.se.recsys.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import uit.se.recsys.bean.TaskBean;
import uit.se.recsys.bean.UserBean;
import uit.se.recsys.bo.TaskBO;
import uit.se.recsys.bo.UserBO;
import uit.se.recsys.utils.DatasetUtil;
import uit.se.recsys.utils.SecurityUtil;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {
    private final String ROOT_PATH = System.getProperty("catalina.home")
		    + File.separator + "data" + File.separator;
    @Autowired
    UserBO userService;
    @Autowired
    TaskBO taskBO;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
	binder.setAllowedFields(
			new String[] { "taskName", "algorithm", "dataset" });
    }

    @RequestMapping(value = { "/", "/trang-chu" }, method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {

	/* Check logged in user */
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	}

	/* Binding new TaskBean and dataset to view */
	model.addAttribute("task", new TaskBean());
	model.addAttribute("datasets", DatasetUtil.getInstance().getDatasets(
			ROOT_PATH + SecurityUtil.getInstance().getUserId()));

	/* Binding list of task to view */
	model.addAttribute("listTask", taskBO.getAllTasks());
	return "home";
    }

    @RequestMapping(value = { "/", "trang-chu" }, method = RequestMethod.POST)
    public String createTask(@ModelAttribute("task") TaskBean task,
			     BindingResult result, Model model,
			     HttpSession session) {

	/* Check logged in user */
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	}

	/* Set task info and save it */
	task.setStatus("running");
	task.setTimeCreate(new Timestamp(new Date().getTime()));
	task.setUserId(SecurityUtil.getInstance().getUserId());
	taskBO.addTask(task);

	/* Execute algorithm */
	String path = ROOT_PATH + task.getUserId() + File.separator
			+ task.getDataset() + File.separator;
	executeAlgorithm(task.getAlgorithm(), path + "input" + File.separator,
			path + "output" + File.separator);

	/* Binding new TaskBean and dataset to view */
	model.addAttribute("task", new TaskBean());
	model.addAttribute("datasets", DatasetUtil.getInstance().getDatasets(
			ROOT_PATH + SecurityUtil.getInstance().getUserId()));

	/* Binding list of task to view */
	model.addAttribute("listTask", taskBO.getAllTasks());
	return "home";
    }

    private void executeAlgorithm(String algorithm, String input,
				  String output) {
	try {

	    /* Create command file to execute .jar file */
	    File commandFile = new File(output + "command.bat");
	    if (!commandFile.exists()) {
		commandFile.createNewFile();
	    }
	    FileWriter fw = new FileWriter(commandFile.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write("java -jar " + System.getProperty("catalina.home")
			    + "\\JobRecAlgComparer.jar " + algorithm + " "
			    + input + " " + output);
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