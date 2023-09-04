package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static final FruitDao FRUIT_DAO = new FruitDaoImpl();
    private static final Map<FruitTransaction.Operation, OperationHandler>
            OPERATION_HANDLER_MAP = new HashMap<>();
    private final List<FruitTransaction> fruitList = new ArrayList<>();
    private final List<FruitTransaction> noValidFruitList = new ArrayList<>();

    private final FruitService fruitService = new FruitServiceImpl(new FruitDaoImpl(),
            new TransactionStrategyImpl(OPERATION_HANDLER_MAP));

    @BeforeAll
    public static void beforeAll() {
        OPERATION_HANDLER_MAP.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(FRUIT_DAO));
        OPERATION_HANDLER_MAP.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(FRUIT_DAO));
        OPERATION_HANDLER_MAP.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(FRUIT_DAO));
        OPERATION_HANDLER_MAP.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(FRUIT_DAO));
    }

    @BeforeEach
    public void init() {
        FruitTransaction f1 = new FruitTransaction();
        f1.setOperation(FruitTransaction.Operation.BALANCE);
        f1.setFruitName("lemon");
        f1.setFruitQuantity(50);
        FruitTransaction f2 = new FruitTransaction();
        f2.setOperation(FruitTransaction.Operation.SUPPLY);
        f2.setFruitName("apple");
        f2.setFruitQuantity(441);
        FruitTransaction f3 = new FruitTransaction();
        f3.setOperation(FruitTransaction.Operation.RETURN);
        f3.setFruitName("banana");
        f3.setFruitQuantity(2);
        FruitTransaction f4 = new FruitTransaction();
        f4.setOperation(FruitTransaction.Operation.BALANCE);
        f4.setFruitName("pineapple");
        f4.setFruitQuantity(231);
        FruitTransaction f5 = new FruitTransaction();
        f5.setOperation(FruitTransaction.Operation.PURCHASE);
        f5.setFruitName("apple");
        f5.setFruitQuantity(37);
        FruitTransaction f6 = new FruitTransaction();
        f6.setOperation(FruitTransaction.Operation.SUPPLY);
        f6.setFruitName("lemon");
        f6.setFruitQuantity(73);
        FruitTransaction f7 = new FruitTransaction();
        f7.setOperation(FruitTransaction.Operation.RETURN);
        f7.setFruitName("pineapple");
        f7.setFruitQuantity(74);
        fruitList.add(f1);
        fruitList.add(f2);
        fruitList.add(f3);
        fruitList.add(f4);
        fruitList.add(f5);
        fruitList.add(f6);
        fruitList.add(f7);
        FruitTransaction noValidFruit1 = new FruitTransaction();
        noValidFruit1.setOperation(FruitTransaction.Operation.BALANCE);
        noValidFruit1.setFruitName("null");
        noValidFruit1.setFruitQuantity(0);
        FruitTransaction noValidFruit2 = new FruitTransaction();
        noValidFruit2.setOperation(FruitTransaction.Operation.SUPPLY);
        noValidFruit2.setFruitName("0");
        noValidFruit2.setFruitQuantity(-462);
        FruitTransaction noValidFruit3 = new FruitTransaction();
        noValidFruit3.setOperation(FruitTransaction.Operation.RETURN);
        noValidFruit3.setFruitName("kiwi");
        noValidFruit3.setFruitQuantity(13);
        FruitTransaction noValidFruit4 = new FruitTransaction();
        noValidFruit4.setOperation(FruitTransaction.Operation.PURCHASE);
        noValidFruit4.setFruitName("strawberry");
        noValidFruit4.setFruitQuantity(48);
        FruitTransaction noValidFruit5 = new FruitTransaction();
        noValidFruit5.setOperation(FruitTransaction.Operation.RETURN);
        noValidFruit5.setFruitName("null");
        noValidFruit5.setFruitQuantity(-16);
        FruitTransaction noValidFruit6 = new FruitTransaction();
        noValidFruit6.setOperation(FruitTransaction.Operation.SUPPLY);
        noValidFruit6.setFruitName("kiwi");
        noValidFruit6.setFruitQuantity(-5);
        FruitTransaction noValidFruit7 = new FruitTransaction();
        noValidFruit7.setOperation(FruitTransaction.Operation.PURCHASE);
        noValidFruit7.setFruitName("0");
        noValidFruit7.setFruitQuantity(48);
        noValidFruitList.add(noValidFruit1);
        noValidFruitList.add(noValidFruit2);
        noValidFruitList.add(noValidFruit3);
        noValidFruitList.add(noValidFruit4);
        noValidFruitList.add(noValidFruit5);
        noValidFruitList.add(noValidFruit6);
        noValidFruitList.add(noValidFruit7);
    }

    @Test
    void processTransactions_notValidData_notOk() {
        Storage.FRUIT_MAP.put("null", null);
        Storage.FRUIT_MAP.put("0", -462);
        Storage.FRUIT_MAP.put("kiwi", -55);
        Storage.FRUIT_MAP.put("strawberry", 4);
        assertThrows(RuntimeException.class,
                () -> fruitService.processTransactions(noValidFruitList));
    }

    @Test
    void processTransactions_ValidData_Ok() {
        Storage.FRUIT_MAP.put("banana", 0);
        Storage.FRUIT_MAP.put("apple", 0);
        Storage.FRUIT_MAP.put("lemon", 0);
        Storage.FRUIT_MAP.put("pineapple", 0);
        fruitService.processTransactions(fruitList);
        String actual = String.join(" ", Storage.FRUIT_MAP.entrySet().toString().split("="));
        String expected = "[banana 2, apple 404, lemon 123, pineapple 305]";
        assertEquals(expected, actual);
    }

    @Test
    void createReport_ValidData_Ok() {
        Storage.FRUIT_MAP.put("grape", 13);
        Storage.FRUIT_MAP.put("banana", 8);
        Storage.FRUIT_MAP.put("apple", 16);
        Storage.FRUIT_MAP.put("lemon", 11);
        String expected = "[fruit,quantity, banana,8, apple,16, lemon,11, grape,13]";
        String actual = String.valueOf(fruitService.createReport());
        assertEquals(expected, actual);
    }

    @AfterEach
    public void clearStorageAfterEachTest() {
        fruitList.clear();
        Storage.FRUIT_MAP.clear();
    }
}
