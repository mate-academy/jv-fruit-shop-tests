package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionParserImpl implements TransactionParser {
    private static final int INDEX_OPERATION_TYPE = 0;
    private static final int INDEX_PRODUCT_NAME = 1;
    private static final int INDEX_QUANTITY = 2;
    private static final String COLUMN_SEPARATOR = ",";
    private static final String POSTFIX_CSV_PATTERN = ",(\\w+'?\\w+[\\s-]?\\w+)+,\\d+";
    private final String csvFormatPattern;
    private int lineCounter;
    private int listSize;

    public TransactionParserImpl() {
        StringBuilder operationCodes = new StringBuilder();
        operationCodes.append("[");
        for (FruitTransaction.Operation operation : FruitTransaction.Operation.values()) {
            operationCodes.append(operation.getCode());
        }
        csvFormatPattern = operationCodes.append("]").append(POSTFIX_CSV_PATTERN).toString();
    }

    @Override
    public List<FruitTransaction> parseTransactions(List<String> stringTransactionsList) {
        listSize = stringTransactionsList.size();
        return stringTransactionsList.stream()
                .filter(this::isCsvFormatValid)
                .map(this::parseTransaction)
                .collect(Collectors.toList());
    }

    private FruitTransaction parseTransaction(String stringTransaction) {
        String[] transaction = stringTransaction.split(COLUMN_SEPARATOR);
        return new FruitTransaction(
                FruitTransaction.Operation.getByCode(transaction[INDEX_OPERATION_TYPE]),
                transaction[INDEX_PRODUCT_NAME],
                Integer.parseInt(transaction[INDEX_QUANTITY]));
    }

    private boolean isCsvFormatValid(String transactionString) {
        ++lineCounter;
        if (transactionString.matches(csvFormatPattern)) {
            return true;
        }
        if (lineCounter == 1 && listSize > 1) {
            return false;
        }
        throw new RuntimeException("Unsupported format of transaction '"
                + transactionString + "' on line: " + lineCounter);
    }
}
