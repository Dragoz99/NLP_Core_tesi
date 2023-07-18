package ClientUS.NLP.Other;

import ClientUS.NLP.Liste;
import ServerUS.CheckObject.ServerReturnObject;
import ServerUS.StoryBuilder;
import ServerUS.UserInterface;

import java.rmi.RemoteException;
import java.sql.SQLException;

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
        stub.insertDDL_userStory("" +
                "INSERT INTO " +
                "");
    }
}
