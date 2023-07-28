package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Interface_rule.AC_RULE;
import ClientUS.NLP.Other.Actor_of_story;
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
        AC2(semanticGraph);
        AC3();

        System.out.println("[AC_R - actor]: "+ Actor_of_story.getActorOfStory());
        System.out.println("[AC_R - actor_ref]: " + Actor_of_story.getActor_ref());

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
                    Actor_of_story.setActor_ref(List_case_SG.get(i).getSource().originalText());
                    System.out.println("[AC_RULE] - [ACTOR FOUND]: "+act.getActorOfStory());
                }
            }
    }

    /**
     * [AC2] <p> If the first noun after "As" is a proper name, followed by
     * another name, then the second noun is an actor. </n>
     *
     * puo andare anche oltre a due collegamento
     * <p>
     *  Proper Name = NNP
     * </p>
     */
    @Override
    public void AC2(SemanticGraph semanticGraph) {
      /*  List<SemanticGraphEdge> coumpound_list = semanticGraph.findAllRelns("compound");
        ArrayList<String> lista_compound_nn = new ArrayList<>(); // contine tutti i compound nn
        String concat_NN;
        for(int i = 0;i<coumpound_list.size();i++){
            concat_NN ="";
            if(i >= 1){
                if(coumpound_list.get(i).getSource().index() == coumpound_list.get(i-1).getTarget().index()){ // caso collegamento
                    concat_NN = coumpound_list.get(i).getTarget().originalText()+"_"+ coumpound_list.get(i).getSource().originalText() +"_"+ coumpound_list.get(i-1).getSource().originalText();
                    lista_compound_nn.remove(lista_compound_nn.size()-1); //togli l'ultimo elemento (da migliorare questo )
                    lista_compound_nn.add(concat_NN);

                }else{ // non collegamento
                    concat_NN = coumpound_list.get(i).getTarget().originalText() +"_"+ coumpound_list.get(i).getSource().originalText();
                    lista_compound_nn.add(concat_NN);
                }
            }else{
                concat_NN = coumpound_list.get(i).getTarget().originalText() +"_"+ coumpound_list.get(i).getSource().originalText();
                lista_compound_nn.add(concat_NN);
            }
        }
        System.out.println("coso");
        System.out.println(lista_compound_nn);*/
        System.out.println(); // piccolo spazio
        String concat_NN;
        List<SemanticGraphEdge> coumpound_list = semanticGraph.findAllRelns("compound");
        for(int i = 0;i<coumpound_list.size();i++){
            concat_NN = "";

            if(coumpound_list.get(i).getSource().originalText().equals( Actor_of_story.getActor_ref())){

                concat_NN = coumpound_list.get(i).getTarget().originalText() +"_"+ coumpound_list.get(i).getSource().originalText();

                Actor_of_story.setActorOfStory(concat_NN);//cambio il nome attuale
                System.out.println("[AC2] Actor_of_user: cangiante : "+ Actor_of_story.getActorOfStory());
                System.out.println("[AC2] Actor_of_user: index  : "+Actor_of_story.getActor_ref());
                System.out.println("[AC2_R] "+concat_NN);

                break; // chiusura ciclo for
            }

            //lista_compound_nn.add(concat_NN);

        }
    }

    /**
     * [AC3] <p>
     * The compound noun after "As" is an actor. If the first
     * noun in a compound noun is an adjective and the second
     * noun is an actor, then inheritance relationships exist
     * between this latter and compound noun which is an actor.
     *
     * <p> esempio : </p>
     * <p> as a stategic RestautantName manager ,I want to be able to generate reports on sales and website performance, to analyze market trends and make data-driven strategic decisions.</p>
     */
    @Override
    public void AC3() {
        try{
            String Actor_Free = semanticGraph.findAllRelns("compound")
                    .get(0).getSource().originalText(); // controlla se c'è un compound
            String compound_free;


            if(Actor_Free.equalsIgnoreCase(Actor_of_story.getActor_ref())){
                System.out.println("COMPOUND EXIST!");
                System.out.println(semanticGraph.findAllRelns("compound"));
                compound_free = semanticGraph.findAllRelns("compound")
                        .get(0).getTarget().originalText();// trova

                if(semanticGraph.findAllRelns("amod") // collegamento con JJ.
                        .get(0).getSource().originalText().equalsIgnoreCase(Actor_Free)){

                    System.out.println("[AC3] [inheritance relationships exist] "+ compound_free + " -> " + Actor_Free);
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
