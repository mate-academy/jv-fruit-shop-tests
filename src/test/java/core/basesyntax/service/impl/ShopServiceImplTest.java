package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopServiceImpl shopService;

    @BeforeEach
    public void setUp() {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_supply_ok() {
        assertEquals(0, Storage.get("banana"));
        shopService.process(List.of(new FruitTransaction(Operation.SUPPLY, "banana",
                69)));
        assertEquals(69, Storage.get("banana"));
    }

    @Test
    void process_balance_ok() {
        assertEquals(0, Storage.get("apple"));
        shopService.process(List.of(new FruitTransaction(Operation.BALANCE, "apple",
                20)));
        assertEquals(20, Storage.get("apple"));
    }

    @Test
    void process_balanceZeroValue_notOk() {
        assertThrows(RuntimeException.class, () -> shopService.process(List.of(
                        new FruitTransaction(Operation.BALANCE, "apple", 0))),
                "You should throw an exception when amount of fruit for balance operation is <= 0");
    }

    @Test
    void process_purchase_ok() {
        Storage.save("pineapple", 10);
        assertEquals(10, Storage.get("pineapple"));
        shopService.process(List.of(new FruitTransaction(Operation.PURCHASE, "pineapple",
                10)));
        assertEquals(0, Storage.get("pineapple"));
    }

    @Test
    void process_purchaseNotEnoughFruitBalance_ok() {
        Storage.save("melon", 10);
        assertThrows(RuntimeException.class, () -> shopService.process(List.of(
                        new FruitTransaction(Operation.PURCHASE, "melon", 11))),
                "You should throw an exception when it's not enough fruits for purchase");
    }

    @Test
    void process_return_ok() {
        assertEquals(0, Storage.get("orange"));
        shopService.process(List.of(new FruitTransaction(Operation.RETURN, "orange",
                15)));
        assertEquals(15, Storage.get("orange"));
    }
}
