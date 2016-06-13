package uit.se.recsys.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {

		return "home";
	}

	@RequestMapping(value = "/excute", method = RequestMethod.POST)
	public ModelAndView excute(@RequestParam("file") MultipartFile[] files) {
		String message = "";
		if (files.length != 0) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					try {
						byte bytes[] = file.getBytes();

						// create directory to store file
						String rootPath = System.getProperty("catalina.home");
						File dir = new File(rootPath + File.separator + "Uploads");
						if (!dir.exists()) {
							dir.mkdirs();
						}

						// create file on server
						File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
						BufferedOutputStream ouputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
						ouputStream.write(bytes);
						ouputStream.close();

						message = "File đã upload: " + serverFile.getAbsolutePath();
					} catch (IOException e) {
						message = "Lỗi upload file: " + e.getMessage();
						e.printStackTrace();
					}
				} else {
					message = "Lỗi upload file: File rỗng";
				}
			}
		}
		ModelAndView md = new ModelAndView("home");
		md.addObject("noti", message);
		return md;
	}
}
