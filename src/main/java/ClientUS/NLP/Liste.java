package ClientUS.NLP;

import ClientUS.NLP.Other.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Liste {
    List<String> NN_list;
    List<String> VB_list;
    String Actor_of_story;
    List<c_obj> c_list; // contine le classi
    List<r_rel> r_list; // contine le relazioni
    List<String> ac_list;
    List<a_rel> a_list;

    List<h_rel> h_list;

    r_rel_new rRelNew ;



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
    public List<c_obj> getC_list() {
        return c_list;
    }
    public void setC_list(List<c_obj> c_list) {
        this.c_list = c_list;
    }
    public List<r_rel> getR_list() {
        return r_list;
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



    public List<r_rel>  removeDuplicates_r(){
        Set<r_rel> set = new HashSet<>(r_list);
        r_list.clear();
        r_list.addAll(set);
        return r_list;
    }

    public void removeDuplicates_r_manuale(){
        List<r_rel> newList = new ArrayList<>();
        for (int i = 0; i < r_list.size(); i++) {
            if (!newList.contains(r_list.get(i))) {
                newList.add(r_list.get(i));
            }
        }
        r_list = newList;
    }

    /**
     * funzione per stampare la lista R
     */
    public void print_R_list() {
        for (ClientUS.NLP.Other.r_rel r_rel : r_list) {
            r_rel.print();
        }
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

    public List<h_rel> getH_list() {
        return h_list;
    }

    public void setH_list(List<h_rel> h_list) {
        this.h_list = h_list;
    }

    public void add_item_h_list(h_rel hRel){
        h_list.add(hRel);
        System.out.println("inserimento nella lista");

    }

    public void print_h_list(){
        for (ClientUS.NLP.Other.h_rel h_rel : h_list) {
            h_rel.print();
        }
    }
    public r_rel_new getrRelNew() {
        return rRelNew;
    }
    public void setrRelNew(r_rel_new rRelNew) {
        this.rRelNew = rRelNew;
    }

    public Liste(){
        NN_list = new ArrayList<>();
        VB_list = new ArrayList<>();
        c_list = new ArrayList<>();
        r_list = new ArrayList<>();
        ac_list = new ArrayList<>();
        a_list = new ArrayList<>();
        h_list = new ArrayList<>();
        rRelNew = new r_rel_new();


    }

}
