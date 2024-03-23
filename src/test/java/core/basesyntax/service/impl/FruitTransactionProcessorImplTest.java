package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {
    private TransactionProcessor testProcessor;
    private Map<String, Integer> fruitTransactionStorage;
    private final List<FruitTransaction> listForBalance =
            List.of(new FruitTransaction("banana", 20, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("apple", 100, FruitTransaction.Operation.BALANCE));
    private final List<FruitTransaction> listForSupply =
            List.of(new FruitTransaction("banana", 20, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("apple", 100, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("banana", 100, FruitTransaction.Operation.SUPPLY));
    private final List<FruitTransaction> listForPurchase =
            List.of(new FruitTransaction("banana", 20, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("apple", 100, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("banana", 13, FruitTransaction.Operation.PURCHASE));
    private final List<FruitTransaction> listForPurchaseMoreQuantity =
            List.of(new FruitTransaction("banana", 20, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("apple", 100, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("banana", 50, FruitTransaction.Operation.PURCHASE));
    private final List<FruitTransaction> listForReturn =
            List.of(new FruitTransaction("banana", 20, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("apple", 100, FruitTransaction.Operation.BALANCE),
                    new FruitTransaction("apple", 10, FruitTransaction.Operation.RETURN));
    private final Map<FruitTransaction.Operation, OperationHandler> strategyMapForTest =
            Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

    @BeforeEach
    void setUp() {
        testProcessor = new FruitTransactionProcessorImpl();
        fruitTransactionStorage = FruitStorage.fruitTransactionStorage;
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitTransactionStorage.clear();
    }

    @Test
    void process_checkQuantityFruitInStorageForBalanceOperation_ok() {
        testProcessor.process(listForBalance, strategyMapForTest);
        Integer expectedBananaQuantity = 20;
        Integer expectedAppleQuantity = 100;
        Integer actualBananaQuantity = fruitTransactionStorage.get("banana");
        Integer actualAppleQuantity = fruitTransactionStorage.get("apple");
        assertEquals(expectedBananaQuantity, actualBananaQuantity,
                "Incorrect quantity fruit in storage. "
                        + "Check your implementation for balance operation");
        assertEquals(expectedAppleQuantity, actualAppleQuantity,
                "Incorrect quantity fruit in storage. "
                        + "Check your implementation for balance operation");
    }

    @Test
    void process_checkQuantityFruitInStorageForSupplyOperation_ok() {
        testProcessor.process(listForSupply, strategyMapForTest);
        Integer expectedBananaQuantity = 120;
        Integer actualBananaQuantity = fruitTransactionStorage.get("banana");
        assertEquals(expectedBananaQuantity, actualBananaQuantity,
                "Incorrect quantity fruit in storage. "
                        + "Check your implementation for balance operation");
    }

    @Test
    void process_checkQuantityFruitInStorageForPurchaseOperation_ok() {
        testProcessor.process(listForPurchase, strategyMapForTest);
        Integer expectedBananaQuantity = 7;
        Integer actualBananaQuantity = fruitTransactionStorage.get("banana");
        assertEquals(expectedBananaQuantity, actualBananaQuantity,
                "Incorrect quantity fruit in storage. "
                        + "Check your implementation for balance operation");
    }

    @Test
    void process_checkEnoughFruitForPurchaseOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> testProcessor.process(listForPurchaseMoreQuantity, strategyMapForTest),
                "The class must contain a checking for enough fruit"
                        + " for the purchase operation");
    }

    @Test
    void process_checkQuantityFruitInStorageForReturnOperation_ok() {
        testProcessor.process(listForReturn, strategyMapForTest);
        Integer expectedBananaQuantity = 110;
        Integer actualBananaQuantity = fruitTransactionStorage.get("apple");
        assertEquals(expectedBananaQuantity, actualBananaQuantity,
                "Incorrect quantity fruit in storage. "
                        + "Check your implementation for balance operation");
    }
}
