package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.services.actions.ActionHandler;
import core.basesyntax.services.actions.BalanceActionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private static Storage fruitDB;
    private static ActionHandler actionHandler;

    @BeforeAll
    static void createHandlerService() {
        fruitDB = new Storage();
        actionHandler = new BalanceActionHandler(fruitDB);
    }

    @BeforeEach
    void cleanStorage() {
        fruitDB.getStorageFruits().clear();
    }

    @Test
    void balanceHandler_isNullStorage_notOk() {
        assertThrows(ValidationDataException.class,
                () -> new BalanceActionHandler(null)
                        .executeAction("banana", 20));
    }

    @Test
    void balanceHandler_isGoodNameEmpty_ok() {
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("", 20));
    }

    @Test
    void balanceHandler_isGoodValueNull_ok() {
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("banana", null));
    }

    @Test
    void balanceHandler_isGoodValueNegative_ok() {
        assertThrows(ValidationDataException.class,
                () -> actionHandler
                        .executeAction("banana", -4));
    }

    @Test
    void balanceHandler_isFirstTest_ok() {
        actionHandler.executeAction("banana", 20);
        Integer actual = fruitDB.getFruit("banana");
        Integer expected = 20;
        assertTrue(expected.equals(actual));
    }

    @Test
    void balanceHandler_isSecondTest_ok() {
        actionHandler.executeAction("banana", 20);
        actionHandler.executeAction("apple", 10);
        Integer actual = fruitDB.getFruit("apple");
        Integer expected = 10;
        assertTrue(expected.equals(actual));
    }
}
