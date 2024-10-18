package core.basesyntax.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataValidatorTest {
    private DataValidator dataValidator;

    @BeforeEach
    public void setUp() {
        dataValidator = new DataValidator();
    }

    @Test
    public void givenEmptyList_whenValidate_thenThrowsException() {
        List<String> inputData = Collections.emptyList();

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    public void givenCorrectHeader_whenValidate_thenReturnsTrue() {
        List<String> inputData = Arrays.asList("type,fruit,quantity");
        boolean isCorrectHeader = dataValidator.validate(inputData);

        Assertions.assertTrue(isCorrectHeader);
    }

    @Test
    public void givenInvalidQuantity_whenValidate_thenThrowsException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,abc"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    public void givenInvalidHeaderInFirstLine_whenValidate_thenThrowsException() {
        List<String> inputData = Collections.singletonList("e,fruit,quantity");

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    public void givenIncompleteData_whenValidate_thenThrowsException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    public void givenInvalidInputData_whenValidate_thenThrowsException() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "x,banana,20"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }
}
