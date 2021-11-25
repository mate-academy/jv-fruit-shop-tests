package service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.ValidatorService;

public class ValidatorServiceImplTest {
    private static ValidatorService validatorService;
    private static List<String> actual;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        validatorService = new ValidatorServiceImpl();
        actual = new ArrayList<>();
    }

    @Test
    public void isValidData_emptyList_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("no any action");
        validatorService.isValidData(new ArrayList<>());
    }

    @Test
    public void isValidData_invalidAmount_notOk() {
        actual.add("type,fruit,quantity");
        actual.add("b,banana,20");
        actual.add("b,apple,-100");
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Invalid data");
        validatorService.isValidData(actual);
    }

    @Test
    public void isValidData_countColumn_notOk() {
        actual.add("type,fruit,quantity");
        actual.add("b,banana,2,0");
        actual.add("b,apple,-100");
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Invalid data");
        validatorService.isValidData(actual);
    }

    @Test
    public void isValidData_amountIsNotInteger_notOk() {
        actual.add("type,fruit,quantity");
        actual.add("b,banana,twenty");
        actual.add("b,apple,-100");
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Error!! the third element is invalid");
        validatorService.isValidData(actual);
    }

    @Test
    public void isValidData_validData_notOk() {
        actual.add("type,fruit,quantity");
        actual.add("b,banana,20");
        actual.add("b,apple,100");
        Assert.assertTrue(validatorService.isValidData(actual));
    }

    @After
    public void tearDown() throws Exception {
        actual.clear();
    }
}
