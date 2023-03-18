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
        Assert.assertEquals(mapper.map(VALID_STRING), EXPECTED_RESULT);
    }

    @Test
    public void map_withMissingParametersString_notOk() {
        try {
            mapper.map(INVALID_STRING_MISSING_ELEMENTS);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals(ArrayIndexOutOfBoundsException.class,
                    e.getClass());
        }
    }

    @Test
    public void map_withWrongOrderParameters_notOk() {
        try {
            mapper.map(INVALID_STRING_WRONG_ORDER);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals(e.getClass(),
                    RuntimeException.class);
        }
    }

    @Test
    public void map_nullString_notOk() {
        try {
            mapper.map(null);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals(e.getClass(),
                    NullPointerException.class);
        }
    }
}
