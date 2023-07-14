package ClientUS.NLP.Interface_rule;

import ClientUS.NLP.Other.Actor_of_story;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

public interface C_RULE {

    void C1(Actor_of_story actor);
    void C2();
    void C3();
    void C4(int i);
    void C5(int i);

    void C6();
    void find_component_noun(SemanticGraph semanticGraph);
    void fix_compound_noun_list(SemanticGraphEdge semanticGraphEdge);


}
