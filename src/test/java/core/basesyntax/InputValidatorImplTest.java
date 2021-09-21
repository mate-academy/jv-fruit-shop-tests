package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.InputValidator;
import core.basesyntax.service.InputValidatorImpl;
import java.util.List;
import org.junit.Test;

public class InputValidatorImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final List<String> listOk = List.of(HEADER, "s,banana,100");
    private static final List<String> invalidOperationList = List.of(HEADER, ",banana,100");
    private static final List<String> invalidFruitTypeList = List.of(HEADER, "!,   ,100");
    private static final List<String> invalidFruitAmountList = List.of(HEADER, "s,banana,-100");
    private static final List<String> invalidColumnsAmount = List.of(HEADER, "s,banana,100,banana");

    private final InputValidator validator = new InputValidatorImpl();

    @Test
    public void validateInput_Ok() {
        assertTrue(validator.validateInput(listOk));
    }

    @Test (expected = IllegalArgumentException.class)
    public void validateInput_Not_Ok() {
        validator.validateInput(invalidOperationList);
        validator.validateInput(invalidFruitTypeList);
    }

    @Test (expected = IllegalArgumentException.class)
    public void validateInput_Not_Ok_Column_Amount() {
        validator.validateInput(invalidColumnsAmount);
    }

    @Test (expected = IllegalArgumentException.class)
    public void validateInput_Not_Ok_Fruit_Amount() {
        validator.validateInput(invalidFruitAmountList);
    }
}
