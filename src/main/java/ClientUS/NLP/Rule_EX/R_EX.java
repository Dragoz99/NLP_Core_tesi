package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Actor_of_story;
import ClientUS.NLP.Interface_rule.R_RULE;
import ClientUS.NLP.Liste;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class R_EX implements R_RULE {
    SemanticGraph semanticGraph;
    Liste liste;
    String Subject = "";
    String obj = "";
    String verb = "";
    List<SemanticGraphEdge> temp_list;
    List<SemanticGraphEdge> R2_list;

    List<SemanticGraphEdge> R3_list;

    ClientUS.NLP.Actor_of_story Actor_of_story;


    public R_EX(SemanticGraph semanticGraph, Liste liste ,Actor_of_story Actor_of_story){
        this.semanticGraph = semanticGraph;
        this.liste = liste;
        this.Actor_of_story = Actor_of_story;

        R1();
        R2();
        R3();


    }

    /**
     * pdf: regole 2 [R3], regole [R1]
     */
    @Override
    public void R1(){
        List<SemanticGraph> R2_list_temp = new ArrayList<>();
        IndexedWord indW1 = semanticGraph.getNodeByWordPattern("I");
        IndexedWord indW2;
        for(int i = 0; i<liste.getC_list().size();i++){
            indW2 = semanticGraph.getNodeByWordPattern(liste.getC_list().get(i));
            temp_list = semanticGraph.getShortestUndirectedPathEdges(indW1,indW2);
            System.out.println("-----------------------------");
            System.out.println(temp_list);

            for(int j =0;j<temp_list.size();j++){  // scan del path trovato
                switch (temp_list.get(j).getTarget().tag()){
                    case "PRP":
                        Subject = temp_list.get(j).getTarget().originalText();
                        break;
                    case "VB":
                        verb = temp_list.get(j).getTarget().originalText();
                        break;
                    case "NN":
                        if(liste.getC_list().contains(temp_list.get(j).getTarget().originalText())){ // controlla la lista C_list
                            obj = temp_list.get(j).getTarget().originalText();
                        }
                        break;
                }
                if(temp_list.get(j).getTarget().originalText().equalsIgnoreCase("I")){
                    Subject = temp_list.get(j).getTarget().originalText();
                }
            }
            System.out.println("subject: "+Subject+ "\nverb: "+verb+"\nobject: "+obj);
            //______--------------------------
            // mettere i comandi di inserimento al database! lista
            //______--------------------------
        }
    }

    /**
     * [R2] verb with preposition
     *
     * questa regola crea la funzione in relazione. non la relazione stessa
     *  pdf: Regole [R2], Regole2 [R4]
     */
    @Override
    public void R2() {
        System.out.println("--------------R2 ---------------");
        R2_list = semanticGraph.findAllRelns("obl");
        // 1) ricerco una nuova lista escludendo i collegamenti che hanno come sorgente tag diveso da VB

        for(int i = 0;i<R2_list.size();i++){
            if(!Objects.equals(R2_list.get(i).getSource().tag(),"VB")){ // se il tag è diverso da VB
                R2_list.remove(i);
            }
        }
        // test
        System.out.println(R2_list);
        for(int i = 0;i<R2_list.size();i++){
            String VB = R2_list.get(i).getSource().originalText();
            String IN = R2_list.get(i).getRelation().getSpecific();
            String NN = R2_list.get(i).getTarget().originalText();
            String Op_r = VB+"_"+IN+"("+Actor_of_story.getActorOfStory()+","+NN+")";
            System.out.println("[R2]"+Op_r);
        }
    }

    /**
     * R3: Prepositions like "of ","to", "for" and "about" detect an association.
     * pdf: regole [R1]
     *
     *
     * utilità: rinforza la regola [R2]
     */
    @Override
    public void R3() {
        System.out.println("--------------R3---------------");
        R3_list = semanticGraph.findAllRelns("obl");


        String assosation;
        for(int i = 0;i<R3_list.size();i++){

            assosation = R3_list.get(i).getRelation().getSpecific();
            //System.out.println(R3_list.get(i).getRelation().getSpecific());

            switch (assosation){

                case "for":
                    //scrivi il comando Sql
                    break;
                case "of":
                    //scrivi il comando Sql
                    break;
                case "to":
                    //scrivi il comando Sql
                    break;
                case "about":
                    //scrivi il comando Sql
                    break;
                default:
                    // non è una associazione
            }
            System.out.println();
        }



        
    }
    /**
     * R3:  Noun–noun compound
     * pdf: regole [R5]
     *
     */
    @Override
    public void R4() {
        System.out.println("---------R4--------");


    }


    /**
     * R5: Possessive case: "s’", "my", "his"… determine an association; Correlation of pronouns with nouns to extract the associations using
     * coreference resolution of Stanford core NLP tool
     * pdf: regole [R4]
     *
     *
     * utilità: aggiunta opt
     */
    public void R5() {

    }
}
