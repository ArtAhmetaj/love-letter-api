package business.exceptions.business;

public class GuardAttackedException extends BusinessException{
    public GuardAttackedException(){
        error = "guard_attacked_exception";
    }
}
