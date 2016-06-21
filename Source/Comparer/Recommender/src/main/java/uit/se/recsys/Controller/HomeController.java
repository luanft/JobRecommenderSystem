package uit.se.recsys.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import uit.se.recsys.Bean.UserBean;
import uit.se.recsys.Service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
//@SessionAttributes(value="user")
public class HomeController {

	@Autowired
	UserService userService;

	@RequestMapping(value = {"/","/trang-chu" }, method = RequestMethod.GET)
	public String home(Model model) {		
		return "home";
	}

	
}
