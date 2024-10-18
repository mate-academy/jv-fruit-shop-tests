package core.basesyntax.service.impl;

import core.basesyntax.DataConverter;
import core.basesyntax.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    private static final String COMA_SEPARATOR = ",";
    private static final int OPERATION_CODE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputData) {
        List<FruitTransaction> transactions = new ArrayList<>();
        boolean hasHeader = !inputData.isEmpty()
                && inputData.get(OPERATION_CODE_INDEX).startsWith("type");

        for (int i = hasHeader ? FRUIT_INDEX : OPERATION_CODE_INDEX; i < inputData.size(); i++) {
            String line = inputData.get(i);
            FruitTransaction transaction = parseLineToTransaction(line);
            transactions.add(transaction);
        }
        return transactions;
    }

    private FruitTransaction parseLineToTransaction(String line) {
        String[] parts = splitLine(line);
        String operationCode = parts[OPERATION_CODE_INDEX];
        String fruit = parts[FRUIT_INDEX];
        int quantity = parseQuantity(parts[QUANTITY_INDEX]);
        FruitTransaction.Operation operation = getOperationFromCode(operationCode);
        return new FruitTransaction(operation, fruit, quantity);
    }

    private String[] splitLine(String line) {
        return line.split(COMA_SEPARATOR);
    }

    private int parseQuantity(String quantityStr) {
        try {
            return Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quantity format: '" + quantityStr
                + "'. Expected a valid integer.");
        }
    }

    private FruitTransaction.Operation getOperationFromCode(String code) {
        for (FruitTransaction.Operation operation : FruitTransaction.Operation.values()) {
            if (operation.getCode().equals(code)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Invalid operation code: " + code);
    }
}
