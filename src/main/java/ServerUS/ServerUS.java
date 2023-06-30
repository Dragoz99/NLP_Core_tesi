package ServerUS;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class ServerUS extends UnicastRemoteObject implements UserInterface{


    public static String REG_NAME ="";
    public ServerUS() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws SQLException {
        try{
            ServerUS obj = new ServerUS();
            Registry reg = LocateRegistry.createRegistry(1099);

            reg.rebind(REG_NAME,obj);
            System.out.println("Server ok");
        }catch (Exception e){
            System.out.println("Server excepion: "+e);
            e.printStackTrace();
        }
    }
    @Override
    public void insertUserStory() throws RemoteException {

    }

    @Override
    public void loadUserStory_DB() throws RemoteException {


    }

    @Override
    public void searchByTag(String a) throws RemoteException {

    }

    @Override
    public void downloardUserStory() throws RemoteException {

    }

    @Override
    public void viewUserStoryPNG() throws RemoteException {

    }

    @Override
    public void testRMI() throws RemoteException {
        System.out.println("Prova RMI TEST");
    }
}
