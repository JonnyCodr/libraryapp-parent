package category.exceptions;

public class FieldNotValidException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    private final String fieldName;


    public FieldNotValidException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "FieldNotValidException{" +
                "fieldName='" + fieldName + '\'' +
                '}';
    }
}
