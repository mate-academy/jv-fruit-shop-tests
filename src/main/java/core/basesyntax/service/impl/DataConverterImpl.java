package core.basesyntax.service.impl;

import core.basesyntax.DataConverter;
import core.basesyntax.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    private static final String COMA_SEPARATOR = ",";

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputData) {
        List<FruitTransaction> transactions = new ArrayList<>();
        List<String> dataLines = removeHeaderIfExists(inputData);
        for (String line : dataLines) {
            FruitTransaction transaction = parseLineToTransaction(line);
            transactions.add(transaction);
        }
        return transactions;
    }

    private List<String> removeHeaderIfExists(List<String> inputData) {
        if (!inputData.isEmpty() && inputData.get(0).startsWith("type")) {
            return inputData.subList(1, inputData.size());
        }
        return inputData;
    }

    private FruitTransaction parseLineToTransaction(String line) {
        String[] parts = splitLine(line);
        String operationCode = parts[0];
        String fruit = parts[1];
        int quantity = parseQuantity(parts[2]);
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
