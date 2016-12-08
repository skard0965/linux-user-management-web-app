package ca.carleton.userapp;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by shadi on 2016-09-28.
 */
public class Application {

    public static void main(String[] args) {

        port(8888);

        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        before("*", Filters.addTrailingSlashes);

        String cmd = ListUsersController.runCommandAsHtml("cut -d : -f 1 /etc/passwd");

        get("/list/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("list", cmd);
            return new ModelAndView(model, "list.vm");

        }, new VelocityTemplateEngine());

        get("/", IndexController.serveIndexPage);

        get("/login/", LoginController.serveLoginPage);
        post("/login/", LoginController.handleLoginPost);

        post("/logout/", LoginController.handleLogoutPost);

        get("/user/", UserController.serveUsersPage);

        get("*", ViewUtil.notFound);

    }


}
