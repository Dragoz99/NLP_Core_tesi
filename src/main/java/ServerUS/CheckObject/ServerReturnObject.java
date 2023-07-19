package ServerUS.CheckObject;

import ServerUS.StoryBuilder;

import javax.sql.RowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.Serializable;
import java.sql.SQLException;

public class ServerReturnObject implements Serializable {

//    private RowSet rowSet;
    private static RowSet rowSet;
    public ServerReturnObject(StoryBuilder a) throws SQLException {
        this.rowSet = RowSetProvider.newFactory().createJdbcRowSet();
        rowSet.setUrl("jdbc:mysql://localhost:3307/userstorydb");
        rowSet.setUsername("root");
        rowSet.setPassword("1234");
        //rowSet.setCommand("select count(*) from class where class_filename_id = '"+a.getId()+"'");
        //rowSet.execute();
        //System.out.println(rowSet.getMaxRows());
    }
    public void printAllTable() throws SQLException {
        while(rowSet.next()){
                System.out.println(rowSet.getObject(1));
                //System.out.println("row:getString"+ rowSet.getString(i));
                //System.out.println(concatRis);
                System.out.println("getRow() :"+ rowSet.getRow());
        }
    }
    public boolean contin() throws SQLException{
        boolean bool = false;
        while(rowSet.next()){
            Object rowReturn = rowSet.getObject(1);
            if(rowReturn.equals("true")){
                bool = true;
                break;
            }
        }
        return bool;
    }


    public int continNumber()throws SQLException{
        int out = 0;
        while(rowSet.next()){
            out = rowSet.getInt(1);
        }
        return out;
    }


    public void esegui_query(String a) throws SQLException {
        rowSet.setCommand(a);
        rowSet.execute();
    }

    public boolean checkExistFileName() throws SQLException {
        String checkMate ="null";
        while(rowSet.next()){
            checkMate = rowSet.getString(1);
        }
        if(checkMate.equals("true")) {
            return true;
        }
        return false;
    }

    public int returnClassId(String nome) throws SQLException{
        int id_class = 69696969;
        while(rowSet.next()){
            if(rowSet.getString(2).equals(nome)){
                id_class = rowSet.getInt(1);
            }
        }
        return id_class;
    }


}
