package core.basesyntax.dao;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserImpl implements TransactionParser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parseTransactions(List<String> stringList) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        for (String transaction : stringList) {
            String[] transactionDetails = transaction.split(",");

            FruitTransaction.Operation operation =
                    FruitTransaction.Operation.fromCode(transactionDetails[OPERATION_INDEX]);
            String fruitName = transactionDetails[FRUIT_NAME_INDEX];
            long testQuantity = Long.parseLong(transactionDetails[QUANTITY_INDEX]);
            if (testQuantity > Integer.MAX_VALUE) {
                throw new ArithmeticException("Quantity of goods is too big.: " + testQuantity);
            }
            int quantity = (int) testQuantity;

            FruitTransaction fruitTransaction =
                    new FruitTransaction(operation, fruitName, quantity);
            fruitTransactionList.add(fruitTransaction);
        }
        return fruitTransactionList;
    }
}
