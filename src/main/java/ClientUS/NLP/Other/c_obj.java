package ClientUS.NLP.Other;

public class c_obj {



    //--------------------------------------------------------------------------------------------------
    // DESCRIZIONI VARIABILI:
    // la variable cangiante:
    // la variable index:
    //--------------------------------------------------------------------------------------------------
    private String nome_cangiante;
    private String nome_index ;

    private String nome_second_index;


    public c_obj(){
        nome_cangiante ="";
        nome_index = "";
        nome_second_index = "";

    }
    public c_obj(String nome){
        this.nome_cangiante= nome;
        nome_index = nome;
        nome_second_index = nome;
    }
    public String getNome_cangiante() {
        return nome_cangiante;
    }

    public void setNome_cangiante(String nome_cangiante) {
        this.nome_cangiante = nome_cangiante;
    }

    public String getNome_index() {
        return nome_index;
    }

    public void setNome_index(String nome_index) {
        this.nome_index = nome_index;
    }

    public void setNome_second_index(String nome_index){
        this.nome_index = nome_index;
    }
    public String getNome_second_index(){
        return nome_second_index;
    }
    public void print(){
        System.out.println("["+nome_cangiante+","+nome_index+","+nome_second_index+"]");
    }

}

