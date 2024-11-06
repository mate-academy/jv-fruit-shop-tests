package core.basesyntax.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataValidatorTest {
    private DataValidator dataValidator;

    @BeforeEach
    void setUp() {
        dataValidator = new DataValidator();
    }

    @Test
    void emptyList_throwsException() {
        List<String> inputData = Collections.emptyList();
        assertThrows(RuntimeException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    void invalidQuantity_throwsException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,:)"
        );
        assertThrows(RuntimeException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    void correctHeader_returnsTrue() {
        List<String> inputData = List.of("type,fruit,quantity");
        boolean isCorrectHeader = dataValidator.validate(inputData);
        assertTrue(isCorrectHeader);
    }

    @Test
    void invalidOperation_throwsException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "f,apple,10"
        );
        assertThrows(RuntimeException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    void incompleteData_throwsException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,apple"
        );
        assertThrows(RuntimeException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    void invalidHeader_throwsException() {
        List<String> inputData = List.of("c,fruit,quantity");
        assertThrows(RuntimeException.class, () ->
                dataValidator.validate(inputData));
    }
}
