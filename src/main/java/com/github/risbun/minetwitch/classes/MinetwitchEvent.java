package com.github.risbun.minetwitch.classes;

public class MinetwitchEvent {

    String key;
    String command;

    public MinetwitchEvent(String key, String command) {
        this.key = key;
        this.command = command;
    }

    public String getKey(){
        return key;
    }

    public String GetCommand(){
        return command;
    }
}
