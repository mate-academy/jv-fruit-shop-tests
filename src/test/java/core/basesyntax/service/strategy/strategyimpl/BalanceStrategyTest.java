package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceStrategyTest {
    private static BalanceStrategy balanceStrategy;

    @BeforeAll
    public static void setUp() {
        balanceStrategy = new BalanceStrategy();
    }

    @Test
    void balanceStrategy_CorrectInputBanana_isOk() {
        FruitRecord record = new FruitRecord(FruitRecord.Operation.BALANCE, "banana", 46);
        balanceStrategy.calculation(record);
        Integer actual = Storage.storage.get("banana");
        Integer expected = 46;
        assertEquals(expected, actual);
    }

    @Test
    void balanceStrategy_CorrectInputApple_isOk() {
        FruitRecord record = new FruitRecord(FruitRecord.Operation.BALANCE, "apple", 20);
        balanceStrategy.calculation(record);
        Integer actual = Storage.storage.get("apple");
        Integer expected = 20;
        assertEquals(expected, actual);
    }
}
