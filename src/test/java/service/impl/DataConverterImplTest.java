package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverter;

class DataConverterImplTest {
    private static final String INVALID_OPERATION = "a";
    private static final String PROPER_HEADER = "type,fruit,quantity";
    private static final String FIRST_PROPER_LINE = "b,apple,32";
    private static final String SECOND_PROPER_LINE = "b,banana,12";
    private static final String THIRD_PROPER_LINE = "s,orange,56";
    private static final int NEGATIVE_QUANTITY = -9;
    private static DataConverter converter;
    private FruitTransaction firstTransaction;
    private FruitTransaction secondTransaction;
    private FruitTransaction thirdTransaction;

    @BeforeAll
    static void beforeAll() {
        converter = new DataConverterImpl();
    }

    @BeforeEach
    void setUp() {
        firstTransaction = new FruitTransaction("b", "apple", 32);
        secondTransaction = new FruitTransaction("b", "banana", 12);
        thirdTransaction = new FruitTransaction("s", "orange", 56);
    }

    @Test
    void convertToTransaction_validFile_ok() {
        List<String> properData = Arrays.asList(
                PROPER_HEADER, FIRST_PROPER_LINE, SECOND_PROPER_LINE, THIRD_PROPER_LINE);
        List<FruitTransaction> actualList = converter.convertToTransaction(properData);
        List<FruitTransaction> expectedList = List.of(
                firstTransaction, secondTransaction, thirdTransaction);
        assertEquals(expectedList, actualList);
    }

    @Test
    void convertToTransaction_emptyLines_notOk() {
        List<String> data = List.of();
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_dataWithoutQuantity_notOk() {
        List<String> data = Arrays.asList(
                PROPER_HEADER, FIRST_PROPER_LINE, "b,banana", THIRD_PROPER_LINE);
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> data = Arrays.asList(
                PROPER_HEADER, FIRST_PROPER_LINE, "s,grape,twenty two");
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_withoutFruit_notOk() {
        List<String> data = Arrays.asList(
                PROPER_HEADER, FIRST_PROPER_LINE, "s, ,6", THIRD_PROPER_LINE);
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_invalidOperation_notOk() {
        List<String> data = Arrays.asList(
                PROPER_HEADER, INVALID_OPERATION + " ,apple,32",
                SECOND_PROPER_LINE, THIRD_PROPER_LINE);
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> data = Arrays.asList(PROPER_HEADER, FIRST_PROPER_LINE, SECOND_PROPER_LINE,
                "b,pear,-45");
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_withoutHeader_notOk() {
        List<String> dataWithoutHeader = Arrays.asList(
                FIRST_PROPER_LINE, SECOND_PROPER_LINE, THIRD_PROPER_LINE);
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(dataWithoutHeader));
    }
}
