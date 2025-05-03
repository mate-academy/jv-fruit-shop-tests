package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.record.Operation;
import core.basesyntax.record.Record;
import core.basesyntax.service.DataConverter;
import core.basesyntax.strategy.impl.RecordMapperStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String SEPARATOR = ",";
    private static final String VALID_TYPE_COLUMN = "type";
    private static final String VALID_PRODUCT_NAME_COLUMN = "fruit";
    private static final String VALID_QUANTITY_COLUMN = "quantity";
    private static final String VALID_PRODUCT_NAME = "banana";
    private static final int VALID_PRODUCT_AMOUNT = 20;
    private static final String INVALID_PRODUCT_NAME_COLUMN = "car";
    private static final String VALID_BODY = "b,banana,20";
    private static final String INVALID_TYPE_COLUMN = "tepa";
    private static final String INVALID_QUANTITY_COLUMN = "price";
    private static final String SHORT_PRODUCT_NAME_COLUMN = "ab";
    private static final String INVALID_HEADER_MSG = "Invalid header. Header=";
    private static final String PRODUCT_TYPE_IS_NOT_SUPPORTED_MSG =
            "Product type is not supported. Type=";
    private static final String PRODUCT_NAME_SHORTER_MSG =
            "Product name shorter than 3 letters. Header=";
    private static final String NUMBER_OF_COLUMNS_IS_INVALID_MSG =
            "Number of columns is invalid. Header=";
    private final DataConverter converter = new DataConverterImpl();

    @BeforeAll
    static void setUp() {
        new RecordMapperStrategyImpl(Map.of(VALID_PRODUCT_NAME_COLUMN, new FruitRecordMapper()));
    }

    @Test
    void convert_withValidHeader_ok() {
        String validHeader = VALID_TYPE_COLUMN
                + SEPARATOR + VALID_PRODUCT_NAME_COLUMN
                + SEPARATOR + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(validHeader, VALID_BODY);

        List<Record> actual = converter.convert(data);

        List<Record> expected = List.of(
                new Record(Operation.BALANCE,new Fruit(VALID_PRODUCT_NAME, VALID_PRODUCT_AMOUNT)));
        assertEquals(expected, actual);
    }

    @Test
    void convert_withInvalidType_notOk() {
        String header = INVALID_TYPE_COLUMN + SEPARATOR
                + VALID_PRODUCT_NAME_COLUMN
                + SEPARATOR + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);

        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(data));

        String expectedMessage = INVALID_HEADER_MSG + header;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void converter_withInvalidQuantity_notOk() {
        String header = VALID_TYPE_COLUMN + SEPARATOR
                + VALID_PRODUCT_NAME_COLUMN
                + SEPARATOR + INVALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);

        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(data));

        String expectedMessage = INVALID_HEADER_MSG + header;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void converter_withInvalidProductName_notOk() {
        String header = VALID_TYPE_COLUMN
                + SEPARATOR + INVALID_PRODUCT_NAME_COLUMN
                + SEPARATOR + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);

        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(data));

        String expectedMessage =
                PRODUCT_TYPE_IS_NOT_SUPPORTED_MSG + INVALID_PRODUCT_NAME_COLUMN;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void converter_withProductNameIsShort_notOk() {
        String header = VALID_TYPE_COLUMN
                + SEPARATOR + SHORT_PRODUCT_NAME_COLUMN
                + SEPARATOR + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);

        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(data));

        String expectedMessage = PRODUCT_NAME_SHORTER_MSG + header;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void convert_withInvalidNumberOfColumns_notOk() {
        String header = VALID_TYPE_COLUMN
                + SEPARATOR + VALID_PRODUCT_NAME_COLUMN;

        Exception exception =
                assertThrows(RuntimeException.class, () -> converter.convert(List.of(header)));

        String expectedMessage = NUMBER_OF_COLUMNS_IS_INVALID_MSG + header;
        assertEquals(expectedMessage, exception.getMessage());
    }
}
