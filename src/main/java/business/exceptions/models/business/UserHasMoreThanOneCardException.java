package business.exceptions.models.business;

public class UserHasMoreThanOneCardException extends BusinessException{
    public UserHasMoreThanOneCardException(){
        error="user_has_more_than_one_card_exception";
    }

}
