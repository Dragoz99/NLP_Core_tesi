package ClientUS.NLP.Rule_EX;

import ClientUS.NLP.Interface_rule.H_RULE;
import ClientUS.NLP.Liste;
import ClientUS.NLP.Other.h_rel;
import ClientUS.NLP.Other.h_rel_new;
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
    List<SemanticGraphEdge> temp_list_ccomp;

    h_rel_new hRelNew;




    public H_EX(Liste liste, SemanticGraph semanticGraph){
        this.semanticGraph = semanticGraph;
        this.liste = liste;
        hRelNew = new h_rel_new();
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
        temp_list_ccomp = semanticGraph.findAllRelns("ccomp");

        //temp_list_nmodOF = semanticGraph.findAllRelns("nmod:of");

        System.out.println(temp_list_obj);
        System.out.println(temp_list_acl);
        //System.out.println(temp_list_nmodOF);
        System.out.println(temp_list_nmod);
        //System.out.println("specific "+temp_list_nmod.get(0).getRelation().getSpecific());
        //System.out.println(temp_list_nmod.get(0).getSource().originalText());


        //-----------------------------
        //part_of
        //----------------------------


        System.out.println("[--- PART OF ---]");
        for(SemanticGraphEdge nmodOFselector : temp_list_nmod){

            System.out.println(nmodOFselector.getSource().originalText());
            if((Objects.equals(nmodOFselector.getRelation().getSpecific(), "of")) && (Objects.equals(nmodOFselector.getSource().originalText(), "part"))){
                for(SemanticGraphEdge aclSelector: temp_list_acl){
                    if(nmodOFselector.getSource().index() == aclSelector.getTarget().index()){ // esiste una relazione ACL:relcl? con target NN? con index uguali
                           hRelNew.getClass_1().add(nmodOFselector.getTarget().originalText());
                           hRelNew.getClass_2().add(aclSelector.getSource().originalText());
                           hRelNew.getType().add("composition");
                           System.out.println("["+nmodOFselector.getTarget().originalText()+","+aclSelector.getSource().originalText()+", composition ]");
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
                                hRelNew.getClass_1().add(obj_sem.getSource().originalText());
                                hRelNew.getClass_2().add(nmod_sem.getTarget().originalText());
                                hRelNew.getType().add("composition");
                            }
                        }
                    }
                }
            }
        }
        //----------------------------------
        //              comprise
        //----------------------------------
        System.out.println("--- comprise ---");
        System.out.println(" nsubj "+temp_list_nsubj);
        System.out.println(" obj "+temp_list_obj);

        for(SemanticGraphEdge obj_sem : temp_list_obj){
            if(Objects.equals(obj_sem.getSource().lemma(), "comprise")){ //selezionato [comprise]
                System.out.println("[---lemma---]");
                System.out.println(obj_sem.getSource().lemma());
                String sec_comprise_h = obj_sem.getTarget().originalText();
                System.out.println("second comp : "+ sec_comprise_h);
                for (SemanticGraphEdge nsubj : temp_list_nsubj){
                    if(Objects.equals(nsubj.getSource().originalText(), obj_sem.getSource().originalText())){
                        String prim_comprise_h = nsubj.getTarget().originalText();
                        h_rel hRel = new h_rel(prim_comprise_h,sec_comprise_h,"composition");
                        System.out.println("["+hRel.getClasse_1()+","+hRel.getClasse_2()+"]");
                        liste.add_item_h_list(hRel);
                    }
                }
            }
        }




        // ciclo di obj
        // if(VB == "comprise") ->
            // obj.target = secondo componente di h
                //ciclo di nsubj:xsubj
                // if()


        ///-----------------------------------
        //   have.... working in progress...
        //     c'è un problema di fondo.
        // bisogna gestire il caso      (Have to have) HTH
        //-----------------------------------


        // INSOSPESO

        System.out.println("--- have ---");
        for(SemanticGraphEdge obj_sem: temp_list_obj){


            if(Objects.equals(obj_sem.getSource().lemma(), "have")){
                System.out.println("--- lemma ---");
                System.out.println(obj_sem.getSource().lemma());
                for(SemanticGraphEdge ccomp: temp_list_ccomp){
                    //HTH
                    if(ccomp.getSource().lemma() == ccomp.getTarget().lemma()){
                        for(SemanticGraphEdge obj_sem2: temp_list_obj){
                            if(ccomp.getTarget().index() == obj_sem2.getSource().index()){
                                System.out.println("["+liste.getActor_of_story()+","+obj_sem2.getSource()+"]");
                                liste.add_item_h_list(new h_rel(liste.getActor_of_story(), obj_sem2.getSource().originalText(),"composition"));
                            }
                        }
                    }
                }
                // manca il caso base e testarlo
                // poi si passa a H2
            }
        }
        //-----------------------------------
        //              contain
        //-----------------------------------
        System.out.println("--- contain  ---");
        for(SemanticGraphEdge obj_sem : temp_list_obj){
            if(Objects.equals(obj_sem.getSource().lemma(), "contain")){ //selezionato [comprise]
                System.out.println("[---lemma---]");
                System.out.println(obj_sem.getSource().lemma());
                String sec_comprise_h = obj_sem.getTarget().originalText();
                System.out.println("second comp : "+ sec_comprise_h);
                for (SemanticGraphEdge nsubj : temp_list_nsubj){
                    if(Objects.equals(nsubj.getSource().originalText(), obj_sem.getSource().originalText())){
                        String prim_comprise_h = nsubj.getTarget().originalText();
                        h_rel hRel = new h_rel(prim_comprise_h,sec_comprise_h,"composition");
                        System.out.println("["+hRel.getClasse_1()+","+hRel.getClasse_2()+"]");
                        liste.add_item_h_list(hRel);
                    }
                }
            }
        }


        //-----------------------------------
        //              include
        //-----------------------------------

        System.out.println("--- include ---");
        for(SemanticGraphEdge obj_sem : temp_list_obj){
            if(Objects.equals(obj_sem.getSource().lemma(), "include")){ //selezionato [comprise]
                System.out.println("[---lemma---]");
                System.out.println(obj_sem.getSource().lemma());
                String sec_comprise_h = obj_sem.getTarget().originalText();
                System.out.println("second comp : "+ sec_comprise_h);
                for (SemanticGraphEdge nsubj : temp_list_nsubj){
                    if(Objects.equals(nsubj.getSource().originalText(), obj_sem.getSource().originalText())){
                        String prim_comprise_h = nsubj.getTarget().originalText();
                        h_rel hRel = new h_rel(prim_comprise_h,sec_comprise_h,"composition");
                        System.out.println("["+hRel.getClasse_1()+","+hRel.getClasse_2()+"]");
                        liste.add_item_h_list(hRel);
                    }
                }
            }
        }


        //-----------------------------------
        //           consist
        //-----------------------------------
        System.out.println("--- consist ---");
        for(SemanticGraphEdge obl_sem : temp_list_obl){
            if(Objects.equals(obl_sem.getSource().lemma(), "consist")){ //selezionato [comprise]
                System.out.println("[---lemma---]");
                System.out.println(obl_sem.getSource().lemma());
                String sec_comprise_h = obl_sem.getTarget().originalText();
                System.out.println("second comp : "+ sec_comprise_h);
                for (SemanticGraphEdge nsubj : temp_list_nsubj){
                    if(Objects.equals(nsubj.getSource().originalText(), obl_sem.getSource().originalText())){
                        String prim_comprise_h = nsubj.getTarget().originalText();
                        h_rel hRel = new h_rel(prim_comprise_h,sec_comprise_h,"composition");
                        System.out.println("["+hRel.getClasse_1()+","+hRel.getClasse_2()+"]");
                        liste.add_item_h_list(hRel);
                    }
                }
            }
        }



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
