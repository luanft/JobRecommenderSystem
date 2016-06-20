package uit.se.recsys.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uit.se.recsys.Service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@RequestMapping(value = { "/", "/trang-chu" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	
}
