package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {

    @Test
    void operate_returnOperation_ok() {
        Storage.fruits.put("orange", 30);
        FruitTransaction transactionReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 15);
        Integer expected = Storage.fruits.get("orange") + transactionReturn.getQuantity();
        OperationHandler handler = new ReturnOperationHandler();
        handler.operate(transactionReturn);
        Integer actual = Storage.fruits.get("orange");
        assertEquals(expected, actual, "Test failed! You should returned next quantity "
                + expected + " but you returned "
                + actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
