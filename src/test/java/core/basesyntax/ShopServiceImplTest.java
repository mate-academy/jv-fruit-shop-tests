package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        shopService = new ShopServiceImpl(handlers);
    }

    @Test
    void process_balanceTransaction_shouldUpdateStorage() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50)
        );

        shopService.process(transactions);
        assertEquals(50, shopService.getFruitQuantity("apple"));
    }

    @Test
    void process_supplyTransaction_shouldIncreaseQuantity() {
        shopService.addFruit("banana", 10);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20)
        );

        shopService.process(transactions);
        assertEquals(30, shopService.getFruitQuantity("banana"));
    }

    @Test
    void process_purchaseTransaction_shouldDecreaseQuantity() {
        shopService.addFruit("orange", 15);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 5)
        );

        shopService.process(transactions);
        assertEquals(10, shopService.getFruitQuantity("orange"));
    }

    @Test
    void process_purchaseTransaction_shouldThrowExceptionWhenNotEnoughStock() {
        shopService.addFruit("grape", 5);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "grape", 10)
        );

        assertThrows(IllegalArgumentException.class, () -> shopService.process(transactions));
    }

    @Test
    void process_returnTransaction_shouldIncreaseQuantity() {
        shopService.addFruit("mango", 5);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "mango", 3)
        );

        shopService.process(transactions);
        assertEquals(8, shopService.getFruitQuantity("mango"));
    }
}
