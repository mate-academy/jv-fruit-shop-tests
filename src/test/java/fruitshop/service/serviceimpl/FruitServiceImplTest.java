package fruitshop.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.model.FruitTransaction;
import fruitshop.model.Operation;
import fruitshop.service.FruitService;
import fruitshop.storage.Storage;
import fruitshop.strategy.operation.OperationHandler;
import fruitshop.strategy.operation.impl.BalanceOperationHandler;
import fruitshop.strategy.operation.impl.PurchaseOperationHandler;
import fruitshop.strategy.operation.impl.ReturnOperationHandler;
import fruitshop.strategy.operation.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static Map<Operation, OperationHandler> operationHandlerMap;
    private List<FruitTransaction> dataLinesObj;
    private FruitService fruitService;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
    }

    @BeforeEach
    void setUp() {
        Storage.setStorage(new HashMap<>());
        fruitService = new FruitServiceImpl(operationHandlerMap);
        dataLinesObj = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        dataLinesObj.clear();
    }

    @Test
    void processFruits_validCase_ok() {
        dataLinesObj.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        dataLinesObj.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        dataLinesObj.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        dataLinesObj.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));
        dataLinesObj.add(new FruitTransaction(Operation.RETURN, "apple", 10));
        fruitService.processFruits(dataLinesObj);
        Map<String, Integer> expected = Storage.getStorage();
        Map<String, Integer> actual = Map.of(
                "banana", 107,
                "apple", 110
        );
        assertEquals(actual, expected);
    }

    @Test
    void processFruits_nullAsParameter_notOk() {
        assertThrows(NullPointerException.class, () -> fruitService.processFruits(null));
    }

    @Test
    void processFruits_emptyListAsParameter_ok() {
        dataLinesObj = new ArrayList<>();
        fruitService.processFruits(dataLinesObj);
        Map<String, Integer> expected = Storage.getStorage();
        Map<String, Integer> actual = new HashMap<>();
        assertEquals(expected, actual);
    }
}
