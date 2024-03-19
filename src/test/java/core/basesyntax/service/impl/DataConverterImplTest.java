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
    private static final String COMMA = ",";
    private static final String VALID_TYPE_COLUMN = "type";
    private static final String VALID_PRODUCT_NAME_COLUMN = "fruit";
    private static final String VALID_QUANTITY_COLUMN = "quantity";
    private static final String INVALID_PRODUCT_NAME_COLUMN = "car";
    private static final String VALID_BODY = "b,banana,20";
    private static final String INVALID_HEADER_MSG = "Invalid header. Header=";
    private final DataConverter converter = new DataConverterImpl();

    @BeforeAll
    static void setUp() {
        //Init strategy for tests:
        new RecordMapperStrategyImpl(Map.of("fruit", new FruitRecordMapper()));
    }

    @Test
    void convert_withValidHeader_ok() {
        String validHeader = VALID_TYPE_COLUMN
                + COMMA + VALID_PRODUCT_NAME_COLUMN
                + COMMA + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(validHeader, VALID_BODY);
        List<Record> expected = List.of(new Record(Operation.BALANCE,new Fruit("banana", 20)));
        List<Record> actual = converter.convert(data);
        assertEquals(expected, actual);
    }

    @Test
    void convert_withInvalidType_notOk() {
        String header = "tepa" + COMMA
                + VALID_PRODUCT_NAME_COLUMN
                + COMMA + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(data));
        String expectedMessage = INVALID_HEADER_MSG + header;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void converter_withInvalidQuantity_notOk() {
        String header = VALID_TYPE_COLUMN + COMMA
                + VALID_PRODUCT_NAME_COLUMN
                + COMMA + "price";
        List<String> data = List.of(header, VALID_BODY);
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(data));
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
                assertThrows(IllegalArgumentException.class, () -> converter.convert(data));
        String expectedMessage =
                "Product type is not supported. Type=" + INVALID_PRODUCT_NAME_COLUMN;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void converter_withProductNameIsShort_notOk() {
        String header = VALID_TYPE_COLUMN
                + COMMA + "ab"
                + COMMA + VALID_QUANTITY_COLUMN;
        List<String> data = List.of(header, VALID_BODY);
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(data));
        String expectedMessage = "Product name shorter than 3 letters. Header=" + header;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void convert_withInvalidNumberOfColumns_notOk() {
        String header = VALID_TYPE_COLUMN
                + COMMA + VALID_PRODUCT_NAME_COLUMN;
        Exception exception =
                assertThrows(RuntimeException.class, () -> converter.convert(List.of(header)));
        String expectedMessage = "Number of columns is invalid. Header=" + header;
        assertEquals(expectedMessage, exception.getMessage());
    }
}
