package ca.carleton.userapp;

/**
 * Created by shadi on 09/12/16.
 */
public class GroupDao {
    public String addGroup(String group) {
        String command = "sudo groupadd " + group;
        return SystemUtil.runCommand(command);
    }

    public String removeGroup(String group) {
        String command = "sudo groupdel " + group;
        return SystemUtil.runCommand(command);
    }
}
