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
    public String getAll(){
        return "["+class_1+","+class_2+"]";
    }

    public static boolean r_rel_eql(r_rel a, r_rel b){
        boolean check = false;
        if(a.getClass_1() == b.getClass_1()){
            if(a.getClass_2()== b.getClass_2()){
                check= true;
            }
        }
        return check;
    }
    public static boolean r_rel_eql(Actor_of_story a, r_rel b){
        boolean check = false;
        if(a.getActor_ref() == b.getClass_2()){
            check = true;
        }
        return check;

    }


}
