package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.ValidatorServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorServiceTest {
    private static final String PATH = "src/test/resources/test.csv";
    private static ValidatorService validatorService;

    @BeforeClass
    public static void init() {
        validatorService = new ValidatorServiceImpl();
    }

    @Test
    public void isValidCorrectData_Ok() {
        List<String> dataList = new ArrayList<>();
        dataList.add("type,fruit,quantity");
        dataList.add("b,orange,30");
        dataList.add("b,kiwi,50");
        dataList.add("r,orange,15");
        boolean actual = validatorService.isValid(dataList);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidIncorrectHeaders_NotOk() {
        List<String> dataList = new ArrayList<>();
        dataList.add("operation,fruit,amount");
        dataList.add("b,orange,30");
        dataList.add("b,kiwi,50");
        dataList.add("r,orange,15");
        boolean actual = validatorService.isValid(dataList);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidIncorrectData_NotOk() {
        List<String> dataList = new ArrayList<>();
        dataList.add("type,fruit,quantity");
        dataList.add("b,banana,200");
        dataList.add("p,banana,42");
        dataList.add("supply,orange,line");
        boolean actual = validatorService.isValid(dataList);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidEmptyData_NotOk() {
        boolean actual = validatorService.isValid(Collections.EMPTY_LIST);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidNullInput_NotOk() {
        validatorService.isValid(null);
    }
}
