package uit.se.recsys.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import uit.se.recsys.Algorithm.CF.CollaborativeFiltering;
import uit.se.recsys.Algorithm.CF.SimilarityMeasure;
import uit.se.recsys.Algorithm.CF.TypeOfNeighborhood;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/", "/trang-chu"}, method = RequestMethod.GET)
	public String home(Model model) {

		return "home";
	}

	@RequestMapping(value = "/excute", method = RequestMethod.POST)
	public ModelAndView excute(@RequestParam("file") MultipartFile[] files,
			@RequestParam("algorithm") String algorithm) {
		String message = "";
		List<String> datasets = new ArrayList<String>();
		boolean uploadStatus = false;
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
						datasets.add(serverFile.getAbsolutePath());
						BufferedOutputStream ouputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
						ouputStream.write(bytes);
						ouputStream.close();

						message = "File đã upload: " + serverFile.getAbsolutePath();
						uploadStatus = true;
					} catch (IOException e) {
						message = "Lỗi upload file: " + e.getMessage();
						uploadStatus = false;
						e.printStackTrace();
					}
				} else {
					uploadStatus = false;
					message = "Lỗi upload file: File rỗng";
				}
			}
		}
		List<RecommendedItem> recommendedItems = null;
		if (uploadStatus) {
			switch (algorithm) {
			case "cf":
				try {					
					CollaborativeFiltering cf = new CollaborativeFiltering(datasets.get(0).replace("\"", ""));
					recommendedItems = cf.UserBase(SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY, TypeOfNeighborhood.NEARESTNUSER, 5, 1, 5);
					for(RecommendedItem ri : recommendedItems){
						System.out.println(ri.toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TasteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "cb":

				break;
			case "hb":

				break;

			default:
				break;
			}
		}
		ModelAndView md = new ModelAndView("home");
		md.addObject("noti", message);
		md.addObject("recommendedItems", recommendedItems);
		return md;
	}
}
