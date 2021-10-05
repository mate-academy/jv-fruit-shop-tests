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
    private String correctInputFilePath = "src/main/resources/input.csv";
    private Reader reader;
    private Validator validator;
    private List<String> validData;
    private List<String> nullInput;
    private List<String> validatorNegativeOperation;
    private List<String> validatorShortString;

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
