package uit.se.recsys.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import uit.se.recsys.Bean.UserBean;
import uit.se.recsys.DAO.UserDAO;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;

	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(11);

	public void validateRegister(UserBean user, BindingResult error) {
		if (userDAO.getUserByUserName(user.getUserName()) != null)
			error.rejectValue("userName", "duplicate.user.username");
		if (userDAO.getUserByEmail(user.getEmail()) != null)
			error.rejectValue("email", "duplicate.user.email");
		if (!user.getPassword().equals(user.getRpassword()))
			error.rejectValue("password", "error.mismatch.user.password");
	}

	public boolean validateLogin(UserBean user, BindingResult error) {
		UserBean userDB = userDAO.getUserByEmail(user.getEmail());
		if (userDB == null)
			error.rejectValue("email", "error.user.email.notExist");
		else {
			if (!bcrypt.matches(user.getPassword(), userDB.getPassword()))
				error.rejectValue("password", "error.mismatch.user.password");
			else
				return true;
		}
		return false;
	}

	public boolean addUser(UserBean user, BindingResult error) {
		user.setPassword(bcrypt.encode(user.getPassword()));
		if (userDAO.addUser(user)) {
			return true;
		} else {
			error.reject("error.mysql.exception");
			return false;
		}
	}
}