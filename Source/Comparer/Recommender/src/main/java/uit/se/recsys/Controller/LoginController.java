package uit.se.recsys.Controller;

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
import org.springframework.web.servlet.ModelAndView;

import uit.se.recsys.Bean.UserBean;
import uit.se.recsys.Service.UserService;

@Controller
@SessionAttributes("user")
public class LoginController {

	@Autowired
	UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "email", "password" });
	}

	@RequestMapping(value = "/dang-nhap", method = RequestMethod.GET)
	public String init(Model model) {
		model.addAttribute("user", new UserBean());
		return "login";
	}

	@RequestMapping(value = "/dang-nhap", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("user") UserBean user, BindingResult result) {
		ModelAndView model = new ModelAndView();
		model.addObject("user", user);
		if (userService.validateLogin(user, result))
//			model.setViewName("redirect:/");
			model.setViewName("login");
		else {
			model.setViewName("login");
		}
		return model;
	}
}
