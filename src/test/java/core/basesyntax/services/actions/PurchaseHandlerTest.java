package core.basesyntax.services.actions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private static Storage fruitDB;
    private static ActionHandler actionHandler;

    @BeforeAll
    static void createHandlerService() {
        fruitDB = new Storage();
        actionHandler = new PurchaseActionHandler(fruitDB);
    }

    @BeforeEach
    void cleanStorage() {
        fruitDB.clean();
        fruitDB.add("banana", 30);
    }

    @Test
    void purchaseHandle_nullStorage_notOk() {
        assertThrows(ValidationDataException.class,
                () -> new BalanceActionHandler(null)
                        .executeAction("banana", 20));
    }

    @Test
    void purchaseHandle_emptyName_notOk() {
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("", 20));
    }

    @Test
    void purchaseHandle_nullValue_notOk() {
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("banana", null));
    }

    @Test
    void purchaseHandle_negativeValue_notOk() {
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("banana", -4));
    }

    @Test
    void purchaseHandle_notContainFruit_notOk() {
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("apple", 10));
    }

    @Test
    void purchaseHandle_correctTestOneAction_ok() {
        actionHandler.executeAction("banana", 20);
        Integer actual = fruitDB.getFruit("banana");
        Integer expected = 10;
        assertTrue(expected.equals(actual));
    }

    @Test
    void purchaseHandle_correctTestTwoActions_ok() {
        fruitDB.clean();
        fruitDB.add("banana", 40);
        fruitDB.add("apple", 40);
        actionHandler.executeAction("banana", 20);
        actionHandler.executeAction("apple", 10);
        Integer actualFirst = fruitDB.getFruit("apple");
        Integer actualSecond = fruitDB.getFruit("banana");
        Integer expectedFirst = 30;
        Integer expectedSecond = 20;
        assertTrue((expectedFirst.equals(actualFirst) && expectedSecond.equals(actualSecond)));
    }
}
