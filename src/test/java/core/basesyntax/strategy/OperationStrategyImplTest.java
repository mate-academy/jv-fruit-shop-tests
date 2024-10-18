package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation;
import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        Map<Operation, OperationHandler> operationHandlers = Map.of(
                BALANCE, new BalanceOperation(storage),
                PURCHASE, new PurchaseOperation(storage),
                RETURN, new ReturnOperation(storage),
                SUPPLY, new SupplyOperation(storage)
        );
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @AfterEach
    void tearDown() {
        storage.clearStorage();
    }

    @Test
    void executeOperation_correctOperationStrategy_ok() {
        operationStrategy.executeOperation(new FruitTransaction(BALANCE, "banana", 50));
        operationStrategy.executeOperation(new FruitTransaction(BALANCE, "apple", 100));
        operationStrategy.executeOperation(new FruitTransaction(PURCHASE, "apple", 10));
        operationStrategy.executeOperation(new FruitTransaction(RETURN, "apple", 10));
        operationStrategy.executeOperation(new FruitTransaction(SUPPLY, "banana", 50));
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = Map.of("banana", 100, "apple", 100);
        Assertions.assertEquals(expected, actual);
    }
}
