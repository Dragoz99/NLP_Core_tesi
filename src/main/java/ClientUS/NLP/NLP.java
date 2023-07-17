package ClientUS.NLP;

import ClientUS.NLP.Other.Actor_of_story;
import ClientUS.NLP.Other.insert_data_list;
import ClientUS.NLP.Rule_EX.AC_EX;
import ClientUS.NLP.Rule_EX.A_EX;
import ClientUS.NLP.Rule_EX.C_EX;
import ClientUS.NLP.Rule_EX.R_EX;
import ClientUS.RemoteListener;
import ServerUS.CheckObject.ServeReturnClassCheck;
import ServerUS.StoryBuilder;
import ServerUS.UserInterface;
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


    public NLP(StoryBuilder a,UserInterface stub) throws IOException, SQLException {

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
        Annotation Document = new Annotation(a.getDescrizione());
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

                SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations
                        .EnhancedDependenciesAnnotation.class);

                System.out.println("[---------------[AC_RULES]--------------]");
                AC_EX ac_ex = new AC_EX(dependencies,actor,stub);

                System.out.println("[---------------[C_RULES]---------------]");
                C_EX c_ex = new C_EX(dependencies,actor,liste);

                System.out.println("[---------------[R_RULES]---------------]");
                R_EX r_ex = new R_EX(dependencies,liste,actor);

                System.out.println("[---------------[A_RULES]---------------]");
                A_EX a_ex = new A_EX(dependencies,liste);

                // prova
                System.out.println("------");
                System.out.println(liste.getNN_list());
                System.out.println("------");
                System.out.println(liste.getC_list());
                System.out.println("------");
                System.out.println(liste.getAc_list());
                System.out.println("------");
                System.out.println(liste.getR_list());



                insert_data_list data_list = new insert_data_list(liste,stub); // filename
                String ddl = "SELECT * FROM class";
              /*ResultSet resultSet =  stub.check_db_resultSet(ddl);
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                int columnsNumber = resultSetMetaData.getColumnCount();
                while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = resultSet.getString(i);
                        System.out.print(columnValue + " " + resultSetMetaData.getColumnName(i));
                    }
                    System.out.println("");
                }*/

                data_list.insert_filename(a); //class

                ServeReturnClassCheck abbo = new ServeReturnClassCheck(a); // controllo se esiste il filename
                boolean prova = abbo.checkExistFileName();
                System.out.println(prova);
                if(!prova){
                    for(int i =0;i<liste.getC_list().size();i++){
                        data_list.insert_class(a,liste.getC_list().get(i));
                    }
                }else{
                    System.out.println("[Attenzione]: classi non inserite. file gia esiste");
                }



            }
    }
    /**
     * inserimento delle info riguardanti il nome del file
     */
    private void check(StoryBuilder a) throws SQLException, RuntimeException, RemoteException {
        stub.insertDDL_userStory("select exist (select * from filename where filename_id ='"+a.getId()+"')");
    }




}
