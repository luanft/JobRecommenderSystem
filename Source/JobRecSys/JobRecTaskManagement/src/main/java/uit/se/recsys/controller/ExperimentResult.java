package uit.se.recsys.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uit.se.recsys.bean.UserBean;
import uit.se.recsys.utils.SecurityUtil;

@Controller
public class ExperimentResult {

    @RequestMapping(value = "/ket-qua", method = RequestMethod.GET)
    public String init(HttpSession session) {
	if (!SecurityUtil.getInstance().haveUserLoggedIn(session)) {
	    return "redirect:/dang-nhap";
	}
	return "experimentResult";
    }
}
