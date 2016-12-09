package ca.carleton.userapp;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static ca.carleton.userapp.Application.groupDao;

/**
 * Created by shadi on 09/12/16.
 */
public class GroupController {

    public static Route serveAddGroupPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, "add-group.vm");
    };

    public static Route handleAddGroupPost = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        final String group = request.queryParams("groupname");
        String addGroupResult = groupDao.addGroup(group);
        if (!addGroupResult.isEmpty()) {
            model.put("addGroupWarning", addGroupResult);
            return ViewUtil.render(request, model, "add-group.vm");
        }
        model.put("addGroupSucceeded", "Group " + group + " created successfully.");
        return ViewUtil.render(request, model, "add-group.vm");
    };


    public static Route serveRemoveGroupPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("groups", SystemUtil.getListOfGroups());
        return ViewUtil.render(request, model, "remove-group.vm");
    };

    public static Route handleRemoveGroupPost = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("groups", SystemUtil.getListOfGroups());
        final String group = request.queryParams("group");
        String removeGroupResult = groupDao.removeGroup(group);
        if (!removeGroupResult.isEmpty()) {
            model.put("removeGroupWarning", removeGroupResult);
            return ViewUtil.render(request, model, "remove-group.vm");
        }
        model.put("removeGroupSucceeded", "Group " + group + " successfully removed.");
        return ViewUtil.render(request, model, "remove-group.vm");
    };
    

    public static Route serveGroupsPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("groups", SystemUtil.getListOfGroups());
        return ViewUtil.render(request, model, "show-groups.vm");
    };

    public static Route serveGroupEditPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("groups", SystemUtil.getListOfGroups());
        return ViewUtil.render(request, model, "edit-group.vm");
    };

    public static Route handleEditGroupPost = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        final String group = request.queryParams("group");
        model.put("group", group);
        return ViewUtil.render(request, model, "edit-group-name.vm");
    };

    public static Route handleEditGroupNamePost = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        final String group = request.queryParams("group");
        final String name = request.queryParams("name");

        model.put("group", group);
        String editGroupNameResult = groupDao.editGroupName(group, name);
        if (!editGroupNameResult.isEmpty()) {
            model.put("editGroupNameWarning", editGroupNameResult);
            return ViewUtil.render(request, model, "edit-group-name.vm");
        }
        model.put("editGroupNameSucceeded", "Group \'" + group + "\' has been successfully renamed into \'" + name + "\'");
        return ViewUtil.render(request, model, "edit-group-name.vm");
    };
}
