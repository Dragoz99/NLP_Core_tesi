package ClientUS.NLP;

import com.google.common.io.Files;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class NLP {



    List<CoreMap> sentences;
    public List<String> NN_list;
    public List<String> VB_list;
    public List<String> C_list;

    public File file;
    public String Actor_of_story;


    //Liste Supplementali
    Collection<RelationTriple> triples;
    Scanner scan = new Scanner(System.in);


    public NLP(File file) throws IOException {
    // pre regole
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref,depparse,natlog,openie,coref");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    //file
    this.file = file;
    this.NN_list = new ArrayList<String>();
    this.VB_list = new ArrayList<String>();

    // read some text from the file..
    file = new File("src/main/resources/sample-context.txt");
    String text = Files.asCharSource(file, Charset.forName("UTF-8")).read();

    // create an empty Annotation just with the given text
    Annotation Document = new Annotation(text);

    // run all Annotators on this text
    pipeline.annotate(Document);

    // frase
    // these are all the sentences in this document
    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
    sentences = Document.get(CoreAnnotations.SentencesAnnotation.class);

        //-----------------------------
        //---- POS: part of speech ----
        //-----------------------------
        for(CoreMap sentence: sentences){
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                // riempimento delle list
                if(pos.equalsIgnoreCase("NN") ){
                    NN_list.add(word);
                }

                if(pos.equalsIgnoreCase("VB") || pos.equalsIgnoreCase("VBP")){
                    VB_list.add(word);
                }
            }
            for(int i =0;i<NN_list.size();i++){
                System.out.println(NN_list.get(i));
            }

            //-----------------------------
            //----     OPEN IE        ----
            //-----------------------------
            System.out.println("[---OPENIE---]");
            for (CoreMap TT : Document.get(CoreAnnotations.SentencesAnnotation.class)) {
                // Get the OpenIE triples for the sentence
                triples = TT.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
                // Print the triples
                for (RelationTriple triple : triples) {
                    System.out.println(triple.confidence + "\t -" +
                            triple.subjectLemmaGloss() + "\t -" +
                            triple.relationLemmaGloss() + "\t -" +
                            triple.objectLemmaGloss());
                }
            }
            System.out.println("[--------------[AC_RULES]--------------]");
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class);


           /* AC_rule(dependencies); // ac rule execute
            System.out.println("[--------------[C_RULES]--------------]");
            C_rule(dependencies);
            System.out.println("[--------------[R_RULES]--------------]");
            R_rule(dependencies);
            */


        }





    }
}
