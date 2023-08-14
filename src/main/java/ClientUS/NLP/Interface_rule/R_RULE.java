package ClientUS.NLP.Interface_rule;

public interface R_RULE {


    /**
     * R1:  Verb (phrase) linking the subject and an object
     */
    void R1();

    /**
     * [R2] verb with preposition
     *
     * questa regola crea la funzione in relazione. non la relazione stessa
     *  pdf: Regole [R2], Regole2 [R4]
     */
    void R2();

    /**
     * R3: Prepositions like "of ","to", "for" and "about" detect an association.
     * pdf: regole [R1]
     *
     *
     * utilità: rinforza la regola [R2]
     */
    void R3();

    /**
     * R3:  Noun–noun compound
     * pdf: regole [R5]
     *
     */
    void R4();


    /**
     * R5: Possessive case: "s’", "my", "his"… determine an association; Correlation of pronouns with nouns to extract the associations using
     * coreference resolution of Stanford core NLP tool
     * pdf: regole [R4]
     *
     *
     * utilità: aggiunta opt
     */

}
