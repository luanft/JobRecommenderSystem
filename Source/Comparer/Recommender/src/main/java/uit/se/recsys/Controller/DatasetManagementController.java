package uit.se.recsys.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DatasetManagementController {
	@RequestMapping(value="/quan-ly-dataset", method=RequestMethod.GET)
	public String init(){
		return "datasetManagement";
	}
}
