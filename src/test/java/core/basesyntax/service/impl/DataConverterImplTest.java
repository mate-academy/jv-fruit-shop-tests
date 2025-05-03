package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String VALID_FRUIT = "Banana";
    private static final int VALID_QUANTITY = 20;
    private static FruitTransaction fruitTransaction;
    private static DataConverter dataConverter;

    @BeforeAll
    static void init() {
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        VALID_FRUIT, VALID_QUANTITY);
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertDataToObject_convertValidData_isOK() {
        String validString = FruitTransaction.Operation.PURCHASE.getCode() + "," + VALID_FRUIT + ","
                + Integer.toString(VALID_QUANTITY);
        FruitTransaction fruitTransaction = dataConverter.convertDataToObject(validString);
        assertEquals(fruitTransaction.getOperation(), fruitTransaction.getOperation());
        assertEquals(fruitTransaction.getFruit(), fruitTransaction.getFruit());
        assertEquals(fruitTransaction.getQuantity(), fruitTransaction.getQuantity());
    }

    @Test
    void convertDataToObject_convertInvalidOperation_notOk() {
        String invalidCodeString = "n,banana,20";
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertDataToObject(invalidCodeString));
    }

    @Test
    void convertDataToObject_convertInvalidQuantity_notOk() {
        String invalidQuantityString = "b,banana,wrong";
        FruitTransaction fruitTransaction =
                dataConverter.convertDataToObject(invalidQuantityString);
        assertNull(fruitTransaction);
    }
}
