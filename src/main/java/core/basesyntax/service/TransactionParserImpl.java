package core.basesyntax.service;

import core.basesyntax.transaction.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserImpl implements TransactionParser {
    private static final int OPERATION_POSITION = 0;
    private static final int COLUMN_NAMES_LINE_INDEX = 0;
    private static final int PRODUCT_POSITION = 1;
    private static final int QUANTITY_POSITION = 2;
    private static final String SPLIT_CHARACTER = ",";
    private static final int SPLIT_LINE_LENGTH = 3;

    @Override
    public List<Transaction> parse(List<String> fileData) {
        isNullList(fileData);
        isEmptyList(fileData);
        fileData.remove(COLUMN_NAMES_LINE_INDEX);
        List<Transaction> operationsList = new ArrayList<>();
        for (String line : fileData) {
            isNullLine(line);
            String[] splitLine = line.split(SPLIT_CHARACTER);
            isSeparatedCorrectly(splitLine);
            operationsList.add(createOperation(splitLine));
        }
        return operationsList;
    }

    private void isSeparatedCorrectly(String[] splitLine) {
        if (splitLine.length != SPLIT_LINE_LENGTH) {
            throw new IllegalArgumentException("Line was not separated correctly");
        }
    }

    private void isNullLine(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Line in List can't be null");
        }
    }

    private void isEmptyList(List<String> fileData) {
        if (fileData.isEmpty()) {
            throw new IllegalArgumentException("List can't be empty: "
            + fileData);
        }
    }

    private void isNullList(List<String> fileData) {
        if (fileData == null) {
            throw new IllegalArgumentException("List can't be null");
        }
    }

    private Transaction createOperation(String[] splitLine) {
        Transaction operation = new Transaction();
        operation.setOperation(Transaction.Operation.getByCode(splitLine[OPERATION_POSITION]));
        operation.setProduct(splitLine[PRODUCT_POSITION]);
        operation.setQuantity(Integer.parseInt(splitLine[QUANTITY_POSITION]));
        return operation;
    }
}
