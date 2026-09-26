package core.basesyntax.dataconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.fruittransaction.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String INFO_ROW = "type,fruit,quantity";
    private static final String VALID_STRING = "b,banana,20";
    private static final String VALID_STRING_ZERO = "b,banana,0";
    private static final String INVALID_STRING_TYPE = "q,banana,20";
    private static final String INVALID_STRING_FRUIT = "b, ,20";
    private static final String INVALID_STRING_QUANTITY = "b,banana,b";
    private static final String INVALID_STRING_NEGATIVE_QUANTITY = "b,banana,-20";
    private static final String INVALID_STRING_TWO_ARGS = "b,banana";
    private static final String INVALID_STRING_DOTS = "b.banana.20";
    private static final String INVALID_STRING_EMPTY = "";
    private static DataConverterImpl dc;
    private static FruitTransaction validTransaction;

    @BeforeAll
    static void beforeAll() {
        dc = new DataConverterImpl();
        validTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
    }

    @Test
    void convert_ValidString_ok() {
        FruitTransaction expected = validTransaction;
        FruitTransaction actual = dc.convert(VALID_STRING);

        assertEquals(expected, actual, "Returned object is invalid");
    }

    @Test
    void convert_ValidStringZero_ok() {
        FruitTransaction expected =
                new FruitTransaction(Operation.BALANCE, "banana", 0);
        FruitTransaction actual = dc.convert(VALID_STRING_ZERO);

        assertEquals(expected, actual, "Returned object is invalid");
    }

    @Test
    void convert_InvalidString_notOk() {
        assertThrows(NumberFormatException.class, () -> dc.convert(INVALID_STRING_QUANTITY));

        assertThrows(IllegalArgumentException.class, () -> dc.convert(INVALID_STRING_TYPE));
        assertThrows(IllegalArgumentException.class, () -> dc.convert(INVALID_STRING_FRUIT));
        assertThrows(IllegalArgumentException.class, () ->
                dc.convert(INVALID_STRING_NEGATIVE_QUANTITY));
        assertThrows(IllegalArgumentException.class, () -> dc.convert(INVALID_STRING_EMPTY));
        assertThrows(IllegalArgumentException.class, () ->
                dc.convert(INVALID_STRING_TWO_ARGS));
        assertThrows(IllegalArgumentException.class, () -> dc.convert(INVALID_STRING_DOTS));
        assertThrows(IllegalArgumentException.class, () -> dc.convert(null));
    }

    @Test
    void convertFromStrings_ValidTransaction_ok() {
        List<String> source = List.of(INFO_ROW, VALID_STRING, VALID_STRING, VALID_STRING);
        List<FruitTransaction> expected =
                List.of(validTransaction, validTransaction, validTransaction);

        List<FruitTransaction> actual = dc.convertFromStrings(source);

        assertEquals(expected, actual, "Provided list is not equal");
    }

    @Test
    void convertFromStrings_nullTransaction_notOk() {
        assertThrows(IllegalArgumentException.class, () -> dc.convertFromStrings(null));
    }
}
