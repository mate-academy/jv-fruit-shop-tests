package core.basesyntax.service.validators;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InputValidatorTest {
    private static final InputValidator validator = new InputValidatorImpl();
    private static final String firstActivity = "b,apple,10";
    private static final String secondActivity = "p,pear,10";
    private static final String invalidAmountInActivity = "bs,fruit,bro";
    private static final String emptyOperaionParameter = ",fruit,bro";
    private static final String emptyFruitParameter = "b,,bro";
    private static final String invalidNumberInDataLine = "hi,there,";
    private static final String validHead = "type,fruit,quantity";
    private static final String EmptyDataMessage = "Input data is empty";
    private static final String EmptyDataLineMessage
            = "Data line is empty";
    private static final String InvalidActivityMessage
            = "Invalid parameters in Activity";
    private static final String InvalidHeadMessage
            = "Input head is invalid";
    private static final String InvalidNumberOfColumnsInDataLineMessage
            = "Invalid number of columns in data line";
    private static final String notUniqueBalanceElementsMessage
            = "Invalid number of columns in data line";
    private static final String fruitNotInStorage
            = "Founded fruit not in Storage";
    private List<String> data;
    private IllegalArgumentException exception;

    @BeforeEach
    void setUp() {
        data = new ArrayList<>();
        data.add(validHead);
    }

    @Test
    void validateWorkabilityOk() {
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        validator.validate(data);
    }

    @Test
    void validateEmptyDataNotOk() {
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(null));
        Assertions.assertEquals(EmptyDataMessage, exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(new ArrayList<>()));
        Assertions.assertEquals(EmptyDataMessage, exception.getMessage());
    }

    @Test
    void validateEmptyDataLineNotOk() {
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(List.of("")));
        Assertions.assertEquals(EmptyDataLineMessage, exception.getMessage());
    }

    @Test
    void validateInvalidNumberOfParametersNotOk() {
        exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    data.add(invalidNumberInDataLine);
                    validator.validate(data);
                });
        Assertions.assertEquals(InvalidNumberOfColumnsInDataLineMessage,
                exception.getMessage());
    }

    @Test
    void validateInvalidHeadNotOk() {
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(List.of(firstActivity)));
        Assertions.assertEquals(InvalidHeadMessage, exception.getMessage());
    }

    @Test
    void validateInvalidActivityNotOk() {
        exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    data.add(invalidAmountInActivity);
                    validator.validate(data);
                });
        Assertions.assertEquals(InvalidActivityMessage,
                exception.getMessage());
        exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    data.remove(data.size() - 1);
                    data.add(emptyOperaionParameter);
                    validator.validate(data);
                });
        Assertions.assertEquals(InvalidActivityMessage,
                exception.getMessage());
        exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    data.remove(data.size() - 1);
                    data.add(emptyFruitParameter);
                    validator.validate(data);
                });
        Assertions.assertEquals(InvalidActivityMessage,
                exception.getMessage());
    }

    @Test
    void validateNotUniqueBalanceElementsNotOk() {
        exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    data.add(firstActivity);
                    data.add(firstActivity);
                    validator.validate(data);
                });
        Assertions.assertEquals(notUniqueBalanceElementsMessage,
                exception.getMessage());
    }

    @Test
    void validateFruitsInStorageNotOk() {
        exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    data.add(secondActivity);
                    validator.validate(data);
                });
        Assertions.assertEquals(fruitNotInStorage,
                exception.getMessage());
    }
}
