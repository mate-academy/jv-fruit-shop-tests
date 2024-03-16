package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.impl.DataProcessingServiceImpl;
import core.basesyntax.impl.TransactionStrategyImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.transaction.BalanceTransactionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataProcessingServiceTest {
    private static FruitTransaction fruitTransaction;
    private static DataProcessingService dataProcessingService;
    private static List<FruitTransaction> fruitTransactionsList;

    @BeforeAll
    static void beforeAll() {
        TransactionStrategy transactionBalanceStrategy = new TransactionStrategyImpl(Map
                .of(FruitTransaction.Operation.BALANCE, new BalanceTransactionHandler()));
        FruitDao fruitDao = new FruitDaoImpl();
        dataProcessingService = new DataProcessingServiceImpl(fruitDao, transactionBalanceStrategy);
    }

    @BeforeEach
    void setUp() {
        fruitTransactionsList = new ArrayList<>();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void updateDataStorage_Null_NotOk() {
        assertThrows(RuntimeException.class, () -> dataProcessingService.updateDataStorage(null),
                "Test failed! Exception should be thrown if path is NULL");
    }

    @Test
    void updateDataStorage_EmptyList_Ok() {
        dataProcessingService.updateDataStorage(fruitTransactionsList);
        int result = Storage.fruitDB.size();
        assertEquals(0, result, "Storage size must be 0");
    }

    @Test
    void updateDataStorage_ObjectIsNull_NotOk() {
        fruitTransactionsList.add(null);
        assertThrows(RuntimeException.class,
                () -> dataProcessingService.updateDataStorage(fruitTransactionsList),
                "The list should not contain Null");
    }

    @Test
    void updateDataStorage_SizeList100_Ok() {
        for (int i = 0; i < 100; i++) {
            fruitTransaction = new FruitTransaction();
            fruitTransaction.setFruit(String.valueOf(i));
            fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
            fruitTransactionsList.add(fruitTransaction);
        }
        dataProcessingService.updateDataStorage(fruitTransactionsList);
        int actual = Storage.fruitDB.size();
        assertEquals(100, actual, "Storage size must be 100, but it's " + actual);
    }

    @Test
    void updateDataStorage_FruitNull_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionsList.add(fruitTransaction);
        assertThrows(RuntimeException.class,
                () -> dataProcessingService.updateDataStorage(fruitTransactionsList),
                "The Fruit field should not be NULL");
    }

    @Test
    void updateDataStorage_OperationNull_NotOk() {
        fruitTransaction.setFruit("banana");
        fruitTransactionsList.add(fruitTransaction);
        assertThrows(RuntimeException.class,
                () -> dataProcessingService.updateDataStorage(fruitTransactionsList),
                "The Operation field should not be NULL");
    }

    @Test
    void updateDataStorage_AddFruitToStorage_Ok() {
        String apple = "apple";

        fruitTransaction.setFruit(apple);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionsList.add(fruitTransaction);

        dataProcessingService.updateDataStorage(fruitTransactionsList);

        boolean actual = Storage.fruitDB.containsKey(apple);

        assertTrue(actual, "The apple isn't exist in Storage");
    }

    @Test
    void updateDataStorage_StorageQuantity100_Ok() {
        String apple = "apple";

        fruitTransaction.setFruit(apple);
        fruitTransaction.setQuantity(100);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);

        fruitTransactionsList.add(fruitTransaction);
        dataProcessingService.updateDataStorage(fruitTransactionsList);

        int actual = Storage.fruitDB.get(apple);

        assertEquals(100, actual, "Storage apple quantity must be 100");
    }

    @AfterEach
    void tearDown() {
        Storage.fruitDB.clear();
    }
}
