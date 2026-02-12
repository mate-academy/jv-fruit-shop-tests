package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;
import core.basesyntax.models.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String PEAR = "pear";
    private static final Map<Operation, OperationHandler> HANDLERS
            = Map.of(Operation.BALANCE, new BalanceHandler(),
            Operation.PURCHASE, new PurchaseHandler(),
            Operation.RETURN, new ReturnHandler(),
            Operation.SUPPLY, new SupplyHandler());
    private static StorageDao storageDao;
    private static ShopService shopService;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationStrategy = new OperationStrategyImpl(HANDLERS);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_allOperationsWithOneFruit_Ok() {
        List<FruitTransfer> fruitTransferList
                = List.of(new FruitTransfer(Operation.BALANCE, PEAR, 50),
                        new FruitTransfer(Operation.PURCHASE, PEAR, 30),
                        new FruitTransfer(Operation.SUPPLY, PEAR, 15),
                        new FruitTransfer(Operation.RETURN, PEAR, 5));
        shopService.process(fruitTransferList);
        int expected = 40;
        int actual = storageDao.getStoredQuantity(PEAR);
        assertEquals(expected, actual);
    }

    @Test
    void process_NullValue_NotOk() {
        List<FruitTransfer> nullFruitTransferList = null;
        assertThrows(InvalidDataException.class, () ->
                shopService.process(nullFruitTransferList));
    }

    @Test
    void process_EmptyListArgument_Ok() {
        Storage.getStoredFruits().clear();
        List<FruitTransfer> emptyFruitTransferList = new ArrayList<>();
        shopService.process(emptyFruitTransferList);
        Map<String, Integer> actual = Storage.getStoredFruits();
        assertTrue(actual.isEmpty());
    }
}
