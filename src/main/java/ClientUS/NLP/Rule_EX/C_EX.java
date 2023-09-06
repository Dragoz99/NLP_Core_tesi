package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Interface_rule.C_RULE;
import ClientUS.NLP.Liste;
import ClientUS.NLP.Other.Actor_of_story;
import ClientUS.NLP.Other.c_obj;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.*;

public class C_EX implements C_RULE {


    private List<SemanticGraphEdge> List_case_SG;
    private List<SemanticGraphEdge> List_compound;
    private List<SemanticGraphEdge> List_case;
    private List<SemanticGraphEdge> List_conj;

    private List<IndexedWord> IndexedWord_list_compound;
    private SemanticGraph semanticGraph;
    Actor_of_story actorOfStory;
    Liste list;

    public C_EX(SemanticGraph semanticGraph, Actor_of_story actor, Liste list){
        this.semanticGraph = semanticGraph;
        this.actorOfStory = actor;
        this.list = list;
        C1(actor);
        C2();
        C3();
        //for(int i = 0;i<List_case.size();i++){ // per ogni case
        C4();
        C5();



       //System.out.println("[C4_rule & C5_rule] "+ Arrays.toString(list.getC_list().toArray()));
        C6();
       // final_C_remove(); // rimuove tutti i duplicati */

        System.out.println("---- [C_RULE] risultato ----");
        list.print_c_list();

    }

    /**
     * The subject of a sentence is a class, provided that it is
     * a noun and not a pronoun. <p>
     *
     * il soggetto è Actor_of_story <p>
     *  [C1]
     * @param actor
     */
    @Override
    public void C1(Actor_of_story actor) {
        System.out.println("--------------[C1]--------------");
        //list.getC_list().add(actor.getActorOfStory());

        c_obj cObj = actor.toC_obj();
        if (cObj.getNome_cangiante().contains("_")){

            String temp_word = cObj.getNome_cangiante().replace("_"+cObj.getNome_index(),"");
            System.out.println("sostituzione "+temp_word);
        }
        list.getC_list().add(actor.toC_obj()); // inserisci il nome dell'attore nella lista
        System.out.print("[C1_RULE] ");



        list.print_c_list();

        System.out.println();
       //System.out.println("[C1_RULE] "+ );
       //System.out.println("[C1_RULE] "+ Arrays.toString(list.getC_list().toArray())); // stampa
    }

    /**
     * [C2]
     * <p>
     * The direct object of the sentence is a class.
     */
    @Override
    public void C2() { // da modificare
        System.out.println("--------------[C2]--------------");
        List_case_SG = semanticGraph.findAllRelns("obj"); // IMP: non deve esse obj.* perchè dobbiamo trovare una cosa precisa

        List_compound = semanticGraph.findAllRelns("compound");
        System.out.println(List_case_SG);
        IndexedWord_list_compound = new LinkedList<>();
        for (int i = 0; i < List_case_SG.size(); i++) {
            //se esiste [VB] ---obj--->[NN] allora [NN] è il direct subject
            IndexedWord contenitore = List_case_SG.get(i).getTarget();

            System.out.println(List_case_SG);
            if (contenitore.tag().equals("NN") || contenitore.tag().equals("NNS")) {
                //list.getC_list().add(new c_obj(contenitore.originalText()));

                IndexedWord_list_compound.add(List_case_SG.get(i).getTarget()); // salviamo temporanamente l'IndexedWord
                System.out.println("aggiunto");
                // 2) altrimenti inserisci il contenitore in liste.getC_list()
                // aggiungi se non esiste il compound !!!



                // se esiste un compound va a C3


            }
            System.out.println("[C2_indexedword] " + IndexedWord_list_compound);
            System.out.println();
            System.out.print("[C2_RULE] ");

            System.out.println("IndexedWord_list: " + IndexedWord_list_compound);
/*              QUESTA PARTE è SPERIMENTALE NON FUNZIONA NSUBJ
        List<SemanticGraphEdge> List_nsubj = semanticGraph.findAllRelns("nsubj");
        List<SemanticGraphEdge> List_concat ;
        IndexedWord indW1;
        IndexedWord indW2;
        //System.out.println("[C2]");
        System.out.println("[C2] "+List_nsubj.toString());
        for(int i = 0; i<List_nsubj.size() ;i++){ // su dimensione di List_nsubj

            if(List_nsubj.get(i).getTarget().tag().equals("PRP")){ // base
                String contenitore = List_nsubj.get(i).getSource().tag();

                if(contenitore.equals("VB") || contenitore.equals("VBP")){
                    indW1 = List_nsubj.get(i).getSource(); // salva
                    for(){

                    }
                    indW2 = semanticGraph.getNodeByWordPattern(list.getC_list().get())
                    // trova
//                  List_concat = semanticGraph.getShortestUndirectedPathEdges(indW1,);
                    /*for(int ){}
                    //indW2 = semanticGraph.getNodeByWordPattern();
                }
            }
        }

 */
        }
        for (int i = 0; i < list.getC_list().size(); i++) {
            System.out.print(list.getC_list().get(i).getNome_cangiante() + "\n");
        }


        System.out.println("----- [C2_RULE] risultato -----");
        list.print_c_list();
    }

