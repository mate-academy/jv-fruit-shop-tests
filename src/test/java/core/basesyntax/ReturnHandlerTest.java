package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.services.actions.ActionHandler;
import core.basesyntax.services.actions.BalanceActionHandler;
import core.basesyntax.services.actions.ReturnActionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private static Storage fruitDB;
    private static ActionHandler actionHandler;

    @BeforeAll
    static void createHandlerService() {
        fruitDB = new Storage();
        actionHandler = new ReturnActionHandler(fruitDB);
    }

    @BeforeEach
    void cleanStorage() {
        fruitDB.getStorageFruits().clear();
    }

    @Test
    void returnHandle_isNullStorage_notOk() {
        assertThrows(ValidationDataException.class,
                () -> new BalanceActionHandler(null)
                        .executeAction("banana", 20));
    }

    @Test
    void returnHandle_isGoodNameEmpty_ok() {
        fruitDB.add("banana", 30);
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("", 20));
    }

    @Test
    void returnHandle_isGoodValueNull_ok() {
        fruitDB.add("banana", 30);
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("banana", null));
    }

    @Test
    void returnHandle_isGoodValueNegative_ok() {
        fruitDB.add("banana", 30);
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("banana", -4));
    }

    @Test
    void returnHandle_isNotContainFruit_ok() {
        fruitDB.add("apple", 30);
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("banana", 10));
    }

    @Test
    void returnHandle_isFirstTest_ok() {
        fruitDB.add("banana", 30);
        actionHandler.executeAction("banana", 20);
        Integer actual = fruitDB.getFruit("banana");
        Integer expected = 50;
        assertTrue(expected.equals(actual));
    }

    @Test
    void returnHandle_isSecondTest_ok() {
        fruitDB.add("banana", 40);
        fruitDB.add("apple", 40);
        actionHandler.executeAction("banana", 20);
        actionHandler.executeAction("apple", 10);
        Integer actualFirst = fruitDB.getFruit("apple");
        Integer actualSecond = fruitDB.getFruit("banana");
        Integer expectedFirst = 50;
        Integer expectedSecond = 60;
        assertTrue((expectedFirst.equals(actualFirst) && expectedSecond.equals(actualSecond)));
    }
}
