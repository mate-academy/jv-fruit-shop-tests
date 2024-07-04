package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.Test;

class PurchaseStrategyTest {
    private PurchaseStrategy purchaseStrategy = new PurchaseStrategy();

    @Test
    void purchase_CorrectInputApple_isOk() {
        Storage.storage.put("apple", 100);
        FruitRecord record = new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 30);
        purchaseStrategy.calculation(record);
        Integer actual = Storage.storage.get("apple");
        Integer expected = 70;
        assertEquals(expected, actual);
    }
}
