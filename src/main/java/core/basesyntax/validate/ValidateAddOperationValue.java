package core.basesyntax.validate;

public class ValidateAddOperationValue implements ValidationAddOperation {
    private static final String EXCEPTION_MESSAGE = "Illegal quantity";

    @Override
    public boolean validateAddOperation(Integer quantity) {
        if (quantity <= 0) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
        return true;
    }
}
