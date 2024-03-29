package ca.carleton.userapp;

import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static ca.carleton.userapp.RequestUtil.getSessionCurrentUser;

public class ViewUtil {

	// Renders a template given a model and a request
	// The request is needed to check the user session for language settings
	// and to see if the user is logged in
	public static String render(Request request, Map<String, Object> model, String templatePath) {
		model.put("currentUser", getSessionCurrentUser(request));
		return strictVelocityEngine().render(new ModelAndView(model, templatePath));
	}

	public static Route notFound = (Request request, Response response) -> {
		response.status(HttpStatus.NOT_FOUND_404);
		return render(request, new HashMap<>(), "/notFound.vm");
	};

	private static VelocityTemplateEngine strictVelocityEngine() {
		VelocityEngine configuredEngine = new VelocityEngine();
		configuredEngine.setProperty("runtime.references.strict", true);
		configuredEngine.setProperty("resource.loader", "class");
		configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		return new VelocityTemplateEngine(configuredEngine);
	}
}