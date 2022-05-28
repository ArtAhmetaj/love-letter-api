package business.exceptions;

public class JsonSerializationException  extends  BusinessException{
    public JsonSerializationException(){
        this.error = "json_serialization_error";
    }
}
