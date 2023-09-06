package ClientUS.NLP.Other;

public class h_rel {
    private static String classe_1;
    private static String classe_2;
    private static String type;
    public h_rel(String classe_1, String classe_2, String type){
        this.classe_1 = classe_1; // involucro
        this.classe_2 = classe_2; // ucro
        this.type = type;

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
        this.type = type;
    }
}
