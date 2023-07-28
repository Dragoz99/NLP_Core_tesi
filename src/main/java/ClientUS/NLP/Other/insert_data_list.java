package ClientUS.NLP.Other;

import ClientUS.NLP.Liste;
import ServerUS.CheckObject.ServerReturnObject;
import ServerUS.StoryBuilder;
import ServerUS.UserInterface;

import java.rmi.RemoteException;
import java.sql.SQLException;


/**
 * classe per inserire i dati all'interno del database.
 * prende in ingresso la classe liste per gestire i dati.
 * utilizza UsertInterface per riferirsi con il server.
 */
public class insert_data_list {
    private Liste liste;
    private final UserInterface stub;
    public insert_data_list(Liste liste, UserInterface stub){
        this.liste=liste;
        this.stub=stub;
    }
    public void insert_class(StoryBuilder storyBuilder, String class_name) throws SQLException, RemoteException {
            stub.insertDDL_userStory("INSERT INTO class (class_name,class_filename_id,class_type)" +
                    "VALUES('" + class_name +
                    "','" + storyBuilder.getId()+
                    "','" + " private "+
                    "')");
            System.out.println("[Inserimento] "+class_name+" inserimento classe eseguito con successo ");
    }

    public void insert_filename(StoryBuilder storyBuilder) throws SQLException, RemoteException {
        String ddl = "select * from class;";
        ServerReturnObject a = new ServerReturnObject(storyBuilder);
        a.esegui_query("select count(*) from class where class_filename_id = '"+storyBuilder.getId()+"'");

        int memoryCountClass = a.continNumber();
        System.out.println(memoryCountClass);

        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        if( memoryCountClass == 0 ){
            try{
                stub.insertDDL_userStory("INSERT INTO filename (filename_id,filename_name,filename_tag,filename_data) " +
                        "VALUES ('"
                        +storyBuilder.getId()+"','"
                        +storyBuilder.getNome()+"','"
                        +storyBuilder.getTag()+"','"
                        +storyBuilder.getData_creazione()+
                        "')");
                System.out.print("INSERT userstory:");
                storyBuilder.print();
            }catch (Exception e){
                System.out.println("USER story gia' presente");
            }
        }else{
            System.out.println("Elemento già presente");
        }

    }
    public void insert_relazion(StoryBuilder a, Liste liste) throws SQLException, RemoteException{
        ServerReturnObject serverReturnObject = new ServerReturnObject(a);

       String cc =
            "select if(count(*) >0,\"true\",\"false\") " +
            "from relazioni " +
            "where relazioni_classFileName_id_1  = '"+a.getId()+"' and relazioni_classFileName_id_2 = '"+a.getId()+"'";
        serverReturnObject.esegui_query(cc);

        boolean check_boolean = serverReturnObject.checkBoolean(); // controllo
        System.out.println("controllo c:"+check_boolean);

        //if(!check_boolean) { // controllo
         /* serverReturnObject.esegui_query(
                    "select class_id, class_name " +
                            "from class " +
                            "where class_filename_id = '" + a.getId() + "'");*/

        serverReturnObject.esegui_query(cc);
            if (!serverReturnObject.checkBoolean()) {
                for (int i = 0; i < liste.getR_list().size(); i++) {

                    String nome_class_1 = liste.getR_list().get(i).getClass_1();
                    String nome_class_2 = liste.getR_list().get(i).getClass_2();

                        stub.insertDDL_userStory("insert into relazioni" +
                                "(relazioni_className_1,relazioni_className_2,relazioni_classID_1,relazioni_classID_2," +
                                "relazioni_classFileName_id_1,relazioni_classFileName_id_2)" +
                                " values ('" + nome_class_1 + "','" + nome_class_2 + "'," +
                                " (select class_id from class where class_name = '"
                                    + nome_class_1 + "' and class_filename_id = '" + a.getId() + "')," +
                                " (select class_id from class where class_name = '"
                                    + nome_class_2 + "' and class_filename_id = '" + a.getId() + "')," +
                                "'" + a.getId() + "','" + a.getId() + "')");
                }
            }
        //}
        // \(^-^)/
    }


    public void insert_attibute(StoryBuilder a, Liste liste) throws SQLException {
        ServerReturnObject attributeObj = new ServerReturnObject(a);
        String cc = "";

    }
}
