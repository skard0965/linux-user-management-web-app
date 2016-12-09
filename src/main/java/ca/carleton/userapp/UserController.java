package ca.carleton.userapp;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

import static ca.carleton.userapp.Application.linuxUserDao;

public class UserController {
	public static Route serveAddUserPage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		model.put("groups", linuxUserDao.getListOfGroups());
		return ViewUtil.render(request, model, "add-user.vm");
	};
	public static Route handleAddUserPost = (Request request, Response response) -> {
		String username = request.queryParams("username");
		String[] groupsArray = request.queryParamsValues("groups");

		if (!username.isEmpty() && groupsArray != null) {
			List<String> groups = Arrays.asList(groupsArray);
			linuxUserDao.addUser(username, groups);
		}

//		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		model.put("groups", linuxUserDao.getListOfGroups());

//
//		if (!addUser(getQueryUsername(request), getQueryGroups(request))) {
//			model.put("addUserFailed", true);
//			return ViewUtil.render(request, model, "add-user");
//		}
//
//		model.put("addUserSucceeded", "User " + getQueryUsername(request) + " created successfully.");
//
//		if (getQueryLoginRedirect(request) != null) {
//			System.out.println("redirect!");
//			response.redirect(getQueryLoginRedirect(request));
//		}
		return ViewUtil.render(request, model, "add-user.vm");
	};

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
