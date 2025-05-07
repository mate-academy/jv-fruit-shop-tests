package fruitshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.service.ShopService;
import fruitshop.service.impl.ShopServiceImpl;
import fruitshop.strategy.BalanceOperationHandler;
import fruitshop.strategy.OperationHandler;
import fruitshop.strategy.OperationStrategy;
import fruitshop.strategy.OperationStrategyImpl;
import fruitshop.strategy.PurchaseOperationHandler;
import fruitshop.strategy.ReturnOperationHandler;
import fruitshop.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(strategy);
        Storage.clear();
    }

    @Test
    void process_validTransactions_storageUpdatedCorrectly() {
        List<FruitTransaction> transactions = List.of(
                transaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                transaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                transaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                transaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );

        shopService.process(transactions);

        assertEquals(130, Storage.get("apple"));
    }

    @Test
    void process_nullOperation_notOk() {
        FruitTransaction transaction = transaction(null, "banana", 20);

        List<FruitTransaction> transactions = List.of(transaction);

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> shopService.process(transactions)
        );
        assertEquals("Unknown operation: null", exception.getMessage());
    }

    @Test
    void process_notEnoughFruitsToPurchase_notOk() {
        List<FruitTransaction> transactions = List.of(
                transaction(FruitTransaction.Operation.BALANCE, "orange", 10),
                transaction(FruitTransaction.Operation.PURCHASE, "orange", 20)
        );

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    private FruitTransaction transaction(FruitTransaction.Operation op, String fruit, int q) {
        FruitTransaction t = new FruitTransaction();
        t.setOperation(op);
        t.setFruit(fruit);
        t.setQuantity(q);
        return t;
    }
}
