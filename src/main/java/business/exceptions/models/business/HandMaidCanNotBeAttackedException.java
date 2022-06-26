package business.exceptions.models.business;

public class HandMaidCanNotBeAttackedException extends  BusinessException{
    public HandMaidCanNotBeAttackedException(){
        error = "handmaid_can_not_be_attacked_exception";
    }
}
