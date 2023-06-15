package ServerUS;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserInterface extends Remote {
    void insertUserStory() throws RemoteException;
    void loadUserStory_DB() throws RemoteException;
    void searchByTag(String a) throws RemoteException;
    void downloardUserStory() throws RemoteException;
    void viewUserStoryPNG() throws RemoteException;


}
