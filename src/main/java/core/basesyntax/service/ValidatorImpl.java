package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;

public class ValidatorImpl implements Validator {
    private static final int COLUMNS_NUMBER = 3;
    private static final int FRUIT_AMOUNT_INDEX = 2;
    private static final int TITLE_LINE = 0;

    @Override
    public boolean isDataValid(List<String> inputDataFromFile) {
        List<String> inputData = new ArrayList<>(inputDataFromFile);
        if (inputData.stream()
                .map(s -> s.split(","))
                .anyMatch(strings -> strings.length == COLUMNS_NUMBER)) {
            return true;
        }
        throw new RuntimeException("Amount of columns is incorrect");
    }

    @Override
    public boolean checkValidOfAmount(List<String> inputDataFromFile) {
        List<String> inputData = new ArrayList<>(inputDataFromFile);
        inputData.remove(TITLE_LINE);
        isDataValid(inputDataFromFile);
        for (String line : inputData) {
            if (Integer.parseInt(line.split(",")[FRUIT_AMOUNT_INDEX]) < 0) {
                throw new RuntimeException("Fruit amount can't be less than 0");
            }
        }
        return true;
    }
}
