package core.basesyntax.service.validation;

public class ValidatorImpl implements Validator {
    private static final int MAX_LENGTH = 3;
    private static final int AMOUNT_INDEX = 2;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int OPERATION_INDEX = 0;

    @Override
    public boolean validate(String[] dataFromFile) {
        if (dataFromFile.length != MAX_LENGTH
                || Integer.parseInt(dataFromFile[AMOUNT_INDEX]) < 0) {
            throw new RuntimeException("Incorrect data in file");
        }
        try {
            Integer.parseInt(dataFromFile[FRUIT_NAME_INDEX]);
            Integer.parseInt(dataFromFile[OPERATION_INDEX]);
            throw new RuntimeException("incorrect data in file");
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
