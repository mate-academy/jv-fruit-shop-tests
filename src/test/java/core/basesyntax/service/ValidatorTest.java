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
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;
    private static final Map<String, ActivityHandler> activityHandlerMap = new HashMap<>();
    private List<String> inputData;

    @BeforeClass
    public static void beforeClass() throws Exception {
        FruitStorageDao fruitDao = new FruitStorageDaoImpl();
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

    @Test(expected = RuntimeException.class)
    public void validateData_emptyData_notOk() {
        validator.validateData(Collections.emptyList());
    }

    @Test(expected = RuntimeException.class)
    public void validateData_dataWithoutFirstLine_notOk() {
        inputData = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
        validator.validateData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void validateData_dataWithNegativeValue_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,-20",
                "b,apple,-100",
                "s,banana,-100",
                "p,banana,-13");
        validator.validateData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void validateData_dataWithWrongActivityType_notOk() {
        inputData = List.of("type,fruit,quantity",
                "q,banana,20",
                "w,apple,100",
                "e,banana,100",
                "t,banana,13");
        validator.validateData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void validateData_dataWithWrongFruitName_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,1banana,20",
                "b,2apple,100",
                "s,44444banana,100",
                " p,banana,13");
        validator.validateData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void validateData_dataWithWrongValue_notOk() {
        inputData = List.of("type,fruit,quantity",
                "b,banana,0",
                "b,apple,100",
                "s,banana,0",
                "p,banana,13");
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
