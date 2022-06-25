package business.TO.model;

public enum CardNameTO {
    //TODO: add all
    QUEEN("Queen"),
    KING("King"),
    GUARD("Guard"),
    HANDMAID("Handmaid"),
    PRIEST("Priest"),
    BARON("Baron"),
    PRINCE("Prince"),
    COUNTESS("Countess"),
    PRINCESS("Princess");

    public final String label;


    private CardNameTO(String label) {
        this.label = label;
    }
}