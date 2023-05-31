package core.basesyntax.datavalidator;

import java.util.List;

public class InputDataValidator {
    private static final int FIRST_LINE_INDEX = 0;
    private static final String HEADLINE = "type,fruit,quantity";

    public void validateInput(List<String> inputData) throws DataValidationException {
        if (inputData == null || inputData.size() == 0) {
            throw new DataValidationException("Given input data can't be null or empty");
        }
        if (!inputData.get(FIRST_LINE_INDEX).equals(HEADLINE)) {
            throw new DataValidationException("Your input list should start with a headline: "
                    + HEADLINE);
        }
    }
}
