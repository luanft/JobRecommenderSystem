package uit.se.recsys.Controller;

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
		binder.setAllowedFields(new String[] { "email", "password", "userName" });
	}

	@RequestMapping(value = { "/dang-nhap", "/dang-xuat" }, method = RequestMethod.GET)
	public String init(Model model) {
		model.addAttribute("user", new UserBean());
		return "login";
	}

	@RequestMapping(value = "/dang-nhap", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("user") UserBean user, BindingResult result, HttpSession session) {
		ModelAndView model = new ModelAndView();
		UserBean validatedUser = userService.validateLogin(user, result);
		if (validatedUser != null) {
			session.setMaxInactiveInterval(60 * 60);
			user.setUserName(validatedUser.getUserName());
			user.setPassword("");
			model.setViewName("redirect:/");
		} else {
			model.setViewName("login");
		}
		model.addObject("user", user);
		return model;
	}

	// @RequestMapping(value="/dang-xuat", method=RequestMethod.GET)
	// public String logout(@ModelAttribute("user") UserBean user, HttpSession
	// session){
	// user = new UserBean();
	//
	// session.removeAttribute("user");
	// return "login";
	// }
}
