package ServerUS;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;

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

    private static final String URL_UserStoryDB = "jdbc:mysql://localhost:3307/userstorydb";

    public static String REG_NAME ="";

    public static Connection connection_icescrum = null;
    public static Connection connection_userstorydb = null;

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
    public synchronized void insertDDL_userStory(String DDL_userStory) throws RemoteException, SQLException {
        connection_userstorydb = DriverManager.getConnection(URL_UserStoryDB,user,password);
        Statement statement_UserStoryDB = connection_userstorydb.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement_UserStoryDB.executeUpdate(DDL_userStory); // insert the data
    }

    @Override
    public  synchronized void loadUserStory_DB() throws RemoteException {


    }

    @Override
    public  synchronized void searchByTag(String a) throws RemoteException, SQLException {


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

    public boolean check_db(StoryBuilder a) throws RemoteException, SQLException {
        String checkQuery =  "select exists (select * from filename where filename_id ='"+a.getId()+"')";
        connection_icescrum = DriverManager.getConnection(URL_UserStoryDB,user,password);
        Statement statement = connection_userstorydb.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(checkQuery);
        ResultSetMetaData resultSetMetaData =resultSet.getMetaData();

        return resultSetMetaData.isSigned(0);
    }

    @Override
    public synchronized ArrayList<StoryBuilder> serachAllUserStoryfromDatabase_Icescrum() throws RemoteException, SQLException {
        String igi = "" +
                "Select is_story.id,is_project.pkey, is_user.username, is_story.name, is_story.description, is_story.date_created\n" +
                "from is_project ,is_user, is_story\n" +
                "where is_project.id = is_story.backlog_id and is_user.id = is_story.creator_id";
        connection_icescrum = DriverManager.getConnection(URL_Icescrum, user, password);
        Statement statement_icescrum = connection_icescrum.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet_1 = statement_icescrum.executeQuery(igi);

        ResultSetMetaData rsmd = resultSet_1.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        ArrayList<StoryBuilder> arrayList = new ArrayList<>();

        while(resultSet_1.next()){
            String id = resultSet_1.getString("id");
            String pkey = resultSet_1.getString("pkey");
            String username = resultSet_1.getString("username");
            String name = resultSet_1.getString("name");
            String description = resultSet_1.getString("description");
            String date_created = resultSet_1.getString("date_created");
            arrayList.add(new StoryBuilder(
                    id,name, description,username,pkey,date_created));
        }
      for(int i = 0;i<arrayList.size();i++){
          arrayList.get(i).print();
      }
        // Arrylist<String> [pkey,nome,descrizione,data_creazione]
        return arrayList;
    }

    /**
     * inserisci un comando ddl come parametro e lo eseguisce
     * riportando un RestulSet da esaminare
     *
     * questa funzione è generica per qualsiasi tipo di funzionalità
     * @param ddl
     * @return ResultSet
     * @throws RemoteException
     * @throws SQLException
     */

    @Override
    public ResultSet check_db_resultSet(String ddl) throws RemoteException, SQLException {
        connection_userstorydb = DriverManager.getConnection(URL_UserStoryDB,user,password);
        Statement statement =connection_userstorydb.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(ddl);
        return resultSet;
    }
}
