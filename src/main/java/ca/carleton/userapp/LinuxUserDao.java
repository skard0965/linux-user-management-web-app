package ca.carleton.userapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadi on 08/12/16.
 */
public class LinuxUserDao {

    public Iterable<LinuxUser> getAllLinuxUsers() {
        List<LinuxUser> linuxUsers = new ArrayList<>();
        for (String user : SystemUtil.getListOfUsers())
            linuxUsers.add(new LinuxUser(user, SystemUtil.getUserGroup(user)));
        return linuxUsers;
    }


    /**
     * Add User method
     *
     * @param username
     * @param groupsStr
     * @return Returns false if it failed.
     */
    public String addUser(String username, String groupsStr) {
        String command = "";
        if (groupsStr.isEmpty()) {
            command = "sudo useradd " + username;
        } else {
            command = "sudo useradd -G " + groupsStr + " " + username;
        }
        return SystemUtil.runCommand(command);
    }

    public String removeUser(String username) {
        String command = "sudo userdel " + username;
        return SystemUtil.runCommand(command);
    }


}
