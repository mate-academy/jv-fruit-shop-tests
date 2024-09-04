package core.basesyntax.service.shop;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.handler.BalanceOperation;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperation;
import core.basesyntax.service.handler.ReturnOperation;
import core.basesyntax.service.handler.SupplyOperation;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final List<FruitTransaction> FRUIT_TRANSACTIONS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
    );
    private static ShopService shopService;
    private Storage storage;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        storage = new Storage();

        Map<String, Integer> initialFruits = new HashMap<>();
        initialFruits.put("apple", 0);
        initialFruits.put("banana", 0);
        storage.setFruits(initialFruits);
    }

    @Test
    public void process_validTransactions_ok() {
        shopService.process(FRUIT_TRANSACTIONS, storage);

        Map<String, Integer> expectedFruits = new HashMap<>();
        expectedFruits.put("apple", 60);
        expectedFruits.put("banana", 15);

        Assertions.assertEquals(expectedFruits, storage.getFruits(),
                "The storage contents do not match the expected results "
                        + "after processing transactions.");
    }

    @Test
    public void process_emptyTransactions_ok() {
        List<FruitTransaction> emptyTransactions = List.of();

        shopService.process(emptyTransactions, storage);

        Map<String, Integer> expectedFruits = new HashMap<>();
        expectedFruits.put("apple", 0);
        expectedFruits.put("banana", 0);

        Assertions.assertEquals(expectedFruits, storage.getFruits());
    }

    @Test
    public void process_nullTransactions_notOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                shopService.process(null, storage));
    }

    @Test
    public void process_nullStorage_notOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                shopService.process(FRUIT_TRANSACTIONS, null));
    }
}
