package core.basesyntax.service.impl;

import core.basesyntax.service.ValidatorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorServiceImplTest {
    private static ValidatorService validatorService;
    private static List<String> input;

    @BeforeClass
    public static void beforeClass() {
        validatorService = new ValidatorServiceImpl();
        input = new ArrayList<>();
    }

    @Before
    public void beforeEach() {
        input.add("type,fruit,quantity");
        input.add("b,banana,150");
        input.add("b,apple,100");
    }

    @Test
    public void isValid_service_validInput_ok() {
        boolean actual = validatorService.isValid(input);
        Assert.assertTrue(actual);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_emptyInput_notOk() {
        input = new ArrayList<>();
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_emptyLine_notOk() {
        input.add("");
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_wrongSpaceLine_notOk() {
        input.add("b, apple, 100");
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_wrongNumberLine_notOk() {
        input.add("b,apple,number");
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_wrongTypeLine_notOk() {
        input.add("k,apple,number");
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_negativeNumber_notOk() {
        input.add("b,apple,-1");
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_withoutNumber_notOk() {
        input.add("b,apple,");
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_withoutType_notOk() {
        input.add(",apple,150");
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_withoutFruitName_notOk() {
        input.add("b, ,150");
        validatorService.isValid(input);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_service_withoutComma_notOk() {
        input.add("b apple 150");
        validatorService.isValid(input);
    }

    @After
    public void afterEach() {
        input.clear();
    }
}
