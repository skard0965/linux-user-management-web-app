package ca.carleton.userapp;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

import static ca.carleton.userapp.Application.linuxUserDao;

public class UserController {
	public static boolean authenticate(String username, String password) {
		if (password.equals("password")) {
			return true;
		}
		return false;
	}

	public static Route serveUsersPage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		model.put("users", linuxUserDao.getAllLinuxUsers());
		return ViewUtil.render(request, model, "user.vm");
	};


}
