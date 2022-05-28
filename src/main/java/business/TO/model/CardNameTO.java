package business.TO.model;

public enum CardNameTO {
    //TODO: add all
    QUEEN("Queen"),
    KING("King");

    public final String label;


    private CardNameTO(String label) {
        this.label = label;
    }
}