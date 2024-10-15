package core.basesyntax.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataValidatorTest {
    private final DataValidator dataValidator = new DataValidator();

    @Test
    public void checkForEmptyList_validate_Ok() {
        List<String> inputData = Arrays.asList("type,fruit,quantity");
        boolean isCorrectHeader = dataValidator.validate(inputData);

        Assertions.assertTrue(isCorrectHeader);
    }

    @Test
    public void invalidQuantityThrowException_validate_NotOk() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,abc"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    public void invalidHeaderInFirstLine_validate_NotOk() {
        List<String> inputData = Collections.singletonList("e,fruit,quantity");

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    public void incompleteDataThrowException_validate_NotOk() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }

    @Test
    public void invalidInputDataThrowException_validate_NotOk() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "x,banana,20"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataValidator.validate(inputData));
    }
}
