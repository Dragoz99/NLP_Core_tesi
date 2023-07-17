package ServerUS;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserInterface extends Remote {
    void insertDDL_userStory(String DDL_userStory) throws RemoteException, SQLException;
    void loadUserStory_DB() throws RemoteException;
    void searchByTag(String a) throws RemoteException, SQLException;
    void downloardUserStory() throws RemoteException;
    void viewUserStoryPNG() throws RemoteException;
    void testRMI() throws RemoteException;

    ArrayList<StoryBuilder> serachAllUserStoryfromDatabase_Icescrum() throws RemoteException, SQLException;

    //check database
    ResultSet check_db_resultSet(String ddl) throws RemoteException,SQLException;
    boolean check_db(StoryBuilder a) throws RemoteException, SQLException;







}
