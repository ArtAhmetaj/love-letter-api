package business.exceptions;

public class JsonSerializationException extends BusinessException {
    public JsonSerializationException() {
        error = "json_serialization_error";
    }
}
