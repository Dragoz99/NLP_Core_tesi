package ServerUS;

import java.io.Serializable;

public class StoryBuilder implements Serializable {

    private String nome;
    private String descrizione;
    private String autore;
    private String versione;
    private String data_creazione;
    private String nome_progetto;
    private String tag;
    private String id;




    public StoryBuilder(String id,String nome, String descrizione,String autore, String versione,String  data_creazione){
        this.id = id; // id della storia. fa riferimento al database icescrum
        this.nome = nome;
        this.descrizione = descrizione;
        this.autore = autore;
        this.tag = "";
        this.versione = versione;
        this.data_creazione = data_creazione;

    }

    public StoryBuilder(String nome,String descrizione, String autore,String versione, String data_creazione){
            this.nome = nome;
            this.descrizione = descrizione;
            this.autore = autore;
            this.versione = versione;
            this.data_creazione = data_creazione;
            this.tag = "nullo";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }

    public String getData_creazione() {
        return data_creazione;
    }

    public void setData_creazione(String data_creazione) {
        this.data_creazione = data_creazione;
    }

    public String getNome_progetto() {
        return nome_progetto;
    }

    public void setNome_progetto(String nome_progetto) {
        this.nome_progetto = nome_progetto;
    }


    public String getTag(){
        return tag;
    }
    public void setTag(String tag){
        this.tag = tag;
    }

    public String getId(){
        return id;
    }
    public void print(){
        System.out.println(
                "["+nome +"/"+descrizione +"/"+ autore+ "/"+versione+"/"+data_creazione+"]"
        );
    }



}
