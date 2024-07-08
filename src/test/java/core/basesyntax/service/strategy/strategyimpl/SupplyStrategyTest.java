package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyStrategyTest {
    private static SupplyStrategy strategy;

    @BeforeAll
    public static void setUp() {
        strategy = new SupplyStrategy();
        Storage.storage.put("apple", 100);
    }

    @Test
    void supplyApple_CorrectInputApple_isOk() {
        FruitRecord record = new FruitRecord(FruitRecord.Operation.SUPPLY, "apple", 23);
        strategy.calculation(record);
        Integer actual = Storage.storage.get("apple");
        Integer expected = 123;
        assertEquals(expected, actual);
    }
}
