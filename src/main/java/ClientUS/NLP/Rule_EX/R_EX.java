package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Interface_rule.R_RULE;
import ClientUS.NLP.Liste;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.ArrayList;
import java.util.List;

public class R_EX implements R_RULE {
    SemanticGraph semanticGraph;
    Liste liste;
    String Subject = "";
    String obj = "";
    String verb = "";
    List<SemanticGraphEdge> temp_list;
    List<SemanticGraphEdge> R2_list;


    public R_EX(SemanticGraph semanticGraph, Liste liste){
        this.semanticGraph = semanticGraph;
        this.liste = liste;

        R1();

    }
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
    @Override
    public void R2() {
        System.out.println("--------------R2 ---------------");


    }

    @Override
    public void R3() {

    }

    @Override
    public void R4() {

    }
}
