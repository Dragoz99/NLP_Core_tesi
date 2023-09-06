package ClientUS.NLP.Other;

import java.util.ArrayList;
import java.util.List;

public class h_rel_new {

    List<String> class_1;
    List<String> class_2;
    List<String> type;

    public h_rel_new(){
        class_1 = new ArrayList<>();
        class_2 = new ArrayList<>();
        type = new ArrayList<>();
    }

    public List<String> getClass_1() {
        return class_1;
    }

    public void setClass_1(List<String> class_1) {
        this.class_1 = class_1;
    }

    public List<String> getClass_2() {
        return class_2;
    }

    public void setClass_2(List<String> class_2) {
        this.class_2 = class_2;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
    public void print(){
        for(int i = 0;i<class_2.size();i++){
            System.out.println("["+class_1.get(i)+","+class_2.get(i)+ ","+type.get(i)+"]");
        }
    }
}
