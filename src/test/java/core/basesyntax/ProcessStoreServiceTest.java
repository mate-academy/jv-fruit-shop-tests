package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.ActionStrategy;
import core.basesyntax.services.ProcessStoreService;
import core.basesyntax.services.actions.ActionHandler;
import core.basesyntax.services.actions.BalanceActionHandler;
import core.basesyntax.services.actions.PurchaseActionHandler;
import core.basesyntax.services.actions.ReturnActionHandler;
import core.basesyntax.services.actions.SupplyActionHandler;
import core.basesyntax.services.impl.ActionStrategyImpl;
import core.basesyntax.services.impl.ProcessStoreServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProcessStoreServiceTest {
    private static Storage fruitDB;
    private static Map<FruitTransaction.ActionType, ActionHandler> actionHandlerMapTest;
    private static ActionStrategy actionStrategyTest;
    private static ProcessStoreService handleProcessTest;

    @BeforeAll
    static void createProcessServer() {
        fruitDB = new Storage();
        actionHandlerMapTest = new HashMap<>();
        actionHandlerMapTest.put(FruitTransaction.ActionType.BALANCE,
                new BalanceActionHandler(fruitDB));
        actionHandlerMapTest.put(FruitTransaction.ActionType.SUPPLY,
                new SupplyActionHandler(fruitDB));
        actionHandlerMapTest.put(FruitTransaction.ActionType.PURCHASE,
                new PurchaseActionHandler(fruitDB));
        actionHandlerMapTest.put(FruitTransaction.ActionType.RETURN,
                new ReturnActionHandler(fruitDB));
        actionStrategyTest = new ActionStrategyImpl(actionHandlerMapTest);
        handleProcessTest = new ProcessStoreServiceImpl(actionStrategyTest);
    }

    @BeforeEach
    void cleanStorage() {
        fruitDB.getStorageFruits().clear();
    }

    @Test
    void processHandle_nullFruitTransition_notOk() {
        List<FruitTransaction> fruitsTransactions = null;
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void processHandle_emptyFruitTransition_notOk() {
        List<FruitTransaction> fruitsTransactions = new ArrayList<>();
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void processHandle_nullStorage_notOk() {
        Map<FruitTransaction.ActionType, ActionHandler> actionHandlerMapTestTemp = new HashMap<>();
        actionHandlerMapTestTemp.put(FruitTransaction.ActionType.BALANCE,
                new BalanceActionHandler(null));
        ActionStrategy actionStrategyTestTemp =
                new ActionStrategyImpl(actionHandlerMapTestTemp);
        ProcessStoreService handleProcessTestTemp =
                new ProcessStoreServiceImpl(actionStrategyTestTemp);
        List<FruitTransaction> fruitsTransactions = new ArrayList<>();
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                        "banana", 20));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTestTemp.processAction(fruitsTransactions));
    }

    @Test
    void processHandle_emptyStorage_notOk() {
        List<FruitTransaction> fruitsTransactions = new ArrayList<>();
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.RETURN,
                        "banana", 10));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void processHandle_nullNameFruit_notOk() {
        List<FruitTransaction> fruitsTransactions = new ArrayList<>();
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                        null, 10));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void processHandle_emptyNameFruit_notOk() {
        List<FruitTransaction> fruitsTransactions = new ArrayList<>();
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                        "", 10));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void processHandle_notExistProduct_notOk() {
        fruitDB.add("apple", 20);
        List<FruitTransaction> fruitsTransactions = new ArrayList<>();
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.RETURN,
                        "banana", 10));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void processHandle_correctTestTransaction_ok() {
        fruitDB.add("apple", 20);
        List<FruitTransaction> fruitsTransactions = new ArrayList<>();
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.RETURN,
                        "apple", 20));
        handleProcessTest.processAction(fruitsTransactions);
        Integer actual = fruitDB.getFruit("apple");
        Integer expected = 40;
        assertTrue(expected.equals(actual));
    }

    @Test
    void processHandle_correctTestFiveTransactions_ok() {
        List<FruitTransaction> fruitsTransactions = new ArrayList<>();
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                        "apple", 20));
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                        "banana", 10));
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.PURCHASE,
                        "banana", 10));
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.SUPPLY,
                        "banana", 10));
        fruitsTransactions
                .add(new FruitTransaction(FruitTransaction.ActionType.RETURN,
                        "apple", 10));
        handleProcessTest.processAction(fruitsTransactions);
        Integer actualFirst = fruitDB.getFruit("apple");
        Integer actualSecond = fruitDB.getFruit("banana");
        Integer expectedFirst = 30;
        Integer expectedSecond = 10;
        assertTrue((expectedFirst.equals(actualFirst) && expectedSecond.equals(actualSecond)));
    }
}
