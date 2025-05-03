package fruitshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.service.impl.ShopServiceImpl;
import fruitshop.strategy.OperationHandler;
import fruitshop.strategy.OperationStrategy;
import fruitshop.strategy.impl.BalanceOperationHandler;
import fruitshop.strategy.impl.OperationStrategyImpl;
import fruitshop.strategy.impl.PurchaseOperationHandler;
import fruitshop.strategy.impl.ReturnOperationHandler;
import fruitshop.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private static ShopService shopService;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void process_balanceTransaction_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);

        shopService.process(List.of(balanceTransaction));

        assertEquals(100, Storage.fruits.get("apple"));
    }

    @Test
    public void process_supplyTransaction_ok() {
        shopService.process(List.of(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100)));

        FruitTransaction supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50);

        shopService.process(List.of(supplyTransaction));

        assertEquals(150, Storage.fruits.get("apple"));
    }

    @Test
    public void process_purchaseTransaction_ok() {
        shopService.process(List.of(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100)));

        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30);

        shopService.process(List.of(purchaseTransaction));

        assertEquals(70, Storage.fruits.get("apple"));
    }

    @Test
    public void process_purchaseTransaction_notEnoughStock_notOk() {
        shopService.process(List.of(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100)));

        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 150);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(List.of(purchaseTransaction)));
        assertEquals("Not enough stock to purchase 150 apple", exception.getMessage());
    }

    @Test
    public void process_returnTransaction_ok() {
        shopService.process(List.of(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100)));

        FruitTransaction returnTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 30);

        shopService.process(List.of(returnTransaction));

        assertEquals(130, Storage.fruits.get("apple"));
    }
}
