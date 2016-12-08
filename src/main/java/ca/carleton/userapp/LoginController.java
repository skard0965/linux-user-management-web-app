package ca.carleton.userapp;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static ca.carleton.userapp.RequestUtil.*;

public class LoginController {

	public static Route serveLoginPage = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		model.put("loggedOut", removeSessionAttrLoggedOut(request));
		model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
		return ViewUtil.render(request, model, "login.vm");
	};

	public static Route handleLoginPost = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		if (!UserController.authenticate(getQueryUsername(request), getQueryPassword(request))) {
			model.put("authenticationFailed", true);
			return ViewUtil.render(request, model, "login.vm");
		}
		model.put("authenticationSucceeded", true);
		request.session().attribute("currentUser", getQueryUsername(request));
		if (getQueryLoginRedirect(request) != null) {
			System.out.println("redirect!");
			response.redirect(getQueryLoginRedirect(request));
		}
		return ViewUtil.render(request, model, "index.vm");
	};

	public static Route handleLogoutPost = (Request request, Response response) -> {
		request.session().removeAttribute("currentUser");
		request.session().attribute("loggedOut", true);
		response.redirect("/login/");
		return null;
	};


	// The origin of the request (request.pathInfo()) is saved in the session so
	// the user can be redirected back after login
	public static void ensureUserIsLoggedIn(Request request, Response response) {
		if (request.session().attribute("currentUser") == null) {
			request.session().attribute("loginRedirect", request.pathInfo());
			response.redirect("/login/");
		}
	};
}
