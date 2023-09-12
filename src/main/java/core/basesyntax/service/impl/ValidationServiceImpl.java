package core.basesyntax.service.impl;

import core.basesyntax.service.ValidationService;
import java.util.List;

public class ValidationServiceImpl implements ValidationService {
    private static final int CODE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String DATA_SEPARATOR = ",";
    private static final String HEADER_CONTENT = "type,fruit,quantity";

    @Override
    public void validateInputData(List<String> inputList) {
        if (inputList == null || inputList.size() < 2) {
            throw new RuntimeException("There is no input data");
        }

        if (!inputList.get(0).trim().equals(HEADER_CONTENT)) {
            throw new RuntimeException("Invalid header");
        }

        for (int i = 1; i < inputList.size(); i++) {
            String[] entryData = inputList.get(i).split(DATA_SEPARATOR);
            if (entryData.length != 3
                    || entryData[CODE_INDEX].trim().length() != 1
                    || !entryData[CODE_INDEX].trim().matches("[bsrp]")
                    || entryData[FRUIT_INDEX].trim().isEmpty()
                    || !entryData[QUANTITY_INDEX].matches("[0-9]+")) {
                throw new RuntimeException("Invalid input data format");
            }
        }
    }
}
