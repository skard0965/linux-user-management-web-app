package ca.carleton.userapp;

/**
 * Created by shadi on 08/12/16.
 */
public class LinuxUser {
    String name;
    String group;

    public LinuxUser(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
