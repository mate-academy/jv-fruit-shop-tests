package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import core.basesyntax.model.operation.BalanceOperationHandler;
import core.basesyntax.model.operation.PurchaseOperationHandler;
import core.basesyntax.model.operation.ReturnOperationHandler;
import core.basesyntax.model.operation.SupplyOperationHandler;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final Storage storage = new Storage();
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlers
            = new HashMap<>();
    private static List<FruitTransaction> transactions;
    private final OperationStrategy operationStrategy
            = new OperationStrategyImpl(operationHandlers);

    @BeforeAll
    public static void setUpBeforeClass() {
        operationHandlers.clear();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storage));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storage));
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storage));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storage));

        transactions = new ArrayList<>();

        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("Melon");
        balanceTransaction.setQuantity(20);

        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("Cherry");
        returnTransaction.setQuantity(12);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit("Melon");
        purchaseTransaction.setQuantity(10);

        transactions.add(balanceTransaction);
        transactions.add(returnTransaction);
        transactions.add(purchaseTransaction);
    }

    @Test
    public void process_validData_Ok() {
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);
        assertFalse(storage.getInventory().isEmpty());
        assertTrue(storage.getInventory().containsKey("Melon"));
        assertEquals(storage.getInventory().get("Melon"),10);
        assertTrue(storage.getInventory().containsKey("Cherry"));
    }
}
