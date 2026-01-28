package core.basesyntax;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.serviceimpl.OperationStrategyImpl;
import core.basesyntax.serviceimpl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyHandler;
import core.basesyntax.utility.FruitTransactionValidator;
import core.basesyntax.utility.FruitTransactionValidatorImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private ShopService shopService;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;
    private Storage storage;
    private FruitTransactionValidator validator;
    private Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        validator = new FruitTransactionValidatorImpl();
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(validator));
        operationStrategy = new OperationStrategyImpl(handlers);
        fruitTransaction = new FruitTransaction();
        storage = new InMemoryStorage();
        shopService = new ShopServiceImpl(operationStrategy, storage);
    }

    @Test
    void successProcessSupply_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(50);
        storage.putQuantity("apple", 20);
        List<FruitTransaction> transactionList1 = new ArrayList<>(List.of(fruitTransaction));
        transactionList1.add(fruitTransaction);
        shopService.process(transactionList1);
        Assertions.assertEquals(120, storage.getQuantity("apple"));
    }

    @Test
    void processNullTransaction_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> shopService.process(null));
        Assertions.assertTrue(ex.getMessage().contains("FruitTransaction can't be null"));
    }

    @Test
    void constructorWithNullStorage_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> shopService = new ShopServiceImpl(operationStrategy, null));
        Assertions.assertTrue(ex.getMessage().contains("Storage can't be null"));
    }

    @Test
    void constructorWithNullOperation_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> shopService = new ShopServiceImpl(null, storage));
        Assertions.assertTrue(ex.getMessage().contains("Operation can't be null"));
    }
}
