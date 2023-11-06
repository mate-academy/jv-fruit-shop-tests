package core.basesyntax.strategy.handler;

import static core.basesyntax.db.Storage.STORAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImp;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    static void setUp() {
        handler = new ReturnHandler(new FruitDaoImp());
    }

    @Test
    void validData_Ok() {
        STORAGE.put("Banana",1000);
        handler.handle(new FruitTransaction(Operation.RETURN,"Banana",300));
        handler.handle(new FruitTransaction(Operation.RETURN,"Banana",100));
        assertEquals(1400, STORAGE.get("Banana"));
    }

    @Test
    void get_InValidData_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.handle(new FruitTransaction(Operation.RETURN, "Banana", -100)));
    }
}
