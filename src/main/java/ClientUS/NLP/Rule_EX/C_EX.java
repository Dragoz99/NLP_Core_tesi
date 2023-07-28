package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Interface_rule.C_RULE;
import ClientUS.NLP.Liste;
import ClientUS.NLP.Other.Actor_of_story;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C_EX implements C_RULE {


    private List<SemanticGraphEdge> List_case_SG;
    private List<SemanticGraphEdge> List_compound;
    private List<SemanticGraphEdge> List_case;
    private List<SemanticGraphEdge> List_conj;
    private SemanticGraph semanticGraph;
    Actor_of_story actorOfStory;
    Liste list;

    public C_EX(SemanticGraph semanticGraph, Actor_of_story actor, Liste list){
        this.semanticGraph = semanticGraph;
        this.actorOfStory = actor;
        this.list = list;
        C1(actor);
        //C1_1(); // è sbagliata
        C2();
        C3();
        for(int i = 0;i<List_case.size();i++){
            C4(i);
            C5(i);
        }
        System.out.println("[C4_rule & C5_rule] "+Arrays.toString(list.getC_list().toArray()));
        C6();
        final_C_remove(); // rimuove tutti i duplicati

    }

    /**
     * The subject of a sentence is a class, provided that it is
     * a noun and not a pronoun. <p></p>
     *
     *  [C1]
     * @param actor
     */
    @Override
    public void C1(Actor_of_story actor) {
        list.getC_list().add(actor.getActorOfStory()); // inserisci il nome dell'attore nella lista

        System.out.println("[C1_RULE] "+ Arrays.toString(list.getC_list().toArray())); // stampa

    }

    /*
     * tutte le NN sono classi
     * prende la lista_NN e inserisce tutte nella lista.

    public void C1_1(){
      for(int i = 0;i<list.getNN_list().size();i++){
          list.getC_list().add(list.getNN_list().get(i));
      }
    }
    */

    @Override
    public void C2() {
        //  List_case_SG = semanticGraph.findAllRelns("obl.*");
        List_case_SG = semanticGraph.findAllRelns("obl.*");
        for(int i = 0;i<List_case_SG.size();i++){
            //se esiste [VB] ---obj--->[NN] allora [NN] è il direct subject
            if(List_case_SG.get(i).getTarget().tag().equalsIgnoreCase("NN")){
                list.getC_list().add(List_case_SG.get(i).getTarget().originalText());
            }
        }

        System.out.println("[C2_RULE] "+ Arrays.toString(list.getC_list().toArray()));
    }


    /**
     * [C3] : The rule C2 is applied but the direct object is part of
     * a compound noun
     */
    @Override
    public void C3() { // da sistemare
        List_compound = semanticGraph.findAllRelns("compound");
        System.out.println(List_compound); // gestire i compound noun


        for(int i = 0;i<List_compound.size();i++){
            String concat_NN ="";
            try{
                if(List_compound.get(i).getTarget().getOriginal() == List_compound.get(i+1).getSource().getOriginal()){
                    concat_NN += List_compound.get(i).getTarget().getOriginal().toString();
                    //memorizare anche il riferimento originale
                }
            }catch (IndexOutOfBoundsException e){
                System.err.println("for interrotto [C3]."+ " i ="+i+ ", List_compound.size():"+List_compound.size());
                break;
            }
            System.out.println(concat_NN);
        }
        List_case = semanticGraph.findAllRelns("case");
    }

    /**
     * [C4] The noun that precedes the possessive apostrophe is a
     * class
     * @param i
     */
    @Override
    public void C4(int i) {
            if(List_case.get(i).getSource().tag().equalsIgnoreCase("NN") &&
            List_case.get(i).getTarget().tag().equalsIgnoreCase("POS")){
                        list.getC_list().add(List_case.get(i).getSource().originalText());
                        System.out.println(List_case.get(i).getSource().originalText() +" added");
            }
            System.out.println("[C4] "+list.getC_list());
    }

    @Override
    public void C5(int i) {
        if((List_case.get(i).getSource().tag().equalsIgnoreCase("NNS") || List_case.get(i).getSource().tag().equalsIgnoreCase("NN")
                && List_case.get(i).getTarget().tag().equalsIgnoreCase("IN"))){

            switch(List_case.get(i).getTarget().originalText()){
                case "of":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    System.out.println(List_case.get(i).getSource().originalText() + " added");
                    break;
                case "to":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    System.out.println(List_case.get(i).getSource().originalText() + " added");
                    break;
                case "for":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    System.out.println(List_case.get(i).getSource().originalText() + " added");
                    break;
            }

        }
        System.out.println("[C5] "+ list.getC_list());

    }

    @Override
    public void C6() {
        List_conj = semanticGraph.findAllRelns("conj");
        for(int i = 0;i<List_conj.size();i++){
            if((List_conj.get(i).getSource().tag().equalsIgnoreCase("NN") || List_conj.get(i).getSource().tag().equalsIgnoreCase("NNS")) &&
                    (List_conj.get(i).getTarget().tag().equalsIgnoreCase("NN") || List_conj.get(i).getTarget().tag().equalsIgnoreCase("NNS"))){
                list.getC_list().add(List_conj.get(i).getSource().originalText());
                list.getC_list().add(List_conj.get(i).getTarget().originalText());
                System.out.println();
            }
        }
        System.out.println("[C6_rule] "+Arrays.toString(list.getC_list().toArray()));


        //cancella le duplicazioni
        removeDuplicates(list.getC_list());
        System.out.println(Arrays.toString(list.getC_list().toArray()));
    }

    @Override
    public void find_component_noun(SemanticGraph semanticGraph) {

    }

    @Override
    public void fix_compound_noun_list(SemanticGraphEdge semanticGraphEdge) {

    }

    /**
     * final_C_remove:
     *
     * rimuove tutti i duplicati all'interno della lista.
     */
    public void final_C_remove(){
        list.setC_list(removeDuplicates(list.getC_list()));
    }

    public static List<String> removeDuplicates(List<String> list) {
        // Create a new ArrayList
        List<String> newList = new ArrayList<>();

        // Traverse through the first list
        for (String element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}
