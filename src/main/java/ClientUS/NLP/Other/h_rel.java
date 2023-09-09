package ClientUS.NLP.Other;

public class h_rel {
    public static String classe_1;
    public  static String classe_2;
    public static String type;
    public h_rel(String classe_1, String classe_2, String type){
        h_rel.classe_1 = classe_1; // involucro
        h_rel.classe_2 = classe_2; // ucro
        h_rel.type = type;
    }


    public h_rel(){
        classe_1 = "";
        classe_2 = "";
        type = "";
    }
    public static void print(){
        System.out.println("["+classe_1+","+classe_2+","+type+"]");
    }
    public String getClasse_1() {
        return classe_1;
    }
    public String getClasse_2(){
        return classe_2;
    }
    public void setClasse_1(String classe_1) {
        this.classe_1 = classe_1;
    }
    public void setClasse_2(String classe_2){
        this.classe_2 = classe_2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        h_rel.type = type;
    }
}
