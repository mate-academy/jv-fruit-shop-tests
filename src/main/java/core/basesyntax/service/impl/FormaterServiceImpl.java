package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FormaterService;
import java.util.List;

public class FormaterServiceImpl implements FormaterService {

    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String SPLITERATOR = ",";

    @Override
    public List<FruitTransaction> form(List<String> stringList) {
        return stringList.stream()
                .map(this::createFruitTransaction)
                .toList();
    }

    private FruitTransaction createFruitTransaction(String line) {
        String[] splitedLine = line.split(SPLITERATOR);
        if (splitedLine.length != 3) {
            throw new InvalidDataException("Invalid data format. Expected three values");
        }
        Operation operation = Operation.getOperationByCode(splitedLine[OPERATION_INDEX].trim());
        String fruit = splitedLine[FRUIT_NAME_INDEX].trim();
        int quantity = this.validateQuantity(splitedLine[QUANTITY_INDEX]);
        return new FruitTransaction(operation, fruit, quantity);
    }

    private int validateQuantity(String strQuantity) {
        int quantity;
        try {
            quantity = Integer.parseInt(strQuantity);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Quantity field must be numerical only: " + strQuantity);
        }
        if (quantity < 0) {
            throw new InvalidDataException("Quantity can't be negative. actually: " + quantity);
        }
        return quantity;
    }
}
