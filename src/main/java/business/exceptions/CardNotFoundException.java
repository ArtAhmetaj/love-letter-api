package business.exceptions;

public class CardNotFoundException extends BusinessException{
    public CardNotFoundException() {
        error = "card_not_found_exception";
    }
}
