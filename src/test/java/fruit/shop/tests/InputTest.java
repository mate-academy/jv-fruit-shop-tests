package fruit.shop.tests;

import fruit.shop.service.InputDataValidator;
import fruit.shop.service.InvalidTransactionInputException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputTest {
    private static final InputDataValidator INPUT_DATA_VALIDATOR = new InputDataValidator();

    @Test
    public void input_correct_ok() {
        String[] input = new String[] {"b","banana", "20"};
        Assertions.assertEquals(INPUT_DATA_VALIDATOR.test(input), input);
    }

    @Test
    public void input_containsNull_notOk() {
        String[] input = new String[] {"b", null, "20"};
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> INPUT_DATA_VALIDATOR.test(input));
    }

    @Test
    public void input_containsMoreArgs_notOk() {
        String[] input = new String[] {"b", "apple", "20", "test"};
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> INPUT_DATA_VALIDATOR.test(input));
    }

    @Test
    public void input_missingArgs_notOk() {
        String[] input = new String[] {"b", "20"};
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> INPUT_DATA_VALIDATOR.test(input));
    }

    @Test
    public void input_negativeQuantity_notOk() {
        String[] input = new String[] {"b", "apple", "-20"};
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> INPUT_DATA_VALIDATOR.test(input));
    }

    @Test
    public void input_invalidOrder_notOk() {
        String[] input = new String[] {"b", "20", "apple"};
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> INPUT_DATA_VALIDATOR.test(input));
    }

    @Test
    public void input_unknownFruit_notOk() {
        String[] input = new String[] {"b", "not existing fruit", "20"};
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> INPUT_DATA_VALIDATOR.test(input));
    }

    @Test
    public void input_quantityNonNumerical_notOk() {
        String[] input = new String[] {"b", "apple", "text"};
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> INPUT_DATA_VALIDATOR.test(input));
    }
}
