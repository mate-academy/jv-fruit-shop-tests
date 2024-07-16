package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnStrategyTest {
    private static final String BANANA = "banana";
    private static final int EXPECTED = 155;
    private static final int FIFTY_FIVE = 55;
    private static final int HUNDRED = 100;
    private static ReturnStrategy returnStrategy;

    @BeforeAll
    public static void setUp() {
        returnStrategy = new ReturnStrategy();
        Storage.storage.put(BANANA, HUNDRED);
    }

    @Test
    void returnStrategy_CorrectInputApple_testOk() {
        FruitRecord record = new FruitRecord(FruitRecord.Operation.RETURN, BANANA, FIFTY_FIVE);
        returnStrategy.calculation(record);
        assertEquals(EXPECTED, Storage.storage.get(BANANA));
    }
}
