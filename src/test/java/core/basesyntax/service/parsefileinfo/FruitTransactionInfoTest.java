package core.basesyntax.service.parsefileinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.functionalityexpansion.ActivityType;
import org.junit.jupiter.api.Test;

class FruitTransactionInfoTest {

    @Test
    void constructorAndAccessors_constructFruitTransactionInfo_Ok() {
        ActivityType activityType = ActivityType.PURCHASE;
        String name = "Apple";
        int quantity = 10;

        FruitTransactionInfo transactionInfo =
                new FruitTransactionInfo(activityType, name, quantity);

        assertEquals(activityType, transactionInfo.activityType(), "Activity type should match");
        assertEquals(name, transactionInfo.name(), "Name should match");
        assertEquals(quantity, transactionInfo.quantity(), "Quantity should match");
    }
}
