package com.example.sunfinder.ServerCommunication;

public class ServerAdministration {

    public void callServer()
    {
        ServerTask task = new ServerTask(new OnTaskFinishedListener() {
            @Override
            public void onTaskFinished() {
                //do something when task is finished
            }
        });
        task.execute(); //enter some params
    }
}
