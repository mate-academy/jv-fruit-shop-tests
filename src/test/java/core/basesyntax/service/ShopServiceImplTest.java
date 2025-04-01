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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopServiceImpl shopService;

    @BeforeEach
    public void setUp() {
        Map<Operation, OperationHandler> operationHandlers = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    public void clearStorage() {
        getStorage().clear();
    }

    @Test
    public void process_validTransactions_updatesStorage() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 10),
                new FruitTransaction(Operation.SUPPLY, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.RETURN, "apple", 3)
        );
        shopService.process(transactions);

        assertEquals(5, getFruits().get("banana"));
        assertEquals(23, getFruits().get("apple"));
    }

    @Test
    public void process_emptyTransactions_noUpdate() {
        shopService.process(List.of());
        assertEquals(Map.of(), getFruits());
    }

    @Test
    public void process_nullTransactions_noUpdate() {
        shopService.process(null);
        assertEquals(Map.of(), getStorage());
    }

    @Test
    public void process_invalidOperation_throwsNullPointerException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(null, "banana", 10)
        );
        assertThrows(NullPointerException.class, () -> shopService.process(transactions));
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
        assertThrows(NullPointerException.class, () -> shopService.process(transactions));
    }
}
