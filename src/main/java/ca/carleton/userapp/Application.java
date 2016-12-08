package ca.carleton.userapp;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by shadi on 2016-09-28.
 */
public class Application {
    public static void main(String[] args) {
        String projectDir = System.getProperty("user.dir");
        String staticDir = "/src/main/resources/public";
        staticFiles.externalLocation(projectDir + staticDir);

        port(8888);
        String cmd = runCommandAsHtml("cut -d : -f 1 /etc/passwd");
        get("/list", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("list", cmd);
            return new ModelAndView(model, "list.vm");

        }, new VelocityTemplateEngine());

    }

    private static String runCommand(String command) {
        return runCommand(command, "\n");
    }

    private static String runCommandAsHtml(String command) {
        return runCommand(command, "<br />");
    }

    private static String runCommand(String command, String newLine) {
        String result = "";
        String s;

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
