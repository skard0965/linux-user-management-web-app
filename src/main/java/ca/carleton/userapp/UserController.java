package ca.carleton.userapp;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class UserController {
	public static boolean authenticate(String username, String password) {
		System.out.println("username = " + username);
		System.out.println("password = " + password);
		if (password.equals("password")) {
			return true;
		}
		return false;
	}

	public static Route serveUsersPage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		return ViewUtil.render(request, model, "user.vm");
	};
}
