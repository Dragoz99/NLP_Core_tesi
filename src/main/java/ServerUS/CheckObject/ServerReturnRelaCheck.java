package ServerUS.CheckObject;

import ServerUS.StoryBuilder;

import javax.sql.RowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.Serializable;
import java.sql.SQLException;

public class ServerReturnRelaCheck implements Serializable {

    private static RowSet rowSet;

    private StoryBuilder storyBuilder;

    /**
     * controlla se esistono le relazioni nel database , avente il filneme id già prese
     * se si. allora le realzioni sono state già inserite precedentemente.
     *
     * @param a
     * @throws SQLException
     */
    public ServerReturnRelaCheck(StoryBuilder a) throws SQLException {
        this.storyBuilder = a;
        this.rowSet = RowSetProvider.newFactory().createJdbcRowSet();
        rowSet.setUrl("jdbc:mysql://localhost:3307/userstorydb");
        rowSet.setUsername("root");
        rowSet.setPassword("1234");
        rowSet.setCommand("select if(count(*) >0,\"true\",\"false\") from relazioni where relazioni_classFileName_id = '" + a.getId() + "'");
        rowSet.execute();
    }

    /**
     * funzione per eseguire le query indipendente
     * @param a
     * @throws SQLException
     */
    public void esegui_query(String a) throws SQLException {
        rowSet.setCommand(a);
        rowSet.execute();
    }


    /**
     * controlla se c'è qualcosa dentro il risulta
     *
     * @return true o false
     * @throws SQLException
     */
    public boolean checkExistFileName() throws SQLException {
        String checkMate = "null";
        while (rowSet.next()) {
            checkMate = rowSet.getString(1);
        }
        if (checkMate.equals("true")) {
            return true;
        }
        return false;
    }
}