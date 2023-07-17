package ServerUS.CheckObject;

import ServerUS.StoryBuilder;

import javax.sql.RowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.Serializable;
import java.sql.SQLException;

public class ServeReturnClassCheck implements Serializable {

    private static RowSet rowSet;

    public ServeReturnClassCheck(StoryBuilder storyBuilder) throws SQLException {
        this.rowSet = RowSetProvider.newFactory().createJdbcRowSet();
        rowSet.setUrl("jdbc:mysql://localhost:3307/userstorydb");
        rowSet.setUsername("root");
        rowSet.setPassword("1234");
        rowSet.setCommand("select if(count(*) >0,\"true\",\"false\") from class where class_filename_id = '"+storyBuilder.getId()+"'");
        rowSet.execute();
        //System.out.println(rowSet.getMaxRows());
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
}
