package core.basesyntax.service;

import static core.basesyntax.db.Storage.getFruits;
import static core.basesyntax.db.Storage.getStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static ShopServiceImpl shopService;
    private static OperationStrategy operationStrategy;
    private static Map<Operation, OperationHandler> operationHandlers;

    @BeforeAll
    public static void setUp() {
        operationHandlers = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler()
        );
    }

    @AfterEach
    public void beforeEach() {
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    public void clearStorage() {
        getStorage().clear();
    }

    @Test
    public void process_validTransactions_updatesStorage() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.BALANCE, "banana", 10));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "apple", 20));
        transactions.add(new FruitTransaction(Operation.PURCHASE, "banana", 5));
        transactions.add(new FruitTransaction(Operation.RETURN, "apple", 3));
        shopService.process(transactions);
        assertEquals(5, getFruits().get("banana"));
        assertEquals(23, getFruits().get("apple"));
    }

    @Test
    public void process_emptyTransactions_noUpdate() {
        shopService.process(List.of());
        assertEquals(Map.of(), getStorage());
    }

    @Test
    public void process_nullTransactions_noUpdate() {
        assertThrows(RuntimeException.class, () -> shopService.process(null));
    }

    @Test
    public void process_invalidOperation_fruitNull_throwsNullPointerException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, null, 10)
        );
        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    public void process_nullHandler_throwsNullPointerException() {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlers.put(Operation.RETURN, null);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.RETURN, "apple", 3)
        );
        assertThrows(NullPointerException.class,
                () -> shopService.process(transactions));
    }
}
