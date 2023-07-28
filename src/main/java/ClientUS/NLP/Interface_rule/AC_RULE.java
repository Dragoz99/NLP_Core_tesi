package ClientUS.NLP.Interface_rule;

import ClientUS.NLP.Other.Actor_of_story;
import edu.stanford.nlp.semgraph.SemanticGraph;

import java.rmi.RemoteException;
import java.sql.SQLException;

public interface AC_RULE {
    void AC1(Actor_of_story act) throws SQLException, RemoteException;
    void AC2(SemanticGraph semanticGraph);
    void AC3();

}