    /**
     * [C3] : The rule C2 is applied but the direct object is part of
     * a compound noun
     */
    @Override
    public void C3() { // da sistemare
        System.out.println("--------------[C3]--------------");
        //List<SemanticGraphEdge> List_Compound_temp = semanticGraph.findAllRelns("compound");
        System.out.println(List_compound);
        for(int i = 0;i<IndexedWord_list_compound.size();i++){ // su  IndexedWord_list_compound

            for(int j= 0;j<List_compound.size();j++){

                if (IndexedWord_list_compound.get(i).originalText().equals(List_compound.get(j).getSource().originalText())) {
                    String cangiante = List_compound.get(j).getTarget().originalText() +"_"+ IndexedWord_list_compound.get(i).originalText();
                    System.out.println(cangiante);

                    // aggiunta
                    c_obj c = new c_obj(IndexedWord_list_compound.get(i).originalText()); // crazione
                    c.setNome_cangiante(cangiante);
                    c.setNome_second_index(List_compound.get(j).getTarget().originalText());

                    list.getC_list().add(c); // aggiungi il nome composto
                    System.out.println("[C3]: aggiunto compound ->" +" cangiante:"+ c.getNome_cangiante()+ " index: "+ c.getNome_index()+" SecIndex: "+ c.nome_second_index);
                }else{

                }
            }
            // controllare se esiste un compound
            boolean shi = Collections.disjoint(IndexedWord_list_compound,List_compound);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("IndexedWord_list_compound "+ IndexedWord_list_compound);
            System.out.println("List_compound "+ List_compound);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~");
            //-------------------------------------------------
            // controlla se c'è un elemento senza un nome composto
            //-------------------------------------------------
           /* if(Collections.disjoint(IndexedWord_list_compound,List_compound)){
               // IndexedWord elementoInComune = getCommonElement(IndexedWord_list_compound,List_compound);  //not funge !
                for(int j =0;j<IndexedWord_list_compound.size();j++){
                    for(int k = 0;k<List_compound.size();k++){
                        if(IndexedWord_list_compound.get(j).originalText().equals(List_compound.get(k).getSource().originalText())){

                        }
                    }
                }
            }*/
            list.getC_list().add(new c_obj(IndexedWord_list_compound.get(i).originalText()));
            System.out.println("[C3]: no compound addd -> "+ IndexedWord_list_compound.get(i).originalText());
        }


        String concat ="";
        for(int i =0;i<list.getC_list().size();i++){
            concat += list.getC_list().get(i).getNome_cangiante() +",";
        }
        System.out.println("prima -> [ "+ concat +"]");
        //algoritmo controlla & stermina
/*
        int i=0;
        int j=1;

        while(i<list.getC_list().size()){
            while(j<list.getC_list().size()){
                if(list.getC_list().get(i).getNome_index().equals(list.getC_list().get(j).getNome_index())){
                    System.out.println("index: "+list.getC_list().get(j).getNome_index()+ "cangiante" + list.getC_list().get(j).getNome_cangiante());
                    System.out.println("rimosso: "+list.getC_list().get(j).getNome_index());
                    list.getC_list().remove(j);
                }
                j++;
            }
            i++;
            j=i+1;
        }
        /*for(int i =0;i<list.getC_list().size();i++){
            for(int j = 1;i<list.getC_list().size();j++){
                if(Objects.equals(list.getC_list().get(i).getNome_index(), list.getC_list().get(j).getNome_index())){
                    System.out.println("index: "+list.getC_list().get(j).getNome_index()+ "cangiante" + list.getC_list().get(j).getNome_cangiante());
                    System.out.println("rimosso"+list.getC_list().get(j).getNome_index());
                    list.getC_list().remove(j);
                }
            }
        }*/

        concat ="";
       for(int k =0;k<list.getC_list().size();k++){
            concat += list.getC_list().get(k).getNome_cangiante() +",";
       }
        System.out.println("dopo -> [ "+ concat +"]");
    }
        /*
        List_compound = semanticGraph.findAllRelns("compound");
        System.out.println(List_compound); // gestire i compound noun

        for(int i = 0;i<List_compound.size();i++){
            String concat_NN ="";
            try{
                if(List_compound.get(i).getTarget().getOriginal() == List_compound.get(i+1).getSource().getOriginal()){
                    concat_NN += List_compound.get(i).getTarget().getOriginal().toString();
                    //memorizare anche il riferimento originale
                }
            }catch (IndexOutOfBoundsException e){
                System.err.println("for interrotto [C3]."+ " i ="+i+ ", List_compound.size():"+List_compound.size());
                break;
            }
            System.out.println(concat_NN);
        }
        List_case = semanticGraph.findAllRelns("case");
        */
        //------------------------------------------


      /*  for(int j =0;j<List_Compound_temp.size();j++){ // scan lista compound
            if(List_Compound_temp.get(j).getSource().originalText().equals(contenitore.originalText())){ // confrondo con "controllore" con i compound della lista
                // <-- sono arrivato qui !!
            }
        }*/






