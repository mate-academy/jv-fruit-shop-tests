package core.basesyntax.service.impl;

import core.basesyntax.dao.ArticleDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.regex.Pattern;

public class FruitTransactionServiceImpl implements TransactionService {
    private static final String LINE_SEPARATOR = ",";
    private static final String FORBIDDEN_CHARACTER = " ";
    private static final int NUMBER_OF_FIELDS = 3;
    private static final int TRANSACTION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private ArticleDao fruitTransactionDao;

    public FruitTransactionServiceImpl(ArticleDao fruitTransactionDao) {
        if (fruitTransactionDao == null) {
            throw new IllegalArgumentException("Constructor parameter can't be null");
        }
        this.fruitTransactionDao = fruitTransactionDao;
    }

    @Override
    public FruitTransaction createTransaction(String line) {
        if (line == null) {
            throw new NullPointerException("Parameter can't be null");
        }
        if (line.length() == 0) {
            throw new RuntimeException("Line is empty");
        }
        if (line.contains(FORBIDDEN_CHARACTER)) {
            throw new RuntimeException("Line '" + line
                    + "' shouldn't contain"
                    + " spaces and upper case letters");
        }
        String[] fields = line.split(LINE_SEPARATOR);
        boolean emptyField = false;
        for (String field : fields) {
            if (field.length() == 0) {
                emptyField = true;
            }
        }
        if (fields.length != NUMBER_OF_FIELDS || emptyField) {
            throw new RuntimeException("Wrong format in line: '" + line
                    + "'\nShould be 3 fields separated by a coma. "
                    + "Example: 'transaction,fruit,quantity'");
        }
        FruitTransaction transaction = new FruitTransaction();
        String fruit = validateFruitField(fields[FRUIT_INDEX], line);
        transaction.setFruit(fruit);
        int quantity = validateQuantityField(fields[QUANTITY_INDEX], line);
        transaction.setQuantity(quantity);
        FruitTransaction.Operation operation
                = validateOperationField(fields[TRANSACTION_INDEX], line);
        transaction.setOperation(operation);
        return transaction;
    }

    private String validateFruitField(String fruitField, String line) {
        if (fruitField == null || fruitField.length() == 0) {
            throw new RuntimeException("Wrong format of fruit field in line: '" + line
                    + "'\nShould be: " + "'transaction_type,fruit,quantity'");
        }
        if (!fruitField.matches("[a-z]+")) {
            throw new RuntimeException("Article name in line: '" + line
                    + "' shouldn't contain numbers and"
                    + " special characters");
        }
        try {
            fruitTransactionDao.getQuantity(fruitField);
        } catch (RuntimeException e) {
            throw new RuntimeException("Storage doesn't contain article '" + fruitField
                    + "'");
        }
        return fruitField;
    }

    private int validateQuantityField(String quantityField, String line) {
        Pattern pattern = Pattern.compile("-?\\d+");
        if (!pattern.matcher(quantityField).matches()) {
            throw new RuntimeException("Wrong format of quantity field in line: '" + line
                    + "'\n The field should be an integer number");
        }
        int quantity = Integer.parseInt(quantityField);
        if (quantity < 0) {
            throw new RuntimeException("Quantity can't be less than zero in line: '"
                    + line + "'");
        }
        return quantity;
    }

    private FruitTransaction.Operation validateOperationField(String operationField, String line) {
        String formattedField = operationField.toLowerCase();
        for (FruitTransaction.Operation operation : FruitTransaction.Operation.values()) {
            if (operation.getCode().equals(formattedField)) {
                return operation;
            }
        }
        throw new RuntimeException("Incorrect transaction index '"
                + operationField + "' in line: '" + line + "'");
    }
}
