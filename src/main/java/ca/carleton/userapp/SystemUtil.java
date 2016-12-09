package ca.carleton.userapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shadi on 09/12/16.
 */
public class SystemUtil {
    public static String runCommand(String command) {
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


    public static String getUserGroup(String user) {
        return runCommand("sudo id -Gn " + user);
    }

    public static List<String> getListOfGroups() {
        return Arrays.asList(runCommand("cut -d : -f 1 /etc/group").split("\n"));
    }

    public static List<String> getListOfUsers() {
        return Arrays.asList(runCommand("cut -d : -f 1 /etc/passwd").split("\n"));
    }
}
