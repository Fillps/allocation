package tcp_trb_final.allocator;

public interface Requirement {

   /**
    * Verify if the requirement is the same class as the caller and evaluate if the requirement is fulfilled.
    *
    * @param o
    * @return 0 => different class
    *         < => 0 decline
    *         > => 0 accepts
    */
   int verify(Requirement o);

   /**
    * Returns if the requirement needs to be saved in another location.
    *
    * @return 0 => false
    *         > 0 => save in the origin
    *         < 0 => in a new location
    */
   int needsSaving();

    /**
     *
     * @return null if doesn't have the answer
     *         String if has the answer
     */
   String answer(); //que sala esta sendo alocada?

   boolean isExclusive();

   boolean setAnswer(String answer);
}
