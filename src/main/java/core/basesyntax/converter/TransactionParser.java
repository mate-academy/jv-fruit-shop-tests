package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.function.Function;

public class TransactionParser implements Function<String, FruitTransaction> {
    private static final String COMMA = ",";
    private static final int OPERATION_POSITION = 0;
    private static final int FRUIT_POSITION = 1;
    private static final int QUANTITY_POSITION = 2;
    private static final String CSV_FORMAT = "^\\w,\\w+,\\d+$";

    @Override
    public FruitTransaction apply(String transaction) {
        checkTransactionFormat(transaction);
        String[] fields = transaction.split(COMMA);
        return new FruitTransaction(
                FruitTransaction.Operation.fromCode(fields[OPERATION_POSITION].trim()),
                fields[FRUIT_POSITION],
                Integer.parseInt(fields[QUANTITY_POSITION])
        );
    }

    private void checkTransactionFormat(String transaction) {
        if (!transaction.matches(CSV_FORMAT)) {
            throw new RuntimeException("Invalid format input transaction" + transaction);
        }
    }
}
