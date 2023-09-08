package ClientUS.NLP.Other;

public class Actor_of_story {
    //--------------------------------------------------------------------------------------------------
    // DESCRIZIONI VARIABILI:
    // la variable cangiante può assumere qualsiasi nome dureane l'esecuzione delle regole [AC1,AC2,AC3]
    // la variable index può assumere valore solo nella regola [AC1]
    //--------------------------------------------------------------------------------------------------
    String ActorOfStory; // nome [cangiante]
    String Actor_ref; // nome [Index]

    String Second_actor_ref;



    public Actor_of_story(String actorOfStory){
        this.ActorOfStory = actorOfStory; //nome cangiante
        Actor_ref = ""; //riferimento
        Second_actor_ref = "";
    }
    public String getActorOfStory(){
        return ActorOfStory;
    }
    public void setActorOfStory(String x) {
        ActorOfStory = x;
    }

    /**
     * metodo per ottenere il riferimento durante il parsing delle dipendenze.
     *
     * @return String
     */
    public String getActor_ref() {
        return Actor_ref;
    }



    /**
     * metodo per modificare il riferimento. <p>
     * serve
     * @param actor_ref
     */
    public void setActor_ref(String actor_ref) {
        Actor_ref = actor_ref;
    }


    public void setSecondActor_ref(String actor_ref){
         Second_actor_ref = actor_ref;

    }
    public String getSecond_actor_ref(){
        return Second_actor_ref;
    }

    public c_obj toC_obj(){
        c_obj a = new c_obj();
        a.setNome_cangiante(ActorOfStory);
        a.setNome_index(Actor_ref);
        a.setNome_second_index(Second_actor_ref);
        return a;
    }
}
