package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.record.Operation;
import core.basesyntax.record.Record;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitRecordMapperTest {
    private static final String COMMA = ",";
    private static final String VALID_OPERATION_TYPE = "s";
    private static final String VALID_NAME = "apple";
    private static final int VALID_COUNT = 10;
    private static final String INVALID_OPERATION_TYPE = "y";
    private static final String LENGTH_CANNOT_BE_LESS_MSG =
            "Product name length cannot be less than 3. Line=";
    private final List<String> lines = new ArrayList<>();
    private final FruitRecordMapper mapper = new FruitRecordMapper();

    @BeforeEach
    void setUp() {
        lines.clear();
        lines.add(VALID_OPERATION_TYPE + COMMA + VALID_NAME + COMMA + VALID_COUNT);
    }

    @Test
    void getRecordsFromLines_validLines_ok() {
        List<Record> expected = List.of(
                new Record(Operation.SUPPLY, new Fruit(VALID_NAME, VALID_COUNT)));
        List<Record> actual = mapper.getRecordsFromLines(lines);
        assertEquals(expected, actual);
    }

    @Test
    void getRecordsFromLines_withEmptyLines_ok() {
        List<Record> expected = List.of();
        List<Record> actual = mapper.getRecordsFromLines(List.of(""));
        assertEquals(expected, actual);
    }

    @Test
    void getRecordsFromLines_withInvalidOperationType_notOk() {
        lines.add(INVALID_OPERATION_TYPE + COMMA + VALID_NAME + COMMA + VALID_COUNT);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));
        String expectedMessage = "No operation with code '" + INVALID_OPERATION_TYPE + "' found";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withInvalidCountType_notOk() {
        String line = VALID_OPERATION_TYPE + COMMA + VALID_NAME + COMMA + "TEN";
        lines.add(line);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));
        String expectedMessage = "Quantity is not a number. Line=" + line;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withCountLessThenZero_notOk() {
        String invalidLine = VALID_OPERATION_TYPE + COMMA + VALID_NAME + COMMA + "-1";
        lines.add(invalidLine);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));
        String expectedMessage = "Quantity cannot be negative. Line=" + invalidLine;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withNameLengthLessThenThree_notOk() {
        String invalidLine = VALID_OPERATION_TYPE + COMMA + "a" + COMMA + VALID_COUNT;
        lines.add(invalidLine);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));
        String expectedMessage = LENGTH_CANNOT_BE_LESS_MSG + invalidLine;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withMissingParameter_notOk() {
        String line = VALID_OPERATION_TYPE + COMMA + VALID_NAME;
        lines.add(line);
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> mapper.getRecordsFromLines(lines));
        String expectedMessage = "Incorrect line format. Line=" + line;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withEmptyOperationType_notOk() {
        String line = COMMA + VALID_NAME + COMMA + VALID_COUNT;
        lines.add(line);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));
        String expectedMessage = "Operation type cannot be empty. Line=" + line;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRecordsFromLines_withEmptyName_notOk() {
        String line = VALID_OPERATION_TYPE + COMMA + COMMA + VALID_COUNT;
        lines.add(line);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> mapper.getRecordsFromLines(lines));
        String expectedMessage = LENGTH_CANNOT_BE_LESS_MSG + line;
        assertEquals(expectedMessage, exception.getMessage());
    }
}
