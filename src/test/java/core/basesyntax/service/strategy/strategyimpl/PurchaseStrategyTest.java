package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.Test;

class PurchaseStrategyTest {
    private static final String APPLE = "apple";
    private static final int THIRTY = 30;
    private static final int SEVENTY = 70;
    private PurchaseStrategy purchaseStrategy = new PurchaseStrategy();

    @Test
    void purchase_CorrectInputApple_isOk() {
        Storage.storage.put("apple", 100);
        FruitRecord record = new FruitRecord(FruitRecord.Operation.PURCHASE, APPLE, THIRTY);
        purchaseStrategy.calculation(record);
        Integer actual = Storage.storage.get(APPLE);
        Integer expected = SEVENTY;
        assertEquals(expected, actual);
    }
}
