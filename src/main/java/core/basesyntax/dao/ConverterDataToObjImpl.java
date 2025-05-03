package core.basesyntax.dao;

import core.basesyntax.FruitTransaction;
import java.util.List;

public class ConverterDataToObjImpl {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_SEQUENCE = 0;
    private static final int FRUIT_NAME_SEQUENCE = 1;
    private static final int FRUIT_QUANTITY_SEQUENCE = 2;
    private static final String REGEX = "^[a-zA-Z],[^,]+,\\d+$";

    public List<FruitTransaction> convertAll(List<String> dataFromFile) {
        for (String line : dataFromFile) {
            if (!line.matches(REGEX)) {
                throw new RuntimeException("Incorrect type of string");
            }
        }
        return dataFromFile.stream()
                .map(line -> line.split(SEPARATOR))
                .map(fruitTransaction -> new FruitTransaction(fruitTransaction[OPERATION_SEQUENCE],
                        fruitTransaction[FRUIT_NAME_SEQUENCE],
                        Integer.parseInt(fruitTransaction[FRUIT_QUANTITY_SEQUENCE])))
                .toList();
    }
}
