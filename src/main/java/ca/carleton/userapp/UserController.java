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
		String addUserResult = linuxUserDao.addUser(request.queryParams("username"), stringArrayToString(request.queryParamsValues("groups")));
		if (!addUserResult.isEmpty()) {
			model.put("addUserWarning", addUserResult);
			return ViewUtil.render(request, model, "add-user.vm");
		}
		model.put("addUserSucceeded", "User " + request.queryParams("username") + " created successfully.");
		return ViewUtil.render(request, model, "add-user.vm");
	};


	public static Route serveRemoveUserPage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		model.put("users", linuxUserDao.getListOfUsers());
		return ViewUtil.render(request, model, "remove-user.vm");
	};

	public static Route handleRemoveUserPost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		model.put("users", linuxUserDao.getListOfUsers());
		final String username = request.queryParams("username");
		String removeUserResult = linuxUserDao.removeUser(username);
		if (!removeUserResult.isEmpty()) {
			model.put("removeUserWarning", removeUserResult);
			return ViewUtil.render(request, model, "remove-user.vm");
		}
		model.put("removeUserSucceeded", "User " + username + " successfully removed.");
		return ViewUtil.render(request, model, "remove-user.vm");
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
