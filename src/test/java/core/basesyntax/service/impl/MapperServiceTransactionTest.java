package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;

public class MapperServiceTransactionTest {
    private static final int OPERATION_CODE_COLUMN = 0;
    private static final int FRUIT_COLUMN = 1;
    private static final int AMOUNT_COLUMN = 2;
    private static final String CSV_SEPARATOR = ",";
    private static final String VALID_STRING =
            "b" + CSV_SEPARATOR + "banana" + CSV_SEPARATOR + "20";
    private static final String INVALID_STRING_MISSING_ELEMENTS =
            "banana" + CSV_SEPARATOR + "20";
    private static final String INVALID_STRING_WRONG_ORDER =
            "banana" + CSV_SEPARATOR + "b" + CSV_SEPARATOR + "20";
    private static final FruitTransaction EXPECTED_RESULT =
            new FruitTransaction("b", "banana", 20);
    private static final MapperServiceTransaction mapper =
            new MapperServiceTransaction(
                    OPERATION_CODE_COLUMN,
                    FRUIT_COLUMN,
                    AMOUNT_COLUMN,
                    CSV_SEPARATOR
            );

    @Test
    public void map_withValidString_ok() {
        Assert.assertEquals(EXPECTED_RESULT, mapper.map(VALID_STRING));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void map_withMissingParametersString_notOk() {
        mapper.map(INVALID_STRING_MISSING_ELEMENTS);
    }

    @Test(expected = RuntimeException.class)
    public void map_withWrongOrderParameters_notOk() {
        mapper.map(INVALID_STRING_WRONG_ORDER);
    }

    @Test(expected = NullPointerException.class)
    public void map_nullString_notOk() {
        mapper.map(null);
    }
}
