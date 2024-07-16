package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyStrategyTest {
    private static final String APPLE = "apple";
    private static final int HUNDRED = 100;
    private static final int TWENTY_THREE = 23;
    private static final int EXPECTED = 123;
    private static SupplyStrategy strategy;

    @BeforeAll
    public static void setUp() {
        strategy = new SupplyStrategy();
        Storage.storage.put(APPLE, HUNDRED);
    }

    @Test
    void supplyApple_CorrectInputApple_isOk() {
        FruitRecord record = new FruitRecord(FruitRecord.Operation.SUPPLY, APPLE, TWENTY_THREE);
        strategy.calculation(record);
        Integer actual = Storage.storage.get(APPLE);
        Integer expected = EXPECTED;
        assertEquals(expected, actual);
    }
}
