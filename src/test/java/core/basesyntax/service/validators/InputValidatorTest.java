package core.basesyntax.service.validators;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InputValidatorTest {
    private static final InputValidator validator = new InputValidatorImpl();
    private static final String firstActivity = "b,apple,10";
    private static final String secondActivity = "p,pear,10";
    private static final String invalidAmountInActivity = "bs,fruit,bro";
    private static final String emptyOperationParameter = ",fruit,bro";
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

    @Before
    public void setUp() {
        data = new ArrayList<>();
        data.add(validHead);
    }

    @Test
    public void validateWorkabilityOk() {
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        validator.validate(data);
    }

    @Test
    public void validateEmptyDataNotOk() {
        try {
            validator.validate(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(EmptyDataMessage, e.getMessage());
        }
        try {
            validator.validate(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(EmptyDataMessage, e.getMessage());
        }
    }

    @Test
    public void validateEmptyDataLineNotOk() {
        try {
            validator.validate(List.of(""));
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(EmptyDataLineMessage, e.getMessage());
        }
    }

    @Test
    public void validateInvalidNumberOfParametersNotOk() {
        try {
            data.add(invalidNumberInDataLine);
            validator.validate(data);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(InvalidNumberOfColumnsInDataLineMessage,
                    e.getMessage());
        }
    }

    @Test
    public void validateInvalidHeadNotOk() {
        try {
            validator.validate(List.of(firstActivity));
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(InvalidHeadMessage,
                    e.getMessage());
        }
    }

    @Test
    public void validateInvalidActivityNotOk() {
        try {
            data.add(invalidAmountInActivity);
            validator.validate(data);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(InvalidActivityMessage, e.getMessage());
        }
        try {
            data.remove(data.size() - 1);
            data.add(emptyOperationParameter);
            validator.validate(data);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(InvalidActivityMessage, e.getMessage());
        }
        try {
            data.remove(data.size() - 1);
            data.add(emptyFruitParameter);
            validator.validate(data);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(InvalidActivityMessage, e.getMessage());
        }
    }

    @Test
    public void validateNotUniqueBalanceElementsNotOk() {
        try {
            data.add(firstActivity);
            data.add(firstActivity);
            validator.validate(data);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(notUniqueBalanceElementsMessage,
                    e.getMessage());
        }
    }

    @Test
    public void validateFruitsInStorageNotOk() {
        try {
            data.add(secondActivity);
            validator.validate(data);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(fruitNotInStorage,
                    e.getMessage());
        }
    }
}
