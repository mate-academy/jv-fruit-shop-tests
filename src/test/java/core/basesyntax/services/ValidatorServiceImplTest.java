package core.basesyntax.services;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ValidatorServiceImplTest {
    private static ValidatorService validatorService;
    private static Map<Fruit, Integer> countingActivities;

    @Before
    public void setUp() {
        validatorService = new ValidatorServiceImpl();
        countingActivities = new HashMap<>();
        countingActivities.put(new Fruit("apple"), 100);
        countingActivities.put(new Fruit("orange"), -50);
    }

    @Test(expected = ValidationException.class)
    public void inputDataValidatorAbsentSplitSymbol_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("I'm title");
        inputData.add("I'm only long text");
        validatorService.inputDataValidator(inputData);
    }

    @Test(expected = ValidationException.class)
    public void inputDataValidatorWrongLength_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("I'm title");
        inputData.add("b,banana,20, I'm superfluous");
        validatorService.inputDataValidator(inputData);
    }

    @Test(expected = ValidationException.class)
    public void positiveQuantityValidator_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("I'm title");
        inputData.add("b,banana,-20");
        validatorService.positiveQuantityValidator(inputData);
    }

    @Test(expected = ValidationException.class)
    public void correctAmountValidator_NotOk() {
        validatorService.correctAmountValidator(countingActivities);
    }
}
