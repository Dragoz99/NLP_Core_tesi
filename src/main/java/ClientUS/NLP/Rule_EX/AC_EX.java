package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Other.Actor_of_story;
import ClientUS.NLP.Interface_rule.AC_RULE;
import ServerUS.UserInterface;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class AC_EX implements AC_RULE {
    private List<SemanticGraphEdge> List_case_SG; // lista dei SemanticGraph
    private SemanticGraph semanticGraph;
    private UserInterface stub;
    Actor_of_story Actor_of_story;
    public AC_EX(SemanticGraph semanticGraph, Actor_of_story x, UserInterface stub) throws SQLException, RemoteException {
        this.Actor_of_story =x;
        this.semanticGraph = semanticGraph;
        this.stub = stub;

        List_case_SG = semanticGraph.findAllRelns("case");
        System.out.println(List_case_SG);
        AC1(Actor_of_story);
        AC3();

    }
    @Override
    public void AC1(Actor_of_story act) throws SQLException, RemoteException {
            for(int i = 0;i<List_case_SG.size();i++){ // scannerizza la lista
                if(List_case_SG.get(i).getTarget().originalText().equalsIgnoreCase("As")){ // trova se c'è un 'As' collegato
                    if(List_case_SG.get(i).getSource().tag().equalsIgnoreCase("NN")){ // controlla se c'è un NN collegato
                        System.out.println("[ACTOR ACCEPT NN] :"+List_case_SG.get(i).getSource());
                       /* stub.insertUserStory(

                        );*/
                    }
                    Actor_of_story.setActorOfStory(List_case_SG.get(i).getSource().originalText());
                    System.out.println("[AC_RULE] - [ACTOR FOUND]: "+act.getActorOfStory());
                }
            }
    }
    @Override
    public void AC2() {




    }

    @Override
    public void AC3() {
        try{
            String Actor_Free = semanticGraph.findAllRelns("compound")
                    .get(0).getSource().originalText(); // controlla se c'è un compound
            String compound_free;

            if(Actor_Free.equalsIgnoreCase(Actor_of_story.getActorOfStory())){
                System.out.println("COMPOUND EXIST!");
                System.out.println(semanticGraph.findAllRelns("compound"));
                compound_free = semanticGraph.findAllRelns("compound")
                        .get(0).getTarget().originalText();// trova


                if(semanticGraph.findAllRelns("amod")
                        .get(0).getSource().originalText().equalsIgnoreCase(Actor_Free)){

                    System.out.println("[inheritance relationships exist] "+ compound_free + " -> " + Actor_Free);
                    System.out.println(semanticGraph.findAllRelns("amod"));
                    Actor_of_story.setActorOfStory(compound_free + "_" +Actor_Free); // inserire memorizzazione

                    //classBulder work and Selettore
                    //inserire una memorizzazione di gerarchia. Class Builder
                    //inserire un modo per non ricreare classi già esistenti

                }
            }
        }catch(IndexOutOfBoundsException e){
            System.out.println("[AC3_SKIP]: no compound found");
        }
        if(Actor_of_story == null){
            System.out.println("[AC_RULE] - [ACTOR NOT FOUND]");
        }
    }
}
