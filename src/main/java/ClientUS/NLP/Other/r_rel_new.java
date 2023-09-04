package ClientUS.NLP.Other;

import java.util.ArrayList;
import java.util.List;

public class r_rel_new {

    public List<String> getClass1() {
        return class1;
    }

    public void setClass1(List<String> class1) {
        this.class1 = class1;
    }

    public List<String> getClass2() {
        return class2;
    }

    public void setClass2(List<String> class2) {
        this.class2 = class2;
    }

    List<String> class1;
    List<String> class2;

    public r_rel_new(){
        this.class1 = new ArrayList<>();
        this.class2 = new ArrayList<>();
    }

    public void print(){
        for(int i = 0;i<class2.size();i++){
            System.out.println("["+class1.get(i)+","+class2.get(i)+"]");
        }
    }
}
