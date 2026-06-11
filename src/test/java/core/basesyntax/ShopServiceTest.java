package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private static final FruitTransaction defaultTransaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
    private static final FruitTransaction additionalTransaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 5);
    private static final FruitTransaction supplyTransaction =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5);
    private static final FruitTransaction purchaseTransaction =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 3);
    private static final FruitTransaction returnTransaction =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 1);

    private static ShopService shopService;
    private static StorageDao storageDao;
    private OperationStrategy strategy;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
    }

    private static Map<FruitTransaction.Operation, OperationHandler>
                getOperationHandlersMap(StorageDao storageDao) {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));

        return operationHandlers;
    }

    @BeforeEach
    void beforeEach() {
        Storage.clearFruits();
        strategy = new OperationStrategyImpl(getOperationHandlersMap(storageDao));
    }

    @Test
    public void process_NullStrategy_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService = new ShopServiceImpl(null);
            shopService.process(List.of(defaultTransaction));
        });
    }

    @Test
    public void process_NullTransactions_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService = new ShopServiceImpl(strategy);
            shopService.process(null);
        });
    }

    @Test
    public void process_UnknownStrategy_NotOk() {
        Map<FruitTransaction.Operation, OperationHandler> map = getOperationHandlersMap(storageDao);
        map.remove(FruitTransaction.Operation.BALANCE);
        strategy = new OperationStrategyImpl(map);

        assertThrows(IllegalArgumentException.class, () -> {
            shopService = new ShopServiceImpl(strategy);
            shopService.process(List.of(defaultTransaction));
        });
    }

    @Test
    public void process_Complete_Ok() {
        shopService = new ShopServiceImpl(strategy);
        shopService.process(List.of(defaultTransaction));
        Integer counter = defaultTransaction.getQuantity();
        assertEquals(Storage.getFruitQuantity(defaultTransaction.getFruit()), counter);
        assertEquals(Storage.getAllFruits().size(), 1);

        shopService.process(List.of(additionalTransaction));
        assertEquals(Storage.getFruitQuantity(additionalTransaction.getFruit()),
                additionalTransaction.getQuantity());
        assertEquals(Storage.getAllFruits().size(), 2);

        counter -= purchaseTransaction.getQuantity();
        shopService.process(List.of(purchaseTransaction));
        assertEquals(Storage.getFruitQuantity(purchaseTransaction.getFruit()), counter);

        counter += supplyTransaction.getQuantity();
        shopService.process(List.of(supplyTransaction));
        assertEquals(Storage.getFruitQuantity(supplyTransaction.getFruit()), counter);

        counter += returnTransaction.getQuantity();
        shopService.process(List.of(returnTransaction));
        assertEquals(Storage.getFruitQuantity(returnTransaction.getFruit()), counter);
    }

    @Test
    public void process_NegativeValue_NotOk() {
        shopService = new ShopServiceImpl(strategy);
        assertThrows(RuntimeException.class,
                () -> shopService.process(List.of(purchaseTransaction)));
    }
}
