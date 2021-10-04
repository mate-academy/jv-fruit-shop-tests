package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dao.Reader;
import dao.ReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import validator.Validator;
import validator.ValidatorImpl;

public class ValidatorTests {
    private static Reader reader;
    private static Validator validator;

    private static List<String> validData;
    private static List<String> nullInput;
    private static List<String> validatorNegativeOperation;
    private static List<String> validatorShortString;
    private static final String correctInputFilePath = "src/main/resources/input.csv";

    @Before
    public void init() {
        reader = new ReaderImpl();
        validator = new ValidatorImpl();
        validatorNegativeOperation = new ArrayList<>();
        validatorShortString = new ArrayList<>();
        validatorNegativeOperation.add("p,banana,-1");
        validatorShortString.add("b,");
        validData = reader.read(correctInputFilePath);
    }

    @Test
    public void validator_validInput_Ok() {
        assertTrue(validator.validate(validData));
    }

    @Test
    public void validator_Null_NotOk() {
        assertThrows(RuntimeException.class, () -> validator.validate(nullInput));
    }

    @Test
    public void validator_negativeOperation_NotOk() {
        assertThrows(RuntimeException.class, () -> validator.validate(validatorNegativeOperation));
    }

    @Test
    public void validator_shortStringInput_NotOk() {
        assertThrows(RuntimeException.class, () -> validator.validate(validatorShortString));
    }
}
