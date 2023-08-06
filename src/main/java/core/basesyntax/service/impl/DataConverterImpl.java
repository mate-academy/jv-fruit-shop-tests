package core.basesyntax.service.impl;

import core.basesyntax.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String DELIMITER = ",";
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int OFFSET = 1;

    @Override
    public List<FruitTransaction> convertData(List<String> dataFromFile) {
        checkForNullOrEmpty(dataFromFile);
        List<FruitTransaction> result = new ArrayList<>();
        for (int i = OFFSET; i < dataFromFile.size(); i++) {
            if (dataFromFile.get(i).isEmpty()) {
                break;
            }
            String[] splitData = dataFromFile.get(i).trim().split(DELIMITER);
            validateData(splitData);
            result.add(new FruitTransaction(splitData[OPERATION_TYPE_INDEX],
                    splitData[FRUIT_INDEX],
                    Integer.parseInt(splitData[QUANTITY_INDEX])));
        }
        return result;
    }

    private void validateData(String[] splitData) {
        if (!Arrays.asList(FruitTransaction.Operation.values())
                .stream()
                .map(operation -> operation.getCode())
                .anyMatch(string -> string.equals(splitData[OPERATION_TYPE_INDEX]))) {
            throw new FruitShopException("operation with code "
                    + splitData[OPERATION_TYPE_INDEX] + " does not exist");
        }
        if (splitData.length != 3
                || !splitData[OPERATION_TYPE_INDEX].matches("^[a-z]{1}$")// one lowercase character
                || !splitData[FRUIT_INDEX].matches("^[a-z]+$")// lowercase word
                || !splitData[QUANTITY_INDEX].matches("^[1-9]\\d*$")) { // only digits
            throw new FruitShopException("The data must be in the format \"s,fruits,100\". "
                    + "Where \"c\" is the opcode in lower case, "
                    + "\"fruit\" is the name of the fruit in lower case, "
                    + "\"100\" is a amount (must be greater than 0)");
        }
    }

    private void checkForNullOrEmpty(List<String> dataFromFile) {
        if (dataFromFile == null) {
            throw new FruitShopException("The data cannot be null");
        }
        if (dataFromFile.isEmpty()) {
            throw new FruitShopException("The data cannot be empty");
        }
    }
}
