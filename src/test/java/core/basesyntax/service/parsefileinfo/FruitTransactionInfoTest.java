package core.basesyntax.service.parsefileinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.functionalityexpansion.ActivityType;
import org.junit.jupiter.api.Test;

class FruitTransactionInfoTest {
    public static final int QUANTITY = 10;
    public static final String FRUIT_NAME = "Apple";

    @Test
    void constructorAndAccessors_constructFruitTransactionInfo_Ok() {
        ActivityType activityType = ActivityType.PURCHASE;

        FruitTransactionInfo transactionInfo =
                new FruitTransactionInfo(activityType, FRUIT_NAME, QUANTITY);

        assertEquals(activityType, transactionInfo.activityType(), "Activity type should match");
        assertEquals(FRUIT_NAME, transactionInfo.name(), "Name should match");
        assertEquals(QUANTITY, transactionInfo.quantity(), "Quantity should match");
    }
}
