package shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.service.Validator;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeAll() {
        validator = new ValidatorImpl();
    }

    @Test(expected = RuntimeException.class)
    public void validator_not_valid_data_ok() {
        List<String> testList = new ArrayList<>();
        testList.add("not valid");
        validator.valid(testList);
    }

    @Test(expected = RuntimeException.class)
    public void validator_emptyList_notOk() {
        validator.valid(new ArrayList<>());
    }

    @Test(expected = RuntimeException.class)
    public void validator_empty_data_ok() {
        List<String> testList = new ArrayList<>();
        validator.valid(testList);
    }

    @Test
    public void validator_ok() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("r,apple,10");
        Assert.assertTrue(validator.valid(testList));
    }

    @Test
    public void validator_coupleData_ok() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("r,apple,10");
        testList.add("r,special,10");
        Assert.assertTrue(validator.valid(testList));
    }

    @Test(expected = RuntimeException.class)
    public void validator_wrongData_ok() {
        List<String> testList = new ArrayList<>();
        testList.add("fruit,quantity");
        testList.add("r,apple,10");
        validator.valid(testList);
    }
}
