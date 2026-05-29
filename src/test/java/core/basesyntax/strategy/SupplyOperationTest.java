package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private SupplyOperation so;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        so = new SupplyOperation();
    }

    @Test
    void supplyOperation_ok() {
        String banana = "banana";
        Storage.getStorage().put(banana, 50);
        FruitTransaction ft = new FruitTransaction(banana, Operation.SUPPLY, 50);
        so.apply(ft);
        int actual = Storage.getStorage().get(banana);
        assertEquals(100, actual);
    }
}
