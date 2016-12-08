package ca.carleton.userapp;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class IndexController {
	public static Route serveIndexPage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		return ViewUtil.render(request, model, "index.vm");
	};
}
