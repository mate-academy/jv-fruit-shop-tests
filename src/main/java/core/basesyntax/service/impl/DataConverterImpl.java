package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.exception.InvalidDataException;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String SEPARATOR = ",";

    @Override
    public List<FruitTransaction> fruitList(List<String> inputInfo) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        for (String line : inputInfo) {
            String[] splitLine = line.split(SEPARATOR);
            dataFormatValidation(line, splitLine);
            OperationType operation = OperationType.getOperation(
                    splitLine[OPERATION_INDEX].trim());
            String fruitName = splitLine[FRUIT_NAME_INDEX].trim();
            int quantity = Integer.parseInt(splitLine[QUANTITY_INDEX]);
            quantityValidation(quantity);
            fruitTransactionList.add(
                    new FruitTransaction(operation, fruitName, quantity));
        }
        return fruitTransactionList;
    }

    private static void quantityValidation(int quantity) {
        if (quantity < 0) {
            throw new InvalidDataException("Quantity of fruits can't be negative");
        }
    }

    private static void dataFormatValidation(String line, String[] splitLine) {
        if (splitLine.length != 3) {
            throw new InvalidDataException("Invalid input data format: " + line);
        }
    }

}
