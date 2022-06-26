package business.exceptions.models.business;

public abstract class BusinessException extends RuntimeException {
    //TODO: need to add more types of exceptions for global handler
    protected String error;

    public String getError(){
        return this.error;
    }

}
