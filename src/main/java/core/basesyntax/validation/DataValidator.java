package core.basesyntax.validation;

import core.basesyntax.model.Operation;
import java.util.List;

public class DataValidator {
    private static final String COMMA = ",";

    public boolean validate(List<String> inputData) {
        if (inputData == null || inputData.isEmpty()) {
            throw new IllegalArgumentException("Input data cannot be empty");
        }

        validateHeader(inputData);

        for (String line : inputData.subList(1, inputData.size())) {
            String[] partsOfData = line.split(COMMA);
            if (partsOfData.length != 3) {
                throw new IllegalArgumentException("Incorrect input data in line: " + line);
            }

            if (partsOfData[1] == null || partsOfData[1].isEmpty()) {
                throw new IllegalArgumentException("Fruit name cannot be null or empty in line: "
                        + line);
            }

            try {
                Operation.getOperationByCode(partsOfData[0]);
                Integer.parseInt(partsOfData[2]);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid data in line: " + line);
            }
        }

        return true;
    }

    private void validateHeader(List<String> inputData) {
        String header = inputData.get(0);
        if (!header.equals("type,fruit,quantity")) {
            throw new IllegalArgumentException("First line must be 'type,fruit,quantity'");
        }
    }
}
