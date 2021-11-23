package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import core.basesyntax.service.activitiy.ActivityHandler;
import core.basesyntax.service.activitiy.ActivityType;
import core.basesyntax.service.activitiy.AddingHandler;
import core.basesyntax.service.activitiy.RemovingHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidatorCsvImplTest {
    private static Validator validator;
    private static Map<String, ActivityHandler> activityHandlerMap;
    private static List<String> fileData;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(ActivityType.BALANCE.getLetter(), new AddingHandler());
        activityHandlerMap.put(ActivityType.SUPPLY.getLetter(), new AddingHandler());
        activityHandlerMap.put(ActivityType.RETURN.getLetter(), new AddingHandler());
        activityHandlerMap.put(ActivityType.PURCHASE.getLetter(), new RemovingHandler());
        validator = new ValidatorCsvImpl(activityHandlerMap);
        fileData = new ArrayList<>();
    }

    @Before
    public void setUp() {
        fileData.clear();
        fileData.addAll(List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "s,apple,13",
                "r,banana,10",
                "r,apple,20",
                "p,banana,5",
                "p,apple,50"));
    }

    @Test
    public void validate_validFileData_Ok() {
        Assert.assertTrue(validator.validate(fileData));
    }

    @Test(expected = RuntimeException.class)
    public void validate_nullFileData_notOk() {
        validator.validate(null);
    }

    @Test
    public void validate_emptyFileData_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(Collections.emptyList());
    }

    //    @Test
    //    public void validate_onlyFirstLine_notOk() {
    //        fileData.clear();
    //        fileData.add("type,fruit,quantity");
    //        expectedException.expect(RuntimeException.class);
    //        expectedException.expectMessage("INPUT DATA IS INVALID");
    //        validator.validate(fileData);
    //    }

    @Test
    public void validate_onlyFirstLine_notOk() {
        fileData.clear();
        fileData.add("type,fruit,quantity");
        Assert.assertTrue(validator.validate(fileData));
    }

    @Test
    public void validate_notExistingFirstLine_notOk() {
        fileData.remove(0);
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_invalidFirstLine_notOk() {
        fileData.set(0, "type,fruit,quantity1");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_notExistingOperationType_notOk() {
        fileData.set(1, "a,banana,20");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_upperCaseOperationType_notOk() {
        fileData.set(2, "B,banana,20");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_twoCharactersOperationType_notOk() {
        fileData.set(3, "bb,banana,20");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_validOperationType_Ok() {
        fileData.set(1, "s,banana,20");
        Assert.assertTrue(validator.validate(fileData));
    }

    @Test
    public void validate_fruitNameContainsNotAlphabeticCharacter_notOk() {
        fileData.set(1, "b,banana1,20");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_fruitNameContainsUppercaseCharacter_notOk() {
        fileData.set(2, "b,Banana,20");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_validFruitName_Ok() {
        fileData.set(1, "s,banana,20");
        Assert.assertTrue(validator.validate(fileData));
    }

    @Test
    public void validate_quantityStartsWithZero_notOk() {
        fileData.set(1, "b,banana,020");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_quantityContainsLetters_notOk() {
        fileData.set(2, "b,banana,twenty");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_negativeQuantity_notOk() {
        fileData.set(3, "b,banana,-1");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_zeroQuantity_notOk() {
        fileData.set(4, "b,banana,0");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_notWholeNumberQuantity_notOk() {
        fileData.set(5, "b,banana,12.4");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_notWholeNumberQuantityCommaSeparated_notOk() {
        fileData.set(6, "b,banana,12,4");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("INPUT DATA IS INVALID");
        validator.validate(fileData);
    }

    @Test
    public void validate_validQuantity_Ok() {
        fileData.set(1, "b,banana,10");
        Assert.assertTrue(validator.validate(fileData));
    }
}
