package uit.se.recsys.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExperimentResult {
	
	@RequestMapping(value="/ket-qua", method=RequestMethod.GET)
	public String init(){		
		return "experimentResult";
	}
}