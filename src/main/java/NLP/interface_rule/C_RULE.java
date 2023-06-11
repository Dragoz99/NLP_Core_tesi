package NLP.interface_rule;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

public interface C_RULE {

    void C1();
    void C2();
    void C3();
    void C4();
    void C5();
    void find_component_noun(SemanticGraph semanticGraph);
    void fix_compound_noun_list(SemanticGraphEdge semanticGraphEdge);


}
