package uit.se.recsys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uit.se.recsys.bean.TaskBean;
import uit.se.recsys.bo.TaskBO;
import uit.se.recsys.utils.DatasetUtil;
import uit.se.recsys.utils.SecurityUtil;

@Controller
public class ExperimentResult {

    @Autowired
    TaskBO taskBO;

    @RequestMapping(value = "/ket-qua", method = RequestMethod.GET)
    public String init(HttpSession session, @RequestParam("taskid") int taskId,
		       Model model) {

	/* Check log in */
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	}

	/* Variables */
	TaskBean task = taskBO.getTaskById(taskId);
	String dirPath = System.getProperty("catalina.home") + File.separator
			+ "data" + File.separator
			+ SecurityUtil.getInstance().getUserId()
			+ File.separator + task.getDataset() + "\\output";
	String fileNamePrefix = task.getAlgorithm().substring(0, 2);

	/* Binding task into view */
	model.addAttribute("task", task);

	/* Biding result into view */
	model.addAttribute("recommendedItems",
			DatasetUtil.getInstance().getRecommendedItems(dirPath
					+ File.separator + getFullFileName(
							fileNamePrefix)));

	return "experimentResult";
    }

    /**
     * Function get full file name from file name prefix
     * 
     * @param fileNamePrefix
     *            {@link String}: file name prefix
     * @return fileName {@link String}: full file name
     */
    private String getFullFileName(String fileNamePrefix) {
	switch (fileNamePrefix) {
	case "cf":
	    return "CF_REC_ITEMS.txt";
	case "cb":
	    return "CB_REC_ITEMS.txt";
	case "hb":
	    return "HB_REC_ITEMS.txt";
	default:
	    return "FILE NOT FOUND";
	}
    }

    @RequestMapping(value = "/ket-qua.tai-ve", method = RequestMethod.GET)
    public String download(HttpServletResponse response, HttpSession session,
			   @RequestParam("task") int taskId) {

	/* Check log in */
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	}

	/* Find file location */
	TaskBean task = taskBO.getTaskById(taskId);
	String dirPath = System.getProperty("catalina.home") + File.separator
			+ "data" + File.separator
			+ SecurityUtil.getInstance().getUserId()
			+ File.separator + task.getDataset() + "\\output\\";
	String fileNamePrefix = task.getAlgorithm().substring(0, 2);

	File downloadFile = new File(dirPath + getFullFileName(fileNamePrefix));
	try {
	    FileInputStream inputStream = new FileInputStream(downloadFile);

	    /* Set content attribute for response */
	    response.setContentType("application/octec-stream");
	    response.setContentLength((int) downloadFile.length());

	    /* Set header for response */
	    String headerKey = "Content-Disposition";
	    String headerValue = String.format("attachment; filename=\"%s\"",
			    downloadFile.getName());
	    response.setHeader(headerKey, headerValue);

	    /* Get output stream of the response */
	    OutputStream outputStream = response.getOutputStream();

	    /* Write file to output */
	    byte[] buffer = new byte[4069];
	    int byteRead = -1;
	    while ((byteRead = inputStream.read(buffer)) != -1) {
		outputStream.write(buffer, 0, byteRead);
	    }
	    inputStream.close();
	    outputStream.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return "experimentResult";
    }
}
