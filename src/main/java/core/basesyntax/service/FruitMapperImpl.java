package core.basesyntax.service;

import core.basesyntax.data.FruitTransaction;
import core.basesyntax.data.FruitTransaction.Operation;
import java.util.ArrayList;
import java.util.List;

public class FruitMapperImpl implements FruitMapper {
    private static final int MIN_SIZE_LIST = 2;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int QUANTITY_ZERO = 0;
    private static final int QUANTITY_INDEX = 2;
    private static final int HEAD_LINE_INDEX = 0;
    private static final String NULL_VALUE = "null";
    private static final String REGEX_COMMA = ",";
    private static final String EXCEPTION_INPUT_NULL_MESSAGE = "The input list is null";
    private static final String EXCEPTION_INPUT_EMPTY_MESSAGE = "The input list is empty";
    private static final String EXCEPTION_TYPE_NULL_MESSAGE = "The type is null";
    private static final String EXCEPTION_TYPE_OPERATION_WRONG_MESSAGE =
            "The type of the operation isn't correct";
    private static final String EXCEPTION_QUANTITY_ZERO_MESSAGE = "The quantity is zero";
    private static final String EXCEPTION_HEAD_ABSENT_MESSAGE = "The head is absent";
    private static final String HEAD = "type,fruit,quantity";

    @Override
    public List<FruitTransaction> mapData(List<String> lines) {
        if (lines == null) {
            throw new RuntimeException(EXCEPTION_INPUT_NULL_MESSAGE);
        }
        if (lines.size() < MIN_SIZE_LIST) {
            throw new RuntimeException(EXCEPTION_INPUT_EMPTY_MESSAGE);
        }
        if (!lines.contains(HEAD) || lines.indexOf(HEAD) != OPERATION_INDEX) {
            throw new IllegalArgumentException(EXCEPTION_HEAD_ABSENT_MESSAGE);
        } else {
            lines.remove(HEAD_LINE_INDEX);
        }
        List<FruitTransaction> fruitTransactionsList = new ArrayList<>();
        boolean typeOperationIsExist = false;
        for (String line : lines) {
            String[] data = line.split(REGEX_COMMA);
            String fruitType = data[FRUIT_TYPE_INDEX];
            if (fruitType.equals(NULL_VALUE)) {
                throw new IllegalArgumentException(EXCEPTION_TYPE_NULL_MESSAGE);
            }
            Operation operation = Operation.getOperationByCode(data[OPERATION_INDEX]);
            for (FruitTransaction.Operation operationValue : FruitTransaction.Operation.values()) {
                if (operation.equals(operationValue)) {
                    typeOperationIsExist = true;
                }
            }
            if (!typeOperationIsExist) {
                throw new IllegalArgumentException(EXCEPTION_TYPE_OPERATION_WRONG_MESSAGE);
            }
            int quantity = Integer.parseInt(data[QUANTITY_INDEX]);
            if (quantity == QUANTITY_ZERO) {
                throw new IllegalArgumentException(EXCEPTION_QUANTITY_ZERO_MESSAGE);
            }
            FruitTransaction fruitTransaction =
                    new FruitTransaction(operation, fruitType, quantity);
            fruitTransactionsList.add(fruitTransaction);
        }
        return fruitTransactionsList;
    }
}
