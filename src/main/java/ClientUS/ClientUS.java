package ClientUS;

import ClientUS.GUI.SchermataPrincipaleV2;
import ServerUS.UserInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
            //RemoteListener rifRemoto = (RemoteListener) UnicastRemoteObject.exportObject(clientUS,port);
            //esecuzione codice
            stub.testRMI();
            // CONTINUARE CON L'RMI
            new SchermataPrincipaleV2(stub);
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
