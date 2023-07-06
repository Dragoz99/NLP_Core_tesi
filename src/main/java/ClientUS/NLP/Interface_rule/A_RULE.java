package ClientUS.NLP.Interface_rule;

public interface A_RULE {


    /**
     * A1 : Adjective - Attribute of noun phrase main
     *
     * pdf: Regole 2
     */
    void A1();

    /**
     * A2:  Adverb modifying a verb -
     * Relationship attribute
     *
     * pdf: Regole 2
     */
    void A2();

    /**
     * A3: Possessive apostrophe -
     * Entity attribute
     *
     * pdf: Regole 2
     */
    void A3();

    /**
     * A4: Genitive case -
     * Entity attribute
     *
     * pdf: Regole 2
     */
    void A4();


    /**
     * A5:  Verb "to have"
     * Entity attribute
     *
     * pdf: Regole 2
     */
    void A5();


    /**
     * A6:  Specific indicators (e.g., "number", "date", "type",...)
     * Entity attribute
     *
     * pdf: Regole 2
     */
    void A6();


    /**
     * A7:  Object of numeric/algebraic operation
     * Entity attribute
     */
    void A7();
}
