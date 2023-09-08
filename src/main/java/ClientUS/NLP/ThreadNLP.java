package ClientUS.NLP;

import ClientUS.RemoteListener;
import ServerUS.StoryBuilder;
import ServerUS.UserInterface;

import java.io.IOException;
import java.sql.SQLException;

public class ThreadNLP implements Runnable, RemoteListener {

    StoryBuilder storyBuilder;
    UserInterface stub;
    public ThreadNLP(StoryBuilder storyBuilder, UserInterface stub){
        this.stub = stub;
        this.storyBuilder = storyBuilder;
    }



    @Override
    public void run() {
        try {
            new NLP(storyBuilder,stub);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
