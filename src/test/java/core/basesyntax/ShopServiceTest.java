package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        ShopStorage.storage.clear();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategy(handlers);
        shopService = new ShopService(strategy);
    }

    @Test
    void process_balanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        shopService.process(List.of(transaction));

        int actualQuantity = ShopStorage.storage.get("apple");
        assertEquals(100, actualQuantity, "Balance operation should set quantity to 100");
    }

    @Test
    void process_supplyOperation_ok() {
        ShopStorage.storage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 30);
        shopService.process(List.of(transaction));

        int actualQuantity = ShopStorage.storage.get("banana");
        assertEquals(50, actualQuantity, "Supply operation should add 30 to existing 20");
    }

    @Test
    void process_purchaseOperation_ok() {
        ShopStorage.storage.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        shopService.process(List.of(transaction));

        int actualQuantity = ShopStorage.storage.get("apple");
        assertEquals(80, actualQuantity, "Purchase operation should decrease quantity by 20");
    }

    @Test
    void process_returnOperation_ok() {
        ShopStorage.storage.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10);
        shopService.process(List.of(transaction));

        int actualQuantity = ShopStorage.storage.get("apple");
        assertEquals(60, actualQuantity, "Return operation should add returned 10 to 50");
    }
}
