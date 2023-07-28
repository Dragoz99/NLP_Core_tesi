package ClientUS.NLP;

import ClientUS.NLP.Other.a_rel;
import ClientUS.NLP.Other.r_rel;

import java.util.ArrayList;
import java.util.List;

public class Liste {
    List<String> NN_list;
    List<String> VB_list;
    String Actor_of_story;
    List<String> c_list;
    List<r_rel> r_list;
    List<String> ac_list;
    List<a_rel> a_list;


    public List<a_rel> getA_list() {
        return a_list;
    }
    public void setA_list(List<a_rel> a_list) {
        this.a_list = a_list;
    }
    public List<String> getNN_list() {
        return NN_list;
    }
    public void setNN_list(List<String> NN_list) {
        this.NN_list = NN_list;
    }
    public List<String> getVB_list() {
        return VB_list;
    }
    public void setVB_list(List<String> VB_list) {
        this.VB_list = VB_list;
    }
    public String getActor_of_story() {
        return Actor_of_story;
    }
    public void setActor_of_story(String actor_of_story) {
        Actor_of_story = actor_of_story;
    }
    public List<String> getC_list() {
        return c_list;
    }
    public void setC_list(List<String> c_list) {
        this.c_list = c_list;
    }
    public List<r_rel> getR_list() {
        return r_list;
    }
    public void print_R_list() {
        for(int i = 0;i<r_list.size();i++){
            r_list.get(i).print();
        }
    }
    public void setR_list(List<r_rel> r_list) {
        this.r_list = r_list;
    }
    public List<String> getAc_list() {
        return ac_list;
    }
    public void setAc_list(List<String> ac_list) {
        this.ac_list = ac_list;
    }
    /**
     * Funzione per aggiungere oggetti di tipo r_rel (dedicati alle relazioni) nella lista
     * relazioni della classe Liste.
     * @param rel
     */
    public void add_item_r_list(r_rel rel){
        r_list.add(rel);
        System.out.println("inserimento nella lista");
    }
    public Liste(){
        NN_list = new ArrayList<>();
        VB_list = new ArrayList<>();
        c_list = new ArrayList<>();
        r_list = new ArrayList<>();
        ac_list = new ArrayList<>();
        a_list = new ArrayList<>();

    }

}
