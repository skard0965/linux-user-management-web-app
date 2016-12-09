package ca.carleton.userapp;

import com.google.common.collect.ImmutableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shadi on 08/12/16.
 */
public class LinuxUserDao {

    public Iterable<LinuxUser> getAllLinuxUsers() {
        List<LinuxUser> linuxUsers = new ArrayList<>();
        List<String> users = getListOfUsers();
        for (String user : users) {
            /**
             * We need to add in /etc/sudoers https://gokceng.wordpress.com/2011/12/29/running-shell-commands-with-java-as-root/
             * # for user
             * YOUR_USER_NAME ALL= NOPASSWD: ALL
             * # for group
             * YOUR_GROUP_NAME ALL= NOPASSWD: ALL
             *
             */
            linuxUsers.add(new LinuxUser(user, getUserGroup(user)));
        }
        return linuxUsers;
    }

    private String getUserGroup(String user) {
        return runCommand("sudo id -Gn " + user);
    }

    public List<String> getListOfGroups() {
        return Arrays.asList(runCommand("cut -d : -f 1 /etc/group").split("\n"));
    }

    public List<String> getListOfUsers() {
        return Arrays.asList(runCommand("cut -d : -f 1 /etc/passwd").split("\n"));
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
        return runCommand(command);
    }

    public String removeUser(String username) {
        String command = "sudo userdel " + username;
        return runCommand(command);
    }

    private static String runCommand(String command) {
        String result = "";
        String s;
        String newLine = "\n";
        try {
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                result += s + newLine;
            }

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                result += s + newLine;
            }
        } catch (IOException e) {
            result += e.getMessage();
        } finally {
            return result;
        }
    }
}
