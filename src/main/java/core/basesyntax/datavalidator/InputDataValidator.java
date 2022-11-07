package core.basesyntax.datavalidator;

import java.util.List;

public class InputDataValidator {
    private static final int FIRST_LINE_INDEX = 0;
    private static final String HEADLINE = "type,fruit,quantity";

    public void validate(List<String> inputData) throws InputDataValidationException {
        if (inputData == null || inputData.size() == 0) {
            throw new InputDataValidationException("Given input data can't be null or empty");
        }
        if (!inputData.get(FIRST_LINE_INDEX).equals(HEADLINE)) {
            throw new InputDataValidationException("Your input list should start with a headline: "
                    + HEADLINE);
        }
    }
}
