package com.github.risbun.minetwitch;

public class EventContainer {

    String key;
    String command;

    public EventContainer(String key, String command) {
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
