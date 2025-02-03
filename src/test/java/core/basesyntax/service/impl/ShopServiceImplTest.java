package core.basesyntax.service.impl;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final Storage STORAGE = new Storage();
    private static final Map<FruitTransaction.Operation, OperationHandler> OPERATION_HANDLERS
            = new HashMap<>();
    private static List<FruitTransaction> transactions;
    private final OperationStrategy operationStrategy
            = new OperationStrategyImpl(OPERATION_HANDLERS);

    @BeforeAll
    public static void setUpBeforeClass() {
        OPERATION_HANDLERS.clear();
        OPERATION_HANDLERS.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(STORAGE));
        OPERATION_HANDLERS.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(STORAGE));
        OPERATION_HANDLERS.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(STORAGE));
        OPERATION_HANDLERS.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(STORAGE));

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
        Assertions.assertFalse(STORAGE.getInventory().isEmpty());
        Assertions.assertTrue(STORAGE.getInventory().containsKey("Melon"));
        Assertions.assertEquals(STORAGE.getInventory().get("Melon"),10);
        Assertions.assertTrue(STORAGE.getInventory().containsKey("Cherry"));
    }
}
