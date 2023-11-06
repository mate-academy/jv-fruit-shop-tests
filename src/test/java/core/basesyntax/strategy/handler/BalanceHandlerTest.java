package core.basesyntax.strategy.handler;

import static core.basesyntax.db.Storage.STORAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImp;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    static void setUp() {
        handler = new BalanceHandler(new FruitDaoImp());
    }

    @Test
    void validData_Ok() {
        handler.handle(new FruitTransaction(Operation.BALANCE,"Banana",300));
        assertEquals(300, STORAGE.get("Banana"));
    }

    @Test
    void InValidData_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.handle(new FruitTransaction(Operation.BALANCE, "Banana", -100)));
    }
}
