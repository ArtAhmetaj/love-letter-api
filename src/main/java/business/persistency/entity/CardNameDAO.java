package business.persistency.entity;

public enum CardNameDAO {
    //TODO: add all
    QUEEN("Queen"),
    KING("King");

    public final String label;


    private CardNameDAO(String label) {
        this.label = label;
    }
}