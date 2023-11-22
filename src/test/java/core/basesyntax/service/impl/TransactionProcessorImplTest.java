package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.CalculateStrategy;
import core.basesyntax.strategy.OperationHandlerBalance;
import core.basesyntax.strategy.OperationHandlerIn;
import core.basesyntax.strategy.OperationHandlerOut;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static final String BANANA_NAME = "banana";
    private static final String APPLE_NAME = "apple";

    private final Map<FruitTransaction.Operation, OperationHandler>
            correspondenceTable = Map.of(
            FruitTransaction.Operation.BALANCE, new OperationHandlerBalance(),
            FruitTransaction.Operation.SUPPLY, new OperationHandlerIn(),
            FruitTransaction.Operation.RETURN, new OperationHandlerIn(),
            FruitTransaction.Operation.PURCHASE, new OperationHandlerOut());
    private final List<String> strings = Stream.of(
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    ).collect(Collectors.toList());

    @Test
    void calculateBalanceTest_ok() {
        CalculateStrategy calculateStrategy = new CalculateStrategy(correspondenceTable);
        TransactionProcessor transactionProcessor = new TransactionProcessorImpl(calculateStrategy);
        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> listOfTransaction = transactionParser.parseTransaction(strings);
        Assertions.assertEquals(8,listOfTransaction.size());
        transactionProcessor.calculateBalance(listOfTransaction);
        Assertions.assertEquals(152, Storage.storage.get(BANANA_NAME));
        Assertions.assertEquals(90, Storage.storage.get(APPLE_NAME));
    }
}
