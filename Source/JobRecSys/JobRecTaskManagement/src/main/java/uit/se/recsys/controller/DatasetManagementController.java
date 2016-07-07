package uit.se.recsys.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import uit.se.recsys.utils.SecurityUtil;

@Controller
public class DatasetManagementController {
    private final String ROOT_PATH = System.getProperty("catalina.home")
		    + File.separator + "data" + File.separator;

    @RequestMapping(value = "/quan-ly-dataset", method = RequestMethod.GET)
    public String init(Model model, HttpSession session) {
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	} else {
	    model.addAttribute("datasets", getDatasets());
	    return "datasetManagement";
	}
    }

    @RequestMapping(value = "/quan-ly-dataset", method = RequestMethod.POST)
    public String uploadDataset(HttpSession session,
				@RequestParam("files") MultipartFile[] files,
				@RequestParam("dataset") String datasetName,
				Model model) {
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	} else {
	    model.addAttribute("message", saveDataset(files, datasetName));
	    model.addAttribute("datasets", getDatasets());
	    return "datasetManagement";
	}
    }

    private String saveDataset(MultipartFile[] files, String datasetName) {
	String message = ""; /* Message to notify the user */

	if (files.length == 3) {

	    /* Create directory to save files */
	    File dir = new File(
			    ROOT_PATH + SecurityUtil.getInstance().getUserId()
					    + File.separator + datasetName
					    + File.separator + "input");
	    if (!dir.exists()) {
		dir.mkdirs();
	    }

	    /* Loop through files and save it */
	    for (int i = 0; i < 3; i++) {
		MultipartFile file = files[i];
		try {
		    byte[] bytes = file.getBytes();
		    String fileName = "Job.txt";
		    if (i == 1) {
			fileName = "CV.txt";
		    } else {
			if (i == 2) {
			    fileName = "Score.txt";
			}
		    }
		    File serverFile = new File(dir.getAbsolutePath()
				    + File.separator + fileName);
		    BufferedOutputStream buffer = new BufferedOutputStream(
				    new FileOutputStream(serverFile));
		    buffer.write(bytes);
		    buffer.close();
		} catch (IOException e) {
		    message = "Lỗi upload file. -- " + e.getMessage();
		    e.printStackTrace();
		}
	    }
	    message = "Upload dataset thành công!";
	} else {
	    message = "Lỗi upload file, vui lòng upload đủ 3 file dataset";
	}
	return message;
    }

    private String[] getDatasets() {
	File dir = new File(ROOT_PATH + SecurityUtil.getInstance().getUserId());
	String[] directories = dir.list(new FilenameFilter() {
	    @Override
	    public boolean accept(File current, String name) {
		return new File(current, name).isDirectory();
	    }
	});
	return directories;
    }
}
