package ca.carleton.userapp;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by shadi on 2016-09-28.
 */
public class Application {

    public static LinuxUserDao linuxUserDao;

    public static void main(String[] args) {

        linuxUserDao = new LinuxUserDao();

        port(8888);

        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        before("*", Filters.addTrailingSlashes);

        get("/", IndexController.serveIndexPage);

        get("/login/", LoginController.serveLoginPage);
        post("/login/", LoginController.handleLoginPost);

        post("/logout/", LoginController.handleLogoutPost);

        get("/user/", UserController.serveUsersPage);

        get("/add-user/", UserController.serveAddUserPage);
        post("/add-user/", UserController.handleAddUserPost);

        get("/remove-user/", UserController.serveRemoveUserPage);
        post("/remove-user/", UserController.handleRemoveUserPost);


        get("*", ViewUtil.notFound);

    }


}
