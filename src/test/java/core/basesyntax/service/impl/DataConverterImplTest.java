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
    private static final DataConverter CONVERTER = new DataConverterImpl();
    private static final String COMMA = ",";
    private static final String VALID_TYPE_COLUMN = "type";
    private static final String VALID_PRODUCT_NAME_COLUMN = "fruit";
    private static final String VALID_QUANTITY_COLUMN = "quantity";
    private static final String INVALID_TYPE_COLUMN = "tepa";
    private static final String INVALID_PRODUCT_NAME_COLUMN = "car";
    private static final String SHORT_PRODUCT_NAME = "ab";
    private static final String INVALID_QUANTITY_COLUMN = "price";
    private static final String VALID_BODY = "b,banana,20";
    private static final String INVALID_HEADER_MSG = "Invalid header. Header=";

    @BeforeAll
    static void setUp() {
        new RecordMapperStrategyImpl(Map.of("fruit", new FruitRecordMapper()));
    }

    @Test
    void convert_withValidHeader_ok() {
        String validHeader = VALID_TYPE_COLUMN
                + COMMA + VALID_PRODUCT_NAME_COLUMN
                + COMMA + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(validHeader, VALID_BODY);
        List<Record> expected = CONVERTER.convert(data);
        List<Record> actual = List.of(new Record(Operation.BALANCE,new Fruit("banana", 20)));
        assertEquals(expected, actual);
    }

    @Test
    void convert_withInvalidType_notOk() {
        String header = INVALID_TYPE_COLUMN + COMMA
                + VALID_PRODUCT_NAME_COLUMN
                + COMMA + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> CONVERTER.convert(data));
        String expectedMessage = INVALID_HEADER_MSG + header;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void converter_withInvalidQuantity_notOk() {
        String header = VALID_TYPE_COLUMN + COMMA
                + VALID_PRODUCT_NAME_COLUMN
                + COMMA + INVALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> CONVERTER.convert(data));
        String expectedMessage = INVALID_HEADER_MSG + header;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void converter_withInvalidProductName_notOk() {
        String header = VALID_TYPE_COLUMN
                + COMMA + INVALID_PRODUCT_NAME_COLUMN
                + COMMA + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> CONVERTER.convert(data));
        String expectedMessage =
                "Product type is not supported. Type=" + INVALID_PRODUCT_NAME_COLUMN;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void converter_withProductNameIsShort_notOk() {
        String header = VALID_TYPE_COLUMN
                + COMMA + SHORT_PRODUCT_NAME
                + COMMA + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> CONVERTER.convert(data));
        String expectedMessage = "Product name shorter than 3 letters. Header=" + header;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void convert_withInvalidNumberOfColumns_notOk() {
        String header = VALID_TYPE_COLUMN
                + COMMA + VALID_PRODUCT_NAME_COLUMN;
        Exception exception =
                assertThrows(RuntimeException.class, () -> CONVERTER.convert(List.of(header)));
        String expectedMessage = "Number of columns is invalid. Header=" + header;
        assertEquals(expectedMessage, exception.getMessage());
    }
}
