package core.basesyntax.service;

import java.util.List;
import org.junit.Test;

public class FileValidatorTest {
    private static DataValidator FILE_VALIDATOR = new FileValidator();
    private static List<String> CORRECT_CONTENT = List.of("type,fruit,quantity"
            + System.lineSeparator(),
            "b,banana,20" + System.lineSeparator(),
            "b,apple,100" + System.lineSeparator(),
            "s,banana,100" + System.lineSeparator(),
            "p,banana,13" + System.lineSeparator(),
            "r,apple,10" + System.lineSeparator(),
            "p,apple,20" + System.lineSeparator(),
            "p,banana,5" + System.lineSeparator(),
            "s,banana,50");
    private static List<String> NEGATIVE_NUMBERS_CONTENT = List.of("type,fruit,quantity"
            + System.lineSeparator(),
            "b,banana,20" + System.lineSeparator(),
            "b,apple,-100" + System.lineSeparator(),
            "s,banana,100" + System.lineSeparator(),
            "p,banana,-13" + System.lineSeparator(),
            "r,apple,10" + System.lineSeparator(),
            "p,apple,-20" + System.lineSeparator(),
            "p,banana,5" + System.lineSeparator(),
            "s,banana,-50");
    private static List<String> WRONG_SEQUENCE_CONTENT = List.of("type,fruit,quantity"
            + System.lineSeparator(),
            "s,banana,20" + System.lineSeparator(),
            "p,apple,100" + System.lineSeparator(),
            "b,banana,100" + System.lineSeparator(),
            "p,banana,13" + System.lineSeparator(),
            "s,apple,10" + System.lineSeparator(),
            "p,apple,20" + System.lineSeparator(),
            "p,banana,5" + System.lineSeparator(),
            "s,banana,50");
    private static List<String> INCORRECT_BALANCE_CONTENT = List.of("type,fruit,quantity"
            + System.lineSeparator(),
            "b,banana,20" + System.lineSeparator(),
            "b,apple,100" + System.lineSeparator(),
            "b,banana,100" + System.lineSeparator(),
            "b,banana,13" + System.lineSeparator(),
            "b,apple,10" + System.lineSeparator(),
            "b,apple,20" + System.lineSeparator(),
            "b,banana,5" + System.lineSeparator(),
            "b,banana,50");

    @Test(expected = RuntimeException.class)
    public void nullInputData_NotOk() {
        FILE_VALIDATOR.validate(null);
    }

    @Test(expected = RuntimeException.class)
    public void negativeNumbers_NotOk() {
        FILE_VALIDATOR.validate(NEGATIVE_NUMBERS_CONTENT);
    }

    @Test(expected = RuntimeException.class)
    public void incorrectBalance_NotOk() {
        FILE_VALIDATOR.validate(INCORRECT_BALANCE_CONTENT);
    }

    @Test(expected = RuntimeException.class)
    public void wrongOperationsSequence_NotOk() {
        FILE_VALIDATOR.validate(WRONG_SEQUENCE_CONTENT);
    }

    @Test
    public void correctValidation_Ok() {
        FILE_VALIDATOR.validate(CORRECT_CONTENT);
    }
}
