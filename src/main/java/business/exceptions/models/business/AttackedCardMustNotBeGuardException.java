package business.exceptions.models.business;

public class AttackedCardMustNotBeGuardException extends BusinessException{
    public AttackedCardMustNotBeGuardException(){
        error = "attacked_card_must_not_be_guard_exception";
    }
}
