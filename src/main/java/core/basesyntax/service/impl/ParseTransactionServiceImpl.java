package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParseTransactionService;
import java.util.List;

public class ParseTransactionServiceImpl implements ParseTransactionService {
    private static final int OPERATION_POSITION = 0;
    private static final int FRUIT_NAME_POSITION = 1;
    private static final int QUANTITY_POSITION = 2;
    private static final String CSV_STRING_SEPARATOR = ",";

    @Override
    public List<FruitTransaction> parseTransactions(List<String> transactions) {
        return transactions.stream()
                .map(this::parseTransaction)
                .toList();
    }

    private FruitTransaction parseTransaction(String transactionLine) {
        String[] stringParts = transactionLine.split(CSV_STRING_SEPARATOR);

        String fruitName = stringParts[FRUIT_NAME_POSITION];
        validateName(fruitName);
        Operation operation = Operation.getByAbbreviation(stringParts[OPERATION_POSITION]);

        validateQuantity(stringParts[QUANTITY_POSITION]);
        int quantity = Integer.parseInt(stringParts[QUANTITY_POSITION]);

        return new FruitTransaction(fruitName, operation, quantity);
    }

    private void validateQuantity(String quantity) {
        if (!quantity.matches("[1-9][0-9]*")) {
            throw new RuntimeException(
                    "Valid range is positive numbers, but it's: " + quantity
            );
        }
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new RuntimeException(
                    "The fruit name can't be empty"
            );
        }
    }

}
