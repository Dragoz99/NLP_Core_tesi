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

        liste.sethRelNew(hRelNew);

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
        temp_list_nsubj = semanticGraph.findAllRelns("nsubj:xsubj");
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
                    if(nmodOFselector.getSource().index() != aclSelector.getTarget().index()){ // esiste una relazione ACL:relcl? con target NN? con index uguali

                        hRelNew.addClass1(nmodOFselector.getTarget().originalText());
                        hRelNew.addClass2(aclSelector.getSource().originalText());
                        hRelNew.addType("composition");


                        System.out.println("["+hRelNew.getClass_1().get(hRelNew.getClass_1().size()-1)+
                                ","+hRelNew.getClass_1().get(hRelNew.getClass_2().size()-1)+
                                ",composition]");
                         /*  hRelNew.getClass_1().add(nmodOFselector.getTarget().originalText());
                           hRelNew.getClass_2().add(aclSelector.getSource().originalText());
                           hRelNew.getType().add("composition");*/

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
                    if (acl_sem.getTarget().index() != obj_sem.getSource().index()) { // se esiste una relazione acl:relcl
                        System.out.println("ok: Acl_sem");
                        for (SemanticGraphEdge nmod_sem : temp_list_nmod) {

                            if (obj_sem.getTarget().index() != nmod_sem.getSource().index()) { // i due index combaciano

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

                    if(nsubj.getSource().index() == obj_sem.getSource().index()){
                   // if(Objects.equals(nsubj.getSource().originalText(), obj_sem.getSource().originalText())){
                        String prim_comprise_h = nsubj.getTarget().originalText();

                        hRelNew.getClass_1().add(prim_comprise_h);
                        hRelNew.getClass_2().add(sec_comprise_h);
                        hRelNew.getType().add("composition");

                        System.out.println("aggiunto: ["+prim_comprise_h+","+sec_comprise_h+",composition]");
                    }
                }
            }
        }
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
                                hRelNew.getClass_1().add(liste.getActor_of_story().getActor_ref());
                                hRelNew.getClass_2().add(obj_sem2.getSource().originalText());
                                hRelNew.getType().add("composition");
                                System.out.println("aggiunto ["+liste.getActor_of_story()+","+obj_sem2.getSource().originalText()+",composition]");

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
                    if(nsubj.getSource().originalText() != obj_sem.getSource().originalText()){
                        String prim_comprise_h = nsubj.getTarget().originalText();


                        hRelNew.getClass_1().add(prim_comprise_h);
                        hRelNew.getClass_2().add(sec_comprise_h);
                        hRelNew.getType().add("composition");

                        System.out.println(" aggiunto h_ex ["+prim_comprise_h+","+sec_comprise_h+",composition]");
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
                    System.out.println("nsubj "+nsubj.getSource().originalText());
                    System.out.println("obj "+obj_sem.getSource().originalText());
                    if(nsubj.getSource().originalText() == obj_sem.getSource().originalText()){
                        String prim_comprise_h = nsubj.getTarget().originalText();
                       // h_rel hRel = new h_rel(prim_comprise_h,sec_comprise_h,"composition");

                        hRelNew.getClass_1().add(prim_comprise_h);
                        hRelNew.getClass_2().add(sec_comprise_h);
                        hRelNew.getType().add("composition");

                        System.out.println("["+prim_comprise_h+","+sec_comprise_h+", composition]");

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

                        hRelNew.getClass_1().add(prim_comprise_h);
                        hRelNew.getClass_2().add(sec_comprise_h);
                        hRelNew.getType().add("composition");

                        // h_rel hRel = new h_rel(prim_comprise_h,sec_comprise_h,"composition");
                        // System.out.println("["+hRel.getClasse_1()+","+hRel.getClasse_2()+"]");
                        // liste.add_item_h_list(hRel);
                        System.out.println("aggiunto: ["+prim_comprise_h+","+sec_comprise_h+",composition ]");
                    }
                }
            }
        }

    }


    @Override
    public void H2() {
        System.out.println("[--------H2--------]");

        String classH2_primo ="";
        String classH2_sec ="";

        liste.print_c_list();
        System.out.println("~~~~~~~~");

        for(int i = 0;i<liste.getC_list().size();i++){
            for(int j =i+1; j <liste.getC_list().size();j++){
                   /* System.out.println("primo -> "+liste.getC_list().get(i).getNome_second_index());
                    System.out.println("secondo -> "+liste.getC_list().get(j).getNome_second_index()); */
                if(Objects.equals(liste.getC_list().get(i).getNome_second_index(), liste.getC_list().get(j).getNome_second_index())){

                    classH2_primo = liste.getC_list().get(i).getNome_second_index();
                    classH2_sec = liste.getC_list().get(j).getNome_second_index();

                    System.out.println("[ "+classH2_primo+" , "+classH2_sec+" ]");

                    hRelNew.getClass_1().add(classH2_primo);
                    hRelNew.getClass_2().add(classH2_sec);
                    hRelNew.getType().add("composition");

                    System.out.println(" aggiunto ["+classH2_primo+","+classH2_sec+",composition]");
                }
            }
        }




    }

    @Override
    public void H3() {
        System.out.println("[---------H3----------]");
        List<String> verb_aggregation = List.of(new String[]{
                "participate"});
        // POSSIAMO METTERE WORDNET MA diventerebbe un casino adesso a scriverlo

        String classe_2 ="";
        String classe_1 ="";
        for(SemanticGraphEdge obl_sem: temp_list_obl){
            if (Objects.equals(obl_sem.getSource().lemma(), "participate")){
                classe_2 = obl_sem.getTarget().originalText();
            }
        }
        for(SemanticGraphEdge nsubj_sem: temp_list_nsubj ){
            if (Objects.equals(nsubj_sem.getSource().lemma(), "participate")){
                for(int i = 0;i<liste.getC_list().size();i++){
                    if(Objects.equals(liste.getC_list().get(i).getNome_index(), nsubj_sem.getTarget().originalText())){
                        classe_1 = nsubj_sem.getTarget().originalText();
                    }
                }
            }
        }

        if(Objects.equals(classe_1, "")){
            classe_1 = liste.getActor_of_story().getActor_ref();
        }

        if ((!Objects.equals(classe_1, ""))&& (!Objects.equals(classe_2, "")) ){
            hRelNew.getClass_1().add(classe_1);
            hRelNew.getClass_2().add(classe_2);
            hRelNew.getType().add("aggregation");
            //liste.add_item_h_list(new h_rel(classe_1,classe_2,"aggregation"));

            System.out.println("aggiunto ["+classe_1+","+classe_2+",aggregation]");

        }
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        hRelNew.print();
        //liste.print_h_list();
    }

    /**
     * If the subject and the direct object of the sentence are classes, and the verb that connects the two objects is "to be", then the class (the
     * object) is the parent class, this rule determines inheritance relationships
     */
    public void H4(){



    }


}
