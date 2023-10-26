package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataProcessing;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.handler.impl.BalanceOperationHandler;
import core.basesyntax.service.handler.impl.PurchaseOperationHandler;
import core.basesyntax.service.handler.impl.ReturnOperationHandler;
import core.basesyntax.service.handler.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataProcessingImplTest {
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit APPLE = new Fruit("apple");
    private static final int EXPECTED_BANANA_AMOUNT = 125;
    private static final int EXPECTED_APPLE_AMOUNT = 35;
    private static final List<String> DATA = List.of("b,banana,100",
            "s,banana,40", "p,banana,35", "r,banana,20", "b,apple,40",
            "r,apple,15", "s,apple,30", "p,apple,50");
    private static final List<String> DATA_INCORRECT_OPERATION = List
            .of("b,banana,100", "q,banana,40", "s,banana,35", "p,banana,20");
    private static DataProcessing dataProcessing;

    @BeforeAll
    static void beforeAll() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<Operation, OperationHandler> handlersMap = new HashMap<>();
        handlersMap.put(Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        handlersMap.put(Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        handlersMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        handlersMap.put(Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlersMap);
        dataProcessing = new DataProcessingImpl(operationStrategy);
    }

    @Test
    void processTransaction_isOk() {
        dataProcessing.processTransaction(DATA);
        Integer actualBananaAmount = Storage.fruits.get(BANANA);
        Integer actualAppleAmount = Storage.fruits.get(APPLE);
        assertEquals(EXPECTED_BANANA_AMOUNT, actualBananaAmount);
        assertEquals(EXPECTED_APPLE_AMOUNT, actualAppleAmount);
    }

    @Test
    void processTransaction_incorrectOperation_isNotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            dataProcessing.processTransaction(DATA_INCORRECT_OPERATION);
        }, "If operation does not fount by letter it should throw Exception!!!");

        String expectedMessage = "There is no such operation by letter ";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void processTransaction_dataIsNull_isNotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            dataProcessing.processTransaction(null);
        });

        String expectedMessage = "Data is null!!!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
