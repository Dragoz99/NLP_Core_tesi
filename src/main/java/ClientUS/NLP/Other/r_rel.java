package ClientUS.NLP.Other;

public class r_rel {
    private String class_1;
    private String class_2;

    public r_rel(String class_1, String class_2){
        this.class_1 = class_1;
        this.class_2 = class_2;
    }
    public String getClass_1() {
        return class_1;
    }
    public void setClass_1(String class_1) {
        this.class_1 = class_1;
    }
    public String getClass_2() {
        return class_2;
    }
    public void setClass_2(String class_2) {
        this.class_2 = class_2;
    }
    public void print(){
        System.out.println("["+class_1+","+class_2+"]");
    }
}
