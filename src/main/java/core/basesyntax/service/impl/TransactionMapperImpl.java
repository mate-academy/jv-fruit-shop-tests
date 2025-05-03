package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TransactionMapperImpl implements TransactionMapper {
    private static final int OPERATION_POSITION = 0;
    private static final int FRUIT_NAME_POSITION = 1;
    private static final int QUANTITY_POSITION = 2;
    private static final int COMPONENT_NUMBER = 3;
    private static final String OPERATION_TYPES = "[bprs]";

    @Override
    public List<Transaction> map(List<String> records) {
        List<Transaction> transactions = new ArrayList<>();
        if (records == null || records.isEmpty()) {
            throw new RuntimeException("There are no records");
        }
        for (String record : records) {
            Transaction transaction = createTransaction(record);
            transactions.add(transaction);
        }
        return transactions;
    }

    private Transaction createTransaction(String record) {
        if (!validateRecord(record)) {
            throw new RuntimeException("Invalid record");
        }
        String[] recordComponents = record.split(",");
        Transaction.Operation operation
                = Transaction.Operation.getFullName(recordComponents[OPERATION_POSITION]);
        String fruitName = recordComponents[FRUIT_NAME_POSITION];
        int quantity = Integer.parseInt(recordComponents[QUANTITY_POSITION]);
        return new Transaction(operation, fruitName, quantity);
    }

    private boolean validateRecord(String record) {
        if (record == null) {
            return false;
        }
        String[] recordComponents = record.split(",");
        if (recordComponents.length != COMPONENT_NUMBER) {
            return false;
        }
        String operation = recordComponents[OPERATION_POSITION];
        String fruitName = recordComponents[FRUIT_NAME_POSITION];
        String quantity = recordComponents[QUANTITY_POSITION];
        Pattern pattern = Pattern.compile(OPERATION_TYPES);
        if (!pattern.matcher(operation).matches()) {
            return false;
        }
        if (fruitName.equals("")) {
            return false;
        }
        char[] quantityCharArray = quantity.toCharArray();
        for (char ch : quantityCharArray) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }
}
