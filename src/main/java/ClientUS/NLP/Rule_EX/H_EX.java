package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Interface_rule.H_RULE;
import ClientUS.NLP.Liste;
import ClientUS.NLP.Other.h_rel;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.List;
import java.util.Objects;

public class H_EX implements H_RULE {


    Liste liste;
    SemanticGraph semanticGraph;
    h_rel h;
    List<SemanticGraphEdge> temp_list_obj;
    List<SemanticGraphEdge> temp_list_acl;
    List<SemanticGraphEdge> temp_list_obl;

    List<SemanticGraphEdge> temp_list_nmod;
    List<SemanticGraphEdge> temp_list_nmodOF;
    List<SemanticGraphEdge> temp_list_nsubj;



    public H_EX(Liste liste, SemanticGraph semanticGraph){
        this.semanticGraph = semanticGraph;
        this.liste = liste;
        H1();
        H2();
        H3();
    }

    /**
     *
     * [H1]
     * The following verbs indicate a relation of composition: "Contain, part of, consist, include, have, comprise…"
     */
    @Override
    public void H1() {
        temp_list_obj = semanticGraph.findAllRelns("obj");
        temp_list_acl = semanticGraph.findAllRelns("acl:relcl");
        temp_list_obl = semanticGraph.findAllRelns("obl");
        temp_list_nmod = semanticGraph.findAllRelns("nmod");
        temp_list_nsubj = semanticGraph.findAllRelns("nsubj");
        //temp_list_nmodOF = semanticGraph.findAllRelns("nmod:of");

        System.out.println(temp_list_obj);
        System.out.println(temp_list_acl);
        //System.out.println(temp_list_nmodOF);
        System.out.println(temp_list_nmod);
       // System.out.println("specific "+temp_list_nmod.get(0).getRelation().getSpecific());
      //  System.out.println(temp_list_nmod.get(0).getSource().originalText());


        //-----------------------------
        //part_of
        //----------------------------


        System.out.println("[--- PART OF ---]");
        for(SemanticGraphEdge nmodOFselector : temp_list_nmod){

            System.out.println(nmodOFselector.getSource().originalText());
            if((Objects.equals(nmodOFselector.getRelation().getSpecific(), "of")) && (Objects.equals(nmodOFselector.getSource().originalText(), "part"))){
                System.out.println("ok boss");
                for(SemanticGraphEdge aclSelector: temp_list_acl){
                    if(nmodOFselector.getSource().index() == aclSelector.getTarget().index()){ // esiste una relazione ACL:relcl? con target NN? con index uguali
                            System.out.println("ok il prezzo è giusto");
                           liste.add_item_h_list(
                                   new h_rel(
                                   nmodOFselector.getTarget().originalText(),
                                   aclSelector.getSource().originalText(),
                                   "composition"));
                           liste.print_h_list();
                    }
                }
            }else{
                System.out.println("not funge");
            }
        }


        //-----------------------------------
        //             consist
        //-----------------------------------
        System.out.println("[R3]---[consist]");
        System.out.println(temp_list_obl);
        for (SemanticGraphEdge obj_sem : temp_list_obl) {
            System.out.println("-------lemma------");
            System.out.println(obj_sem.getSource().lemma());
            // in questo caso consist se è prulare, allora ci saranno moteplici H da salvare
            if (obj_sem.getSource().lemma() == "consist") {
                for (SemanticGraphEdge acl_sem : temp_list_acl) {
                    if (acl_sem.getTarget().index() == obj_sem.getSource().index()) { // se esiste una relazione acl:relcl
                        System.out.println("ok: Acl_sem");
                        for (SemanticGraphEdge nmod_sem : temp_list_nmod) {
                            if (obj_sem.getTarget().index() == nmod_sem.getSource().index()) { // i due index combaciano
                                liste.add_item_h_list(
                                        new h_rel(
                                                obj_sem.getSource().originalText(),
                                                nmod_sem.getTarget().originalText(),
                                                "composition"));


                            }
                        }
                    }
                }
            }
        }
        //----------------------------------
        //              comprise
        //----------------------------------
        System.out.println("[R3]---[comprise]");
        System.out.println(temp_list_obj);
        for(SemanticGraphEdge obj_sem: temp_list_obj){
            System.out.println("---- lemma ----");
            System.out.println(obj_sem.getSource().lemma());

            if(Objects.equals(obj_sem.getSource().lemma(), "comprise")){

                for(SemanticGraphEdge nsubj_sem : temp_list_nsubj){
                    if(nsubj_sem.getSource().index() == obj_sem.getSource().index()){

                    }

                }

            }

        }




        // ciclo di obj
        // if(VB == "comprise") ->
            // obj.target = secondo componente di h
                //ciclo di nsubj:xsubj
                // if()


        //-----------------------------------
        //   have.... working in progress...
        //     c'è un problema di fondo.
        //-----------------------------------




        //-----------------------------------
        //              contain
        //-----------------------------------



        //-----------------------------------
        //              include
        //-----------------------------------



            /*switch (obj_sem.getSource().lemma()) {







                case "contain":

                case "include":
                    for (int j = 0; j < temp_list_acl.size(); j++) {
                        if (temp_list_acl.get(j).getTarget().index() == obj_sem.getSource().index()) {
                            liste.add_item_h_list(new h_rel(
                                    temp_list_acl.get(j).getSource().originalText(),
                                    temp_list_obj.get(j).getTarget().originalText(),
                                    "composition"));
                        }
                    }
                    break;
                //liste.add_item_h_list(new h_rel());
            }*/
        }


    @Override
    public void H2() {

    }

    @Override
    public void H3() {

    }

}
