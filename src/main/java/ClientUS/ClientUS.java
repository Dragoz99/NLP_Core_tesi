package ClientUS;

import ServerUS.UserInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientUS implements RemoteListener {


    static UserInterface stub;
    static Registry reg;
    static String host;

    ClientUS(){

    }
    public ClientUS(int port) throws Exception{
        ClientUS clientUS = new ClientUS();
        try{
            reg = LocateRegistry.getRegistry();
            stub = (UserInterface) reg.lookup("");
            //esecuzione codice
            stub.testRMI();

            RemoteListener rifRemoto = (RemoteListener) UnicastRemoteObject.exportObject(clientUS,port);

            // avvio interfaccia grafica


        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Client exc: "+e);
        }
    }

    public static void main(String[] args) throws Exception {
        new ClientUS(1100);
    }
}
