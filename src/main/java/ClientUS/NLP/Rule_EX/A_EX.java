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

    public A_EX(SemanticGraph semanticGraph, Liste liste){
        this.semanticGraph = semanticGraph;
        this.liste = liste;
        A1();

    }
    @Override
    public void A1() {


        A1_list = semanticGraph.findAllRelns("amod");
        for(int i =0;i<A1_list.size();i++){

            System.out.println(A1_list.get(i).getTarget().originalText());
        }



    }
    @Override
    public void A2() {



    }
    @Override
    public void A3() {




    }
    @Override
    public void A4() {




    }

    @Override
    public void A5() {

    }

    @Override
    public void A6() {

    }

    @Override
    public void A7() {

    }
}
