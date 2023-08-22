package ClientUS.NLP;

import ClientUS.NLP.Other.Actor_of_story;
import ClientUS.NLP.Other.insert_data_list;
import ClientUS.NLP.Rule_EX.*;
import ClientUS.RemoteListener;
import ServerUS.CheckObject.ServerReturnObject;
import ServerUS.StoryBuilder;
import ServerUS.UserInterface;
import com.google.common.io.Files;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.Mention;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

public class NLP implements RemoteListener {
    List<CoreMap> sentences;
    public List<String> NN_list;
    public List<String> VB_list;

    public List<String> C_list;

    public File file;
    public Actor_of_story actor;

    private static UserInterface stub;

    //Liste Supplementali
    Collection<RelationTriple> triples;
    Scanner scan = new Scanner(System.in);


    public NLP(StoryBuilder storyBuilder,UserInterface stub) throws IOException, SQLException {

        this.stub = stub; // collegamento
        Liste liste = new Liste();


        // pre regole
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref,depparse,natlog,openie,coref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        //file

        this.NN_list = new ArrayList<String>();
        this.VB_list = new ArrayList<String>();
        actor = new Actor_of_story("");

        // read some text from the file..
        file = new File("src/main/resources/sample-context.txt");
        String text = Files.asCharSource(file, Charset.forName("UTF-8")).read();

        //create an empty Annotation just with the given text
        //Annotation Document = new Annotation(text);
        Annotation Document = new Annotation(storyBuilder.getDescrizione());
        //Annotation Document = new Annotation("As a registered user, I want to be able to easily return or exchange items so that I can shop with confidence and get the products that I need.");
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
                        liste.getNN_list().add(word);
                        //NN_list.add(word);
                    }

                    if(pos.equalsIgnoreCase("VB") || pos.equalsIgnoreCase("VBP")){
                         //VB_list.add(word);
                        liste.getVB_list().add(word);
                    }
                }
                for(int i =0;i<liste.getNN_list().size();i++){
                    System.out.println(liste.getNN_list().get(i));
                }

                //-----------------------------
                //----     OPEN IE        ----
                //-----------------------------
             /*   System.out.println("[---OPENIE---]");
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
*/
                //--------------------------
                //---     Coreference    ---
                //--------------------------

                List<SemanticGraph> List_coref = new ArrayList<>();
                for (CoreMap sentence_coreg : Document.get(CoreAnnotations.SentencesAnnotation.class)) {
                    System.out.println("-----");
                    for(Mention m: sentence_coreg.get(CorefCoreAnnotations.CorefMentionsAnnotation.class)){
                        Annotation annotation = new Annotation(m.toString());
                        pipeline.annotate(annotation);
                        for(CoreMap pippo : annotation.get(CoreAnnotations.SentencesAnnotation.class)){
                            SemanticGraph dipe = pippo.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class);
                            List_coref.add(dipe);
                            System.out.println("aggiunto :"+dipe);

                        }

                    }

                }



                //-------------------------
                SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations
                        .EnhancedDependenciesAnnotation.class);

                System.out.println("[---------------[AC_RULES]--------------]");
                AC_EX ac_ex = new AC_EX(dependencies,actor,stub,liste);

                System.out.println("[---------------[C_RULES]---------------]");
                C_EX c_ex = new C_EX(dependencies,actor,liste);

                System.out.println("[---------------[R_RULES]---------------]");
                R_EX r_ex = new R_EX(dependencies,liste,actor,List_coref);

                System.out.println("[---------------[A_RULES]---------------]");
                A_EX a_ex = new A_EX(dependencies,liste);

                System.out.println("[---------------[H_RULES]---------------]");
                H_EX h_ex = new H_EX(liste,dependencies);

                System.out.println("[---------------------------------------]");
                System.out.println("Riepilogo");
                // prova
                System.out.println("----------");
                System.out.println("--- NN --");
                System.out.println(liste.getNN_list());
                System.out.println("-- C --");
                System.out.println(liste.getC_list());
                System.out.println("-- AC --");
                System.out.println(liste.getAc_list());
                System.out.println("-- R --");
                liste.print_R_list();
                System.out.println("-- A --");
                System.out.println(liste.getA_list());
                System.out.println("-- H --");
                System.out.println(liste.getH_list());
                liste.print_h_list();
                System.out.println("--------");


//---------------------------------------------------------------------------------------------------------------
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//---------------------------------------------------------------------------------------------------------------
                insert_data_list data_list = new insert_data_list(liste,stub); // filename
                data_list.insert_filename(storyBuilder);
                ServerReturnObject returnObject = new ServerReturnObject(storyBuilder);

                // esecuzione controllo nel database
                returnObject.esegui_query("select if(count(*) >0,\"true\",\"false\") from class where class_filename_id = '"+storyBuilder.getId()+"'");
                boolean controllo_conteggio = returnObject.checkExistFileName(); // ritorna un valore booleano
                System.out.println(controllo_conteggio);

                if(!controllo_conteggio){
                    // inserimento classi
                    for(int i =0;i<liste.getC_list().size();i++){
                        data_list.insert_class(
                                storyBuilder,
                                liste.getC_list().get(i).getNome_index(),
                                liste.getC_list().get(i).getNome_cangiante()); // inserimento nome cangiante all'interno del db
                    }
                }else{
                    System.out.println("[Attenzione]: classi non inserite. file gia esiste");
                }
                // inserimento relazioni
                data_list.insert_relazion(storyBuilder,liste);
            }
    }
    /**
     * inserimento delle info riguardanti il nome del file
     */
    private void check(StoryBuilder a) throws SQLException, RuntimeException, RemoteException {
        stub.insertDDL_userStory(
                "select exist (select * from filename where filename_id ='"+a.getId()+"')"
        );
    }




}
