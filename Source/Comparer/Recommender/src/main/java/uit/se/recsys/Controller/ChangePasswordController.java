package uit.se.recsys.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uit.se.recsys.Bean.UserBean;

@Controller
public class ChangePasswordController {

	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.setAllowedFields(new String[]{
				"email", "password", "rpassword"
		});
	}
	
	@RequestMapping(value="/quen-mat-khau", method = RequestMethod.GET)
	public String init(Model model){
		model.addAttribute("user", new UserBean());
		return "forgotPassword";
	}
}
