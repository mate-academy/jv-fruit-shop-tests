package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

class OperationStrategyImplTest {
    private static final FruitTransaction CORRECT_FIRST_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final FruitTransaction CORRECT_SECOND_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
    private static final FruitTransaction CORRECT_PURCHASE_OPERATION_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
    private static final FruitTransaction BIGGER_THEN_BALANCE_PURCHASE_OPERATION_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 21);
    private static final FruitTransaction CORRECT_RETURN_OPERATION_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10);
    private static final FruitTransaction CORRECT_SUPPLY_OPERATION_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);
    private static final FruitTransaction NEGATIVE_BALANCE_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -10);
    private static final FruitTransaction NEGATIVE_PURCHASE_OPERATION_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", -10);
    private static final FruitTransaction NEGATIVE_SUPPLY_OPERATION_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", -10);
    private static final FruitTransaction NEGATIVE_RETURN_OPERATION_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", -10);
    private static final Map<String, Integer> CORRECT_BALANCE_OPERATION_STORAGE = Map.of(
            "banana", 20,
            "apple", 100
    );
    private static final Map<String, Integer> CORRECT_PURCHASE_OPERATION_STORAGE = Map.of(
            "banana", 10
    );
    private static final Map<String, Integer> CORRECT_RETURN_OPERATION_STORAGE = Map.of(
            "banana", 30
    );
    private static final Map<String, Integer> CORRECT_SUPPLY_OPERATION_STORAGE = Map.of(
            "banana", 30
    );

    private static OperationStrategy operationStrategy;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(storage),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(storage),
                FruitTransaction.Operation.RETURN, new ReturnOperation(storage),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(storage)
        );
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @AfterEach
    void tearDown() {
        storage.clearStorage();
    }

    @Test
    void executeOperation_balanceOperation_ok() {
        operationStrategy.executeOperation(CORRECT_FIRST_FRUIT);
        operationStrategy.executeOperation(CORRECT_SECOND_FRUIT);
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = CORRECT_BALANCE_OPERATION_STORAGE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void executeOperation_purchaseOperation_ok() {
        operationStrategy.executeOperation(CORRECT_FIRST_FRUIT);
        operationStrategy.executeOperation(CORRECT_PURCHASE_OPERATION_FRUIT);
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = CORRECT_PURCHASE_OPERATION_STORAGE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void executeOperation_returnOperation_ok() {
        operationStrategy.executeOperation(CORRECT_FIRST_FRUIT);
        operationStrategy.executeOperation(CORRECT_RETURN_OPERATION_FRUIT);
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = CORRECT_RETURN_OPERATION_STORAGE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void executeOperation_purchaseOperationGreaterThenBalance_notOk() {
        operationStrategy.executeOperation(CORRECT_FIRST_FRUIT);
        Assertions.assertThrows(RuntimeException.class, () ->
                operationStrategy.executeOperation(BIGGER_THEN_BALANCE_PURCHASE_OPERATION_FRUIT));
    }

    @Test
    void executeOperation_supplyOperation_ok() {
        operationStrategy.executeOperation(CORRECT_FIRST_FRUIT);
        operationStrategy.executeOperation(CORRECT_SUPPLY_OPERATION_FRUIT);
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = CORRECT_SUPPLY_OPERATION_STORAGE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void executeOperation_negativeBalanceInOllOperation_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy.executeOperation(
                        NEGATIVE_BALANCE_FRUIT),
                "Expected RuntimeException was not thrown in " + BalanceOperation.class);
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy.executeOperation(
                        NEGATIVE_RETURN_OPERATION_FRUIT),
                "Expected RuntimeException was not thrown in " + ReturnOperation.class);
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy.executeOperation(
                        NEGATIVE_PURCHASE_OPERATION_FRUIT),
                "Expected RuntimeException was not thrown in " + PurchaseOperation.class);
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy.executeOperation(
                        NEGATIVE_SUPPLY_OPERATION_FRUIT),
                "Expected RuntimeException was not thrown in " + SupplyOperation.class);
    }
}