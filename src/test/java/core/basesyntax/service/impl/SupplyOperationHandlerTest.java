package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {

    @Test
    void operate_supplyOperation_ok() {
        Storage.fruits.put("pineapple", 8);
        FruitTransaction transactionSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pineapple", 7);
        Integer expected = Storage.fruits.get("pineapple") + transactionSupply.getQuantity();
        OperationHandler handler = new SupplyOperationHandler();
        handler.operate(transactionSupply);
        Integer actual = Storage.fruits.get("pineapple");
        assertEquals(expected, actual, "Test failed! You should returned next quantity "
                + expected + " but you returned "
                + actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
