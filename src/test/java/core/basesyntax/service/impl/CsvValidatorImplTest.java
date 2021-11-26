package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvValidatorImplTest {
    private static Validator validator;
    private static List<String> fileData;

    @BeforeClass
    public static void beforeClass() {
        validator = new CsvValidatorImpl();
        fileData = new ArrayList<>();
    }

    @Before
    public void setUp() {
        fileData = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "s,apple,13",
                "r,banana,10",
                "r,apple,20",
                "p,banana,5",
                "p,apple,50");
    }

    @Test
    public void isValid_validData_Ok() {
        Assert.assertTrue(validator.isValid(fileData));
    }

    @Test(expected = RuntimeException.class)
    public void isValid_null_notOk() {
        validator.isValid(null);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_emptyData_notOk() {
        validator.isValid(Collections.emptyList());
    }

    @Test(expected = RuntimeException.class)
    public void isValid_missingHeadData_notOk() {
        fileData.remove(0);
        validator.isValid(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_invalidHeadData_notOk() {
        fileData.set(0, "type,fruit");
        validator.isValid(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_notExistingOperationType_notOk() {
        fileData.set(1, "a,banana,20");
        validator.isValid(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_upperCaseOperationType_notOk() {
        fileData.set(2, "B,banana,20");
        validator.isValid(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_twoCharactersOperationType_notOk() {
        fileData.set(3, "bb,banana,20");
        validator.isValid(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_quantityContainsLetters_notOk() {
        fileData.set(1, "b,banana,twenty");
        validator.isValid(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_negativeQuantity_notOk() {
        fileData.set(1, "b,banana,-1");
        validator.isValid(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_notWholeNumberQuantity_notOk() {
        fileData.set(1, "b,banana,12.4");
        validator.isValid(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_notWholeNumberQuantityCommaSeparated_notOk() {
        fileData.set(1, "b,banana,12,4");
        validator.isValid(fileData);
    }
}
