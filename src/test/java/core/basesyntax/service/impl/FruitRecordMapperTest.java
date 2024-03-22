package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.record.Operation;
import core.basesyntax.record.Record;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitRecordMapperTest {
    private static final String SEPARATOR = ",";
    private static final String VALID_OPERATION_TYPE = "s";
    private static final String VALID_NAME = "apple";
    private static final int VALID_COUNT = 10;
    private static final String INVALID_OPERATION_TYPE = "y";
    private static final String INVALID_COUNT_TYPE = "TEN";
    private static final String SHORT_PRODUCT_NAME = "a";
    private static final String NEGATIVE_AMOUNT = "-1";
    private static final String LENGTH_CANNOT_BE_LESS_MSG =
            "Product name length cannot be less than 3. Line=";
    private static final String NO_OPERATION_WITH_CODE_MSG = "No operation with code '";
    private static final String FOUND_MSG = "' found";
    private static final String QUANTITY_IS_NOT_A_NUMBER_MSG = "Quantity is not a number. Line=";
    private static final String QUANTITY_CANNOT_BE_NEGATIVE_MSG =
            "Quantity cannot be negative. Line=";
    private static final String INCORRECT_LINE_FORMAT_MSG = "Incorrect line format. Line=";
    private static final String OPERATION_TYPE_CANNOT_BE_EMPTY_MSG =
            "Operation type cannot be empty. Line=";
    private final FruitRecordMapper mapper = new FruitRecordMapper();

    @Test
    void getRecordsFromLines_validLines_ok() {
        List<String> lines = new ArrayList<>();
        lines.add(VALID_OPERATION_TYPE + SEPARATOR + VALID_NAME + SEPARATOR + VALID_COUNT);
        List<Record> actual = mapper.getRecordsFromLines(lines);

        List<Record> expected = List.of(
                new Record(Operation.SUPPLY, new Fruit(VALID_NAME, VALID_COUNT)));
        assertEquals(expected, actual);
    }

    @Test
    void getRecordsFromLines_withEmptyLines_ok() {
        List<Record> actual = mapper.getRecordsFromLines(List.of(""));

        List<Record> expected = List.of();
        assertEquals(expected, actual);
    }

    @Test
    void getRecordsFromLines_withInvalidOperationType_notOk() {
        List<String> lines = new ArrayList<>();
        lines.add(INVALID_OPERATION_TYPE + SEPARATOR + VALID_NAME + SEPARATOR + VALID_COUNT);

        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));

        String expectedMessage = NO_OPERATION_WITH_CODE_MSG + INVALID_OPERATION_TYPE + FOUND_MSG;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withInvalidCountType_notOk() {
        List<String> lines = new ArrayList<>();
        String line =
                VALID_OPERATION_TYPE + SEPARATOR + VALID_NAME + SEPARATOR + INVALID_COUNT_TYPE;
        lines.add(line);

        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));

        String expectedMessage = QUANTITY_IS_NOT_A_NUMBER_MSG + line;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withCountLessThenZero_notOk() {
        List<String> lines = new ArrayList<>();
        String invalidLine =
                VALID_OPERATION_TYPE + SEPARATOR + VALID_NAME + SEPARATOR + NEGATIVE_AMOUNT;
        lines.add(invalidLine);

        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));

        String expectedMessage = QUANTITY_CANNOT_BE_NEGATIVE_MSG + invalidLine;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withNameLengthLessThenThree_notOk() {
        List<String> lines = new ArrayList<>();
        String invalidLine =
                VALID_OPERATION_TYPE + SEPARATOR + SHORT_PRODUCT_NAME + SEPARATOR + VALID_COUNT;
        lines.add(invalidLine);

        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));

        String expectedMessage = LENGTH_CANNOT_BE_LESS_MSG + invalidLine;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withMissingParameter_notOk() {
        List<String> lines = new ArrayList<>();
        String line = VALID_OPERATION_TYPE + SEPARATOR + VALID_NAME;
        lines.add(line);

        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> mapper.getRecordsFromLines(lines));

        String expectedMessage = INCORRECT_LINE_FORMAT_MSG + line;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withEmptyOperationType_notOk() {
        List<String> lines = new ArrayList<>();
        String line = SEPARATOR + VALID_NAME + SEPARATOR + VALID_COUNT;
        lines.add(line);

        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));

        String expectedMessage = OPERATION_TYPE_CANNOT_BE_EMPTY_MSG + line;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withEmptyName_notOk() {
        List<String> lines = new ArrayList<>();
        String line = VALID_OPERATION_TYPE + SEPARATOR + SEPARATOR + VALID_COUNT;
        lines.add(line);

        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));

        String expectedMessage = LENGTH_CANNOT_BE_LESS_MSG + line;
        assertEquals(expectedMessage, exception.getMessage());
    }
}
