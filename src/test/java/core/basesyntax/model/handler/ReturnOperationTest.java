package core.basesyntax.model.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static ReturnOperation returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @AfterEach
    void afterEach() {
        Storage.getStorage().clear();
    }

    @Test
    void returnIsValid_Ok() {
        Storage.getStorage().put("banana", 100);
        returnOperation.handle(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 57));
        assertEquals(Integer.valueOf(157), Storage.getStorage().get("banana"));
    }

    @Test
    void addNegativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.handle(
                        new FruitTransaction(
                                FruitTransaction.Operation.RETURN, "banana", -34)));
    }
}
