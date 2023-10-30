package core.basesyntax.serviceimpl.transactionimpl;

import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;
import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction.Operation;
import core.basesyntax.strategy.serviceintrface.transaction.TransactionParser;
import java.util.List;
import java.util.stream.Collectors;

public class FruitTransactionParserImpl implements TransactionParser {

    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int QUANTITY_INDEX = 2;
    public static final String COMMA = ",";

    @Override
    public List<FruitTransaction> parseTransactions(List<String> data) {
        if (data == null || data.size() == 0) {
            throw new RuntimeException("Invalid data");
        }
        return data.stream()
                .map(transactionData -> {
                    String[] transactionFields = transactionData.split(COMMA);
                    if (transactionFields.length != 3) {
                        throw new IllegalArgumentException("Invalid transaction data: "
                                + transactionData);
                    }
                    Operation operation = Operation
                            .returnOperation(transactionFields[OPERATION_INDEX]);

                    String fruit = transactionFields[FRUIT_INDEX];
                    int quantity = Integer.parseInt(transactionFields[QUANTITY_INDEX].trim());
                    return new FruitTransaction(operation, fruit, quantity);
                })
                .collect(Collectors.toList());
    }
}