    /**
     * [C4] The noun that precedes the possessive apostrophe is a
     * class
     * @param
     */
    @Override
    public void C4() {
        System.out.println("--------------[C4]--------------");
        List_case = semanticGraph.findAllRelns("case");
        for(int i =0;i< List_case.size();i++){
            if(List_case.get(i).getSource().tag().equalsIgnoreCase("NN") &&
                    List_case.get(i).getTarget().tag().equalsIgnoreCase("POS")){
                //System.out.println(List_case.get(i).getSource().originalText());
                list.getC_list().add(new c_obj(List_case.get(i).getSource().originalText()));
                //list.getC_list().add(List_case.get(i).getSource().originalText());
                //System.out.println(List_case.get(i).getSource().originalText() +" added");
            }
            //System.out.println("[C4] "+list.getC_list());
        }
        System.out.print("[C4] ");
        for(int i = 0;i<list.getC_list().size();i++){
            System.out.print(list.getC_list().get(i).getNome_cangiante()+ " , ");
        }
        System.out.println("\n");

    }

    @Override
    public void C5() {
      /*  if((List_case.get(i).getSource().tag().equalsIgnoreCase("NNS") || List_case.get(i).getSource().tag().equalsIgnoreCase("NN")
                && List_case.get(i).getTarget().tag().equalsIgnoreCase("IN"))){

            switch(List_case.get(i).getTarget().originalText()){
                case "of":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    System.out.println(List_case.get(i).getSource().originalText() + " added");
                    break;
                case "to":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    System.out.println(List_case.get(i).getSource().originalText() + " added");
                    break;
                case "for":
                    list.getC_list().add(List_case.get(i).getSource().originalText());
                    System.out.println(List_case.get(i).getSource().originalText() + " added");
                    break;
            }

        }
        System.out.println("[C5] "+ list.getC_list()); */

        System.out.println("--------------[C5]--------------");

        List_case = semanticGraph.findAllRelns("case");
        System.out.println(semanticGraph.findAllRelns("case"));
        for (SemanticGraphEdge semanticGraphEdge : List_case) {
            if (semanticGraphEdge.getSource().tag().equals("NN") ||
                    semanticGraphEdge.getSource().tag().equals("NNS")) {
                if (semanticGraphEdge.getTarget().tag().equals("IN")) {
                    switch (semanticGraphEdge.getTarget().originalText()) {
                        case "for", "of", "to" ->
                                list.getC_list().add(new c_obj(semanticGraphEdge.getSource().originalText()));
                    }
                }
            }
        }
        System.out.print("[C5] ");
        for(int i = 0;i<list.getC_list().size();i++){
            System.out.print(list.getC_list().get(i).getNome_cangiante()+ " ,");
        }
        System.out.println();
    }
    @Override
    public void C6() {
        System.out.println("--------------[C6]--------------");
      List_conj = semanticGraph.findAllRelns("conj");
        for (SemanticGraphEdge semanticGraphEdge : List_conj) {
            if ((semanticGraphEdge.getSource().tag().equalsIgnoreCase("NN") || semanticGraphEdge.getSource().tag().equalsIgnoreCase("NNS")) &&
                    (semanticGraphEdge.getTarget().tag().equalsIgnoreCase("NN") || semanticGraphEdge.getTarget().tag().equalsIgnoreCase("NNS"))) {

                c_obj c_source = new c_obj(semanticGraphEdge.getSource().originalText());
                c_obj c_target = new c_obj(semanticGraphEdge.getTarget().originalText());

                if(list.getC_list().contains(c_source)) {
                    list.getC_list().add(new c_obj(semanticGraphEdge.getSource().originalText()));
                }
                if(list.getC_list().contains(c_target)){
                    list.getC_list().add(new c_obj(semanticGraphEdge.getTarget().originalText()));
                }
                System.out.println();
            }
        }
        System.out.print("[C6] ");
        for(int i = 0;i<list.getC_list().size();i++){
            System.out.print(list.getC_list().get(i).getNome_cangiante()+ " ,");
        }
        System.out.println();


        //cancella le duplicazioni
        removeDuplicates(list.getC_list());
        //System.out.println(Arrays.toString(list.getC_list().toArray()));
    }

    @Override
    public void find_component_noun(SemanticGraph semanticGraph) {

    }

    @Override
    public void fix_compound_noun_list(SemanticGraphEdge semanticGraphEdge) {

    }

    /**
     * final_C_remove:
     *
     * rimuove tutti i duplicati all'interno della lista.
     * */

   /* public void final_C_remove(){
        list.setC_list(removeDuplicates(list.getC_list()));
    } */


    private static IndexedWord getCommonElement(List<IndexedWord> list1, List<SemanticGraphEdge> list2){
        IndexedWord ind = null;
        for(IndexedWord indexedWord : list1){
            if(list2.contains(indexedWord)){
                ind = indexedWord;
                break;
            }
        }
        return ind;
    }


    public static List<c_obj> removeDuplicates(List<c_obj> list) {
        // Create a new ArrayList
        List<c_obj> newList = new ArrayList<>();
        // Traverse through the first list
        for (c_obj element : list) {
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
