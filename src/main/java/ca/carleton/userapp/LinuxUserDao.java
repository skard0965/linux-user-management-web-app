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
        List<String> users = Arrays.asList(runCommand("cut -d : -f 1 /etc/passwd").split("\n"));
        for (String user : users) {
            /**
             * We need to add in /etc/sudoers https://gokceng.wordpress.com/2011/12/29/running-shell-commands-with-java-as-root/
             * # for user
             * YOUR_USER_NAME ALL= NOPASSWD: ALL
             * # for group
             * YOUR_GROUP_NAME ALL= NOPASSWD: ALL
             *
             */
            linuxUsers.add(new LinuxUser(user, runCommand("sudo id -gn " + user)));
        }
        return linuxUsers;
    }

    public List<String> getListOfGroups() {
        return Arrays.asList(runCommand("cut -d : -f 1 /etc/group").split("\n"));
    }

    public void addUser(String username, List<String> groups) {
        String groupsStr = "";
        for (String group : groups) {
            groupsStr = group + ",";
        }
       groupsStr = groupsStr.substring(0, groupsStr.length() - 2);
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
