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
        shopService = new ShopServiceImpl(new HashMap<>());
    }

    @Test
    void shouldAddFruitCorrectly() {
        shopService.addFruit("apple", 10);
        assertEquals(10, shopService.getFruitQuantity("apple"));
    }

    @Test
    void shouldUpdateQuantityWhenFruitAlreadyExists() {
        shopService.addFruit("apple", 10);
        shopService.addFruit("apple", 5);
        assertEquals(15, shopService.getFruitQuantity("apple"));
    }

    @Test
    void shouldThrowExceptionWhenRemovingMoreThanAvailable() {
        shopService.addFruit("apple", 10);
        assertThrows(IllegalArgumentException.class, ()
                -> shopService.removeFruit("apple", 15));
    }

    @Test
    void shouldProcessSupplyTransactionCorrectly() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction
                .Operation.SUPPLY, new SupplyOperation());
        shopService = new ShopServiceImpl(handlers);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction
                        .Operation.SUPPLY, "banana", 20)
        );
        shopService.process(transactions);
        assertEquals(20, shopService
                .getFruitQuantity("banana"));
    }

    @Test
    void shouldProcessBalanceTransactionCorrectly() {

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction
                .Operation.BALANCE, new BalanceOperation());
        shopService = new ShopServiceImpl(handlers);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction
                        .Operation.BALANCE, "banana", 30)
        );
        shopService.process(transactions);
        assertEquals(30, shopService
                .getFruitQuantity("banana"));
    }

    @Test
    void shouldProcessPurchaseTransactionCorrectly() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction
                .Operation.PURCHASE, new PurchaseOperation());
        shopService = new ShopServiceImpl(handlers);

        shopService.addFruit("orange", 15);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction
                        .Operation.PURCHASE, "orange", 5)
        );
        shopService.process(transactions);
        assertEquals(10, shopService.getFruitQuantity("orange"));
    }

    @Test
    void shouldProcessReturnTransactionCorrectly() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        shopService = new ShopServiceImpl(handlers);

        shopService.addFruit("grape", 10);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction
                        .Operation.RETURN, "grape", 5)
        );
        shopService.process(transactions);
        assertEquals(15, shopService.getFruitQuantity("grape"));
    }
}
