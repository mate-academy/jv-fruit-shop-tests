package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnStrategyTest {
    private static ReturnStrategy returnStrategy;

    @BeforeAll
    public static void setUp() {
        returnStrategy = new ReturnStrategy();
        Storage.storage.put("banana", 100);
    }

    @Test
    public void returnStrategy__CorrectInputApple_testOk() {
        FruitRecord record = new FruitRecord(FruitRecord.Operation.RETURN, "banana", 55);
        returnStrategy.calculation(record);
        assertEquals(155, Storage.storage.get("banana"));
    }
}
