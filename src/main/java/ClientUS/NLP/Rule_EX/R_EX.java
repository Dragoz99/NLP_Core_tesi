package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Interface_rule.R_RULE;
import ClientUS.NLP.Liste;
import ClientUS.NLP.Other.Actor_of_story;
import ClientUS.NLP.Other.r_rel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.ArrayList;
import java.util.Arrays;
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

    ClientUS.NLP.Other.Actor_of_story Actor_of_story;


    public R_EX(SemanticGraph semanticGraph, Liste liste ,Actor_of_story Actor_of_story){
        this.semanticGraph = semanticGraph;
        this.liste = liste;
        this.Actor_of_story = Actor_of_story;
        R1();
        R2();
      //  R3();


        System.out.println("--------R_EX: risultato--------");
       System.out.println("stamapa R_list");
        liste.print_R_list();
        //liste.removeDuplicates_r();
    }

    /**
     * [R1]
     * The verb is an association between the direct object of the sentence and the subject: verb ( direct object, subject)
     */
    @Override
    public void R1() {
       /*List<SemanticGraph> R2_list_temp = new ArrayList<>();
        IndexedWord indW1 = semanticGraph.getNodeByWordPattern("I");
        IndexedWord indW2;
        for(int i = 0; i<liste.getC_list().size();i++){
            indW2 = semanticGraph.getNodeByWordPattern(liste.getC_list().get(i));
            temp_list = semanticGraph.getShortestUndirectedPathEdges(indW1,indW2);
            System.out.println("-----------------------------");
            System.out.println(temp_list);
            try{
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
            }catch (NullPointerException e){
                System.out.println("[R1] fail");
                e.printStackTrace();
            }
            System.out.println("subject: "+Subject+ "\n verb: "+verb+"\n object: "+obj);
            //______--------______-------______
            // mettere i comandi di inserimento al database! lista
            //______--------______-------______
        }
        for(int i = 0; i<R2_list_temp.size();i++){System.out.println(Arrays.toString(R2_list_temp.toArray()));} */
        // creazione di una lista temporanea --> poi si elimiano i duplicati

        ArrayList<r_rel> r_rels = new ArrayList<>();
        IndexedWord indW1 = semanticGraph.getNodeByWordPattern("I"); // si assume che I si riferisce al Actor of user story
        IndexedWord indW2;
        for (int i = 0; i < liste.getC_list().size(); i++) {
            indW2 = semanticGraph.getNodeByWordPattern(liste.getC_list().get(i).getNome_index());// salva temporanamente il nome della calsse della C_list
            temp_list = semanticGraph.getShortestUndirectedPathEdges(indW1, indW2);
            System.out.println(semanticGraph.getNodeByWordPattern(liste.getC_list().get(i).getNome_index()));
            System.out.println("-----------------------------");
            System.out.println(temp_list); // lista congiunzioni
            try {
                for (int j = 0; j < temp_list.size(); j++) {
                    switch (temp_list.get(j).getTarget().tag()) {
                        case "PRP":
                            Subject = temp_list.get(j).getTarget().originalText();
                            break;
                        case "VB":
                            verb = temp_list.get(j).getTarget().originalText();
                            break;
                        case "NN":
                            obj = temp_list.get(j).getTarget().originalText();
                            /*if(liste.getC_list().contains(temp_list.get(j).getTarget().originalText())){ // controlla la lista C_list
                                obj = temp_list.get(j).getTarget().originalText();*/
                            break;
                    }

                }

                // se i tre campi non sono vuoti , allora aggiungi
                if (!Subject.isEmpty() && !verb.isEmpty() && !obj.isEmpty()) {
                    if (!Objects.equals(Actor_of_story.getActor_ref(), obj)) { // se sono diversi i due campi, inserisci altrimenti no
                        if (!liste.getR_list().contains(new r_rel(Actor_of_story.getActor_ref(), obj))) { // se non contiene un duplicato
                            liste.add_item_r_list(new r_rel(Actor_of_story.getActor_ref(), obj));
                            System.out.println("aggiunto " + Actor_of_story.getActor_ref() + " , " + obj);
                            // liste.removeDuplicates_r();

                        }

                    }

                }
            } catch (NullPointerException e) {
                System.out.println("[R1] fail");
                e.printStackTrace();
            }
            //liste.removeDuplicates_r(); // togli i duplicati.

            //System.out.println("subject: "+Subject+ "\nverb: "+verb+"\nobject: "+obj);
           /* System.out.println("subject: "+Subject);
            System.out.println("verb:" + verb);
            System.out.println("object: "+obj);

        }

        /*for(int j = 0;j<liste.getC_list().size();j++){

            System.out.println("selezionato: " + liste.getC_list().get(j).getNome_index());
            if(liste.getC_list().get(j).getNome_index().equals(obj)){
                liste.add_item_r_list(new r_rel(Actor_of_story.getActor_ref(), liste.getC_list().get(j).getNome_index()));


            }

        }
        removeDuplicates(liste.getR_list());
        System.out.println("stampa");
        liste.print_R_list(); // stampa */

        }

        liste.removeDuplicates_r_manuale();
        liste.print_R_list();
    }

    /**
     * [R2] Verb with preposition
     */
    @Override
    public void R2()  {
        // trovare [VB--(obl)-->NN--(case)-->IN]
        System.out.println("------------[R2]-------------");
        // obl:*
        List<SemanticGraphEdge> list_obj_R2 = semanticGraph.findAllRelns("obl");
        List<SemanticGraphEdge> list_case_R2 = semanticGraph.findAllRelns("case");
        System.out.println("obl: "+Arrays.toString(list_obj_R2.toArray()));
        System.out.println("case: "+Arrays.toString(list_case_R2.toArray()));


        for (SemanticGraphEdge semanticGraphEdge : list_obj_R2) {
            for (SemanticGraphEdge graphEdge : list_case_R2) {
                if (semanticGraphEdge.getTarget().index() == graphEdge.getSource().index()) {
                    if (!Objects.equals(semanticGraphEdge.getTarget().originalText(), Actor_of_story.getActor_ref())) {
                        System.out.println("aggiungo : [" + Actor_of_story.getActor_ref() + " , " + semanticGraphEdge.getTarget().originalText() + "]");
                        liste.add_item_r_list(new r_rel(Actor_of_story.getActor_ref(), semanticGraphEdge.getTarget().originalText())); // "inserimento nella lista"
                    }
                }
            }
        }
        liste.removeDuplicates_r();



       liste.print_R_list(); // comprende "stampa R_list"


      /* System.out.println("Risultato R2 prima: ");
       liste.print_R_list();
      /*for(int i =0;i<liste.getR_list().size();i++){
           System.out.println(liste.getR_list().get(i).getAll());
       }

        liste.removeDuplicates_r();
        System.out.println("Risultato R2 dopo: ");
        liste.print_R_list();
        System.out.println("------------------------------");*/
    }

    /**
     * R3: Prepositions like "of ","to", "for" and "about" detect an association.
     * pdf: regole [R1]
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

    public static List<r_rel> removeDuplicates(List<r_rel> list) {
        // Create a new ArrayList
        List<r_rel> newList = new ArrayList<>();
        // Traverse through the first list
        for (r_rel element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}
