package core.basesyntax.service;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.service.activity.ActivityHandler;
import core.basesyntax.service.activity.ActivityType;
import core.basesyntax.service.activity.AddingHandler;
import core.basesyntax.service.activity.RemovingHandler;
import core.basesyntax.service.impl.ValidatorImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidatorTest {
    private static final String EXPECTED_MESSAGE = "Invalid input data, try again";
    private static Validator validator;
    private static Map<String, ActivityHandler> activityHandlerMap;
    private static List<String> inputData;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        FruitStorageDao fruitDao = new FruitStorageDaoImpl();
        activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(ActivityType.BALANCE.getName(), new AddingHandler(fruitDao));
        activityHandlerMap.put(ActivityType.PURCHASE.getName(), new RemovingHandler(fruitDao));
        activityHandlerMap.put(ActivityType.SUPPLY.getName(), new AddingHandler(fruitDao));
        activityHandlerMap.put(ActivityType.RETURN.getName(), new AddingHandler(fruitDao));
        validator = new ValidatorImpl(activityHandlerMap);
    }

    @Test(expected = RuntimeException.class)
    public void validateData_nullData_notOk() {
        validator.validateData(null);
    }

    @Test
    public void validateData_emptyData_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(Collections.emptyList());
    }

    @Test
    public void validateData_dataWithOnlyHeadLine_notOk() {
        inputData = List.of("b,banana,20");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithoutFirstLine_notOk() {
        inputData = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithWrongHeadLine_notOk() {
        inputData = List.of("type,fruit,quantity1",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithNegativeValue_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,-20",
                "b,apple,-100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithZeroValue_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,0",
                "b,apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithInvalidValue_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,030",
                "b,apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithDoubleValue_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,6.66",
                "b,apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithExtraComma_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,6,66",
                "b,apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithQuantityByLetter_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,six",
                "b,apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithWrongActivityType_notOk() {
        inputData = List.of("type,fruit,quantity",
                "q,banana,20",
                "w,apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithTwoLetterActivityType_notOk() {
        inputData = List.of("type,fruit,quantity",
                "qq,banana,20",
                "w,apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithUpperCaseActivityType_notOk() {
        inputData = List.of("type,fruit,quantity",
                "B,banana,20",
                "R,apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithWrongFruitName_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,1banana,20",
                "b,2apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_dataWithUpperCaseFruitName_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,Banana,20",
                "b,Apple,100");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_MESSAGE);
        validator.validateData(inputData);
    }

    @Test
    public void validateData_validData_ok() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
        boolean actual = validator.validateData(inputData);
        Assert.assertTrue(actual);
    }
}
