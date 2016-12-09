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
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		model.put("groups", linuxUserDao.getListOfGroups());
		if (!linuxUserDao.addUser(request.queryParams("username"), stringArrayToString(request.queryParamsValues("groups")))) {
			model.put("addUserFailed", true);
			return ViewUtil.render(request, model, "add-user.vm");
		}
		model.put("addUserSucceeded", "User " + request.queryParams("username") + " created successfully.");
		return ViewUtil.render(request, model, "add-user.vm");
	};

	private static String stringArrayToString(String[] groupsArray) {
		String groupsStr = "";
		for (int i = 0; i < groupsArray.length; i++) {
			groupsStr = groupsStr + groupsArray[i] + ",";
		}
		groupsStr = groupsStr.substring(0, groupsStr.length() - 1);
		return groupsStr;
	}

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
