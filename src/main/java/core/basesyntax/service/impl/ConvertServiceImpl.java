package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.service.ConvertService;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertServiceImpl implements ConvertService {
    private static final String EXCEPTION
            = "Quantity cannot be negative or quantity column should contains only digits!"
            + " Insert correct values to input file";
    private static final String LESS_THAN_THREE_ELEMENT_EXCEPTION
            = "Row should contains 3 elements: type, fruit, quantity";
    private static final String INCORRECT_COLUMNS_NAMES_EXCEPTION
            = "Columns names should be as: type,fruit,quantity but was wrote as: ";
    private static final String COMA_DELIMITER = ",";

    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String COLUMNS_NAMES = "type,fruit,quantity";
    private static final String INVALID_INPUT_PARAMETER
            = "Invalid input parameter in convertData()";

    @Override
    public List<FruitTransaction> convertData(List<String> strings) {
        if (strings == null) {
            throw new RuntimeException(INVALID_INPUT_PARAMETER);
        }
        if (!strings.get(0).equals(COLUMNS_NAMES)) {
            throw new RuntimeException(INCORRECT_COLUMNS_NAMES_EXCEPTION + strings.get(0));
        }
        return strings.stream()
                .filter(line -> !line.startsWith(COLUMNS_NAMES) && !line.isEmpty())
                .map(this::getFruitTransactionObject)
                .collect(Collectors.toList());
    }

    private FruitTransaction getFruitTransactionObject(String line) {
        String[] row = line.split(COMA_DELIMITER);
        if (row.length != 3) {
            throw new RuntimeException(LESS_THAN_THREE_ELEMENT_EXCEPTION);
        }
        Operation operation = Operation.getOperationType(row[TYPE_INDEX]);
        checkException(row[QUANTITY_INDEX]);
        String fruit = row[FRUIT_INDEX];
        int quantity = Integer.parseInt(row[QUANTITY_INDEX]);
        return new FruitTransaction(operation, fruit, quantity);
    }

    private void checkException(String quantityColumn) {
        char[] charArray = quantityColumn.toCharArray();
        for (char element : charArray) {
            if (!Character.isDigit(element)) {
                throw new RuntimeException(EXCEPTION);
            }
        }
    }
}
