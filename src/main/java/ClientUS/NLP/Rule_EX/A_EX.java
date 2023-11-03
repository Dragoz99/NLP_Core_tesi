package ClientUS.NLP.Rule_EX;


import ClientUS.NLP.Interface_rule.A_RULE;
import ClientUS.NLP.Liste;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.List;

/**
 *
 */
public class A_EX implements A_RULE {
    Liste liste;
    SemanticGraph semanticGraph;
    List<SemanticGraphEdge> A1_list;
    List<SemanticGraphEdge> A2_list;
    List<SemanticGraphEdge> A3_list;

    public A_EX(SemanticGraph semanticGraph, Liste liste){
        this.semanticGraph = semanticGraph;
        this.liste = liste;
        A1();
        A2();

    }

    /**
     * Adjective - Attribute of noun phrase main
     *
     * [amod]
     */
    @Override
    public void A1() {
        A1_list = semanticGraph.findAllRelns("amod");
        for(int i =0;i<A1_list.size();i++){
             //System.out.println(A1_list.get(i).getTarget().originalText());
             //liste.getA_list().add(A1_list.get(i).getTarget().originalText());
             //liste.getA_list().add( new a_rel())
        }
    }


    /**
     * <h2> A2 </h2>
     * <p>
     * adverbs modifying verbs
     * quelli più comuni
     * <p>
     * Maniera [quickly, slowly, carefully, neatly, loudly, softly, happily, sadly, angrily] <p>
     * Tempo [now, then, soon, later, yesterday, today, tomorrow] <p>
     * Posto [here, there, near, far, up, down, inside, outside] <p>
     *
     * <h3>relazioni [advmod] tra VBD/VB e RB/JJ </h3>
     */

    /**
     * questa regola non è stata implementata
     */
    @Override
    public void A2() {
       A2_list = semanticGraph.findAllRelns("advmod");
       for(int i = 0;i<A2_list.size();i++){
           // mettere anche i VB e i JJ
            if(A2_list.get(i).getSource().tag().equals("VBD")){
                if(A2_list.get(i).getTarget().tag().equals("RB") || A2_list.get(i).getTarget().tag().equals("JJ")){
                    //liste.getA_list().add(A2_list.get(i).getTarget().originalText()); // inserimento dell'attributo
                }
            }
       }
    }
    @Override
    public void A3() {
    } // skip inutile
    @Override
    public void A4() {} // skip Genetive case
    @Override
    public void A5() {

    }  //
    @Override
    public void A6() {

    } // Specific indicators
    @Override
    public void A7() {

    }
}
