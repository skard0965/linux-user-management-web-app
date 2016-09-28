package ca.carleton.userapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static spark.Spark.*;

/**
 * Created by shadi on 2016-09-28.
 */
public class Application {
    public static void main(String[] args) {
      port(8888);
      String cmd = runCommandAsHtml("cut -d : -f 1 /etc/passwd");
      get("/", (req, res) -> cmd);

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

      // run the Unix "ps -ef" command
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
    }
    catch (IOException e) {
      result += e.getMessage();
    } finally {
      return result;
    }
  }
}
