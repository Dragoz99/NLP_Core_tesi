package ServerUS;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.LinkedList;

public class ServerUS extends UnicastRemoteObject implements UserInterface{

    /**
     * Variabili per l'accesso al database
     */
    public static final String NAME_REG = "";          //variabili per accesso a DB

    /**
     * Localhost
     */
    private static String host ="localhost";      //localhost
    private static String port = "3307";        //5432
    private static String user = "root";
    private static String password = "1234";

    private static final String URL_Icescrum = "jdbc:mysql://localhost:3307/icescrum";

    public static String REG_NAME ="";

    public static Connection connection_icescrum = null;
    public ServerUS() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws SQLException {
        try{
            ServerUS obj = new ServerUS();
            System.setProperty("java.rmi.server.hostname", "192.168.1.101");
            Registry reg = LocateRegistry.createRegistry(1099);

            reg.rebind(REG_NAME,obj);
            System.out.println("Server ok");


            //String URL_2 = "jdbc:mysql://localhost:3307/icescrum";//  server NLP URL

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection_icescrum = DriverManager.getConnection(URL_Icescrum, user, password);
                Statement statement_icescrum = connection_icescrum.createStatement();
                ResultSet resultSet_1 = statement_icescrum.executeQuery("select description from is_story");

                while (resultSet_1.next()){
                    System.out.println(resultSet_1.getString(1));
                    System.out.println("-----------");
                }

                connection_icescrum.close();

            }catch (Exception e){

            }
        }catch (Exception e){
            System.out.println("Server excepion: "+e);
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void insertUserStory() throws RemoteException {

    }

    @Override
    public  synchronized void loadUserStory_DB() throws RemoteException {


    }

    @Override
    public  synchronized void searchByTag(String a) throws RemoteException {

    }

    @Override
    public  synchronized void downloardUserStory() throws RemoteException {

    }


    @Override
    public synchronized void viewUserStoryPNG() throws RemoteException {

    }

    @Override
    public void testRMI() throws RemoteException {
        System.out.println("Prova RMI TEST");
    }

    @Override
    public synchronized LinkedList<String> serachAllUserStoryfromDatabase_Icescrum() throws RemoteException, SQLException {

        String igi = "" +
                "Select is_project.pkey, is_user.username, is_story.name, is_story.description, is_story.date_created\n" +
                "from is_project ,is_user, is_story\n" +
                "where is_project.id = is_story.backlog_id and is_user.id = is_story.creator_id";

        connection_icescrum = DriverManager.getConnection(URL_Icescrum, user, password);

        Statement statement_icescrum = connection_icescrum.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet_1 = statement_icescrum.executeQuery(igi);

        ResultSetMetaData rsmd = resultSet_1.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        LinkedList<String> linkedList = new LinkedList<>();


        // Arrylist<String> [pkey,nome,descrizione,data_creazione]
        while (resultSet_1.next()) {
            for (int i = 1; i <= columnsNumber; i++) {

                String columnValue = resultSet_1.getString(i);
                linkedList.add(columnValue);
                //System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }

        for(String i: linkedList){
            System.out.println(i);
        }
        return linkedList;
    }
}
