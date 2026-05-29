package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.ReturnOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private ReturnOperation ro;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        ro = new ReturnOperation();
    }

    @Test
    void returnOperation_ok() {
        String banana = "banana";
        Storage.getStorage().put(banana, 50);
        FruitTransaction ft = new FruitTransaction(banana, Operation.RETURN, 25);
        ro.apply(ft);
        int actual = Storage.getStorage().get(banana);
        assertEquals(75, actual);
    }
}
