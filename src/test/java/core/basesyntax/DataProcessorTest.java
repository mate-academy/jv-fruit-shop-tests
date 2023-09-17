package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataProcessorService;
import core.basesyntax.serviceimpl.DataProcessorServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataProcessorTest {
    private static DataProcessorService dataProcessorService;
    private static List<FruitTransaction> fruitTransactionTestList;
    private static HashMap<Operation, OperationStrategy> handlerMap;

    @BeforeAll
    static void setUp() {
        handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        dataProcessorService = new DataProcessorServiceImpl(handlerMap);
        fruitTransactionTestList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        fruitTransactionTestList.clear();
        Storage.STORAGE.clear();
    }

    @DisplayName("Processing valid input data")
    @Test
    void dataProcessor_ProcessValidData_Ok() {
        fruitTransactionTestList.add(new FruitTransaction(Operation.BALANCE, "Apple", 100));
        String key = "Apple";
        Integer value = 100;
        dataProcessorService.processData(fruitTransactionTestList);
        Assertions.assertEquals(Storage.STORAGE.get(key), value);
    }

    @DisplayName("Null values processing tests")
    @Test
    void dataProcessor_ProcessNullValue_NotOk() {
        Assert.assertThrows(InvalidDataException.class,
                () -> dataProcessorService.processData(null));
    }

    @Test
    void dataProcessor_ProcessNullOperationType_NotOk() {
        fruitTransactionTestList.add(new FruitTransaction(null,"Apple", 20));
        Assert.assertThrows(InvalidDataException.class,
                () -> dataProcessorService.processData(fruitTransactionTestList));
    }

    @Test
    void dataProcessor_ProcessNullFruitType_NotOk() {
        fruitTransactionTestList.add(new FruitTransaction(Operation.BALANCE,null, 20));
        Assert.assertThrows(InvalidDataException.class,
                () -> dataProcessorService.processData(fruitTransactionTestList));
    }

    @Test
    void dataProcessor_ProcessNullQuantity_NotOk() {
        fruitTransactionTestList.add(new FruitTransaction(Operation.RETURN, "Grape", null));
        Assert.assertThrows(InvalidDataException.class,
                () -> dataProcessorService.processData(fruitTransactionTestList));
    }

    @DisplayName("Storage quantity is less then purchase transaction quantity")
    @Test
    void dataProcessorTest_StorageQuantityLessTransactionQuantity_NotOk() {
        fruitTransactionTestList.add(
                new FruitTransaction(Operation.PURCHASE, "Banana", 100));
        Storage.STORAGE.put("Banana", 99);
        Assert.assertThrows(InvalidDataException.class,
                () -> dataProcessorService.processData(fruitTransactionTestList));
    }

    @DisplayName("Negative quantity test")
    @Test
    void dataProcessorTest_NegativeQuantity_NotOk() {
        fruitTransactionTestList.add(new FruitTransaction(
                Operation.BALANCE,"Banana", -1));
        Assert.assertThrows(InvalidDataException.class,
                () -> dataProcessorService.processData(fruitTransactionTestList));
    }
}
