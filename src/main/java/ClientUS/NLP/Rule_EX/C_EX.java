package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Other.Actor_of_story;
import ClientUS.NLP.Interface_rule.C_RULE;
import ClientUS.NLP.Liste;
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
        C2();
        C3();
        for(int i = 0;i<List_case.size();i++){
            C4(i);
            C5(i);
        }
        System.out.println("[C4_rule & C5_rule] "+Arrays.toString(list.getC_list().toArray()));
        C6();
    }

    @Override
    public void C1(Actor_of_story actor) {
        list.getC_list().add(actor.getActorOfStory()); // inserisci il nome dell'attore nella lista
        System.out.println("[C1_RULE] "+ Arrays.toString(list.getC_list().toArray())); // stampa
    }
    @Override
    public void C2() {
        List_case_SG = semanticGraph.findAllRelns("obj");
        for(int i = 0;i<List_case_SG.size();i++){
            //se esiste [VB] ---obj--->[NN] allora [NN] è il direct subject
            if(List_case_SG.get(i).getTarget().tag().equalsIgnoreCase("NN")){
                list.getC_list().add(List_case_SG.get(i).getTarget().originalText());
            }
        }
        System.out.println("[C2_RULE] "+ Arrays.toString(list.getC_list().toArray()));
    }
    @Override
    public void C3() {
        List_compound = semanticGraph.findAllRelns("compound");
        System.out.println(List_compound);
        List_case = semanticGraph.findAllRelns("case");
    }

    @Override
    public void C4(int i) {
            if(List_case.get(i).getSource().tag().equalsIgnoreCase("NNS") &&
            List_case.get(i).getTarget().tag().equalsIgnoreCase("POS")){
                        list.getC_list().add(List_case.get(i).getSource().originalText());
            }

    }

    @Override
    public void C5(int i) {
        if((List_case.get(i).getSource().tag().equalsIgnoreCase("NNS") || List_case.get(i).getSource().tag().equalsIgnoreCase("NN")
                && List_case.get(i).getTarget().tag().equalsIgnoreCase("IN"))){

            switch(List_case.get(i).getTarget().originalText()){
                case "of":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    break;
                case "to":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    break;
                case "for":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    break;
            }

        }

    }

    @Override
    public void C6() {
        List_conj = semanticGraph.findAllRelns("conj");
        for(int i = 0;i<List_conj.size();i++){
            if((List_conj.get(i).getSource().tag().equalsIgnoreCase("NN") || List_conj.get(i).getSource().tag().equalsIgnoreCase("NNS")) &&
                    (List_conj.get(i).getTarget().tag().equalsIgnoreCase("NN") || List_conj.get(i).getTarget().tag().equalsIgnoreCase("NNS"))){
                list.getC_list().add(List_conj.get(i).getSource().originalText());
                list.getC_list().add(List_conj.get(i).getTarget().originalText());
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
