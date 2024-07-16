package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceStrategyTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    private static final int TWENTY = 20;
    private static final int FORTY_SIX = 46;
    private static BalanceStrategy balanceStrategy;

    @BeforeAll
    public static void setUp() {
        balanceStrategy = new BalanceStrategy();
    }

    @Test
    void balanceStrategy_CorrectInputBanana_isOk() {
        FruitRecord record = new FruitRecord(FruitRecord.Operation.BALANCE, BANANA, FORTY_SIX);
        balanceStrategy.calculation(record);
        Integer actual = Storage.storage.get(BANANA);
        Integer expected = FORTY_SIX;
        assertEquals(expected, actual);
    }

    @Test
    void balanceStrategy_CorrectInputApple_isOk() {
        FruitRecord record = new FruitRecord(FruitRecord.Operation.BALANCE, APPLE, TWENTY);
        balanceStrategy.calculation(record);
        Integer actual = Storage.storage.get(APPLE);
        Integer expected = TWENTY;
        assertEquals(expected, actual);
    }
}
