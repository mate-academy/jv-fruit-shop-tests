package core.basesyntax;

import static core.basesyntax.service.impl.Operation.BALANCE;
import static core.basesyntax.service.impl.Operation.PURCHASE;
import static core.basesyntax.service.impl.Operation.SUPPLY;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.AddOperation;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.Operation;
import core.basesyntax.service.impl.SubtractOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static Map<Operation, FruitOperationHandler> operations = new HashMap<>();
    private static FruitService fruitService;

    @BeforeClass
    public static void initialization() {
        fruitService = new FruitServiceImpl(operations);

        operations.put(BALANCE, new AddOperation());
        operations.put(Operation.PURCHASE, new SubtractOperation());
        operations.put(Operation.RETURN, new AddOperation());
        operations.put(Operation.SUPPLY, new AddOperation());
    }

    @Test
    public void saveData_saveCorrectInput_ok() {
        final Fruit banana = new Fruit("banana");
        List<FruitRecordDto> correctInput = new ArrayList<>();
        correctInput.add(new FruitRecordDto(BALANCE, "banana", 20));
        correctInput.add(new FruitRecordDto(SUPPLY, "banana", 40));
        fruitService.saveData(correctInput);
        int expected = 60;
        int actual = Storage.fruits.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void saveData_saveIncorrectInput_notOk() {
        List<FruitRecordDto> incorrectInput = new ArrayList<>();
        incorrectInput.add(new FruitRecordDto(BALANCE, "apple", 40));
        incorrectInput.add(new FruitRecordDto(SUPPLY, "apple", 15));
        incorrectInput.add(new FruitRecordDto(PURCHASE, "apple", 60));
        fruitService.saveData(incorrectInput);
    }

    @Test
    public void createReport_createWithOneLine_ok() {
        Storage.fruits.put(new Fruit("apple"), 37);
        String expected = "fruit,quantity"
                + "\n"
                + "apple,37"
                + "\n";
        String actual = fruitService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_createWithThreeLines_ok() {
        Storage.fruits.put(new Fruit("banana"), 34);
        Storage.fruits.put(new Fruit("apple"), 56);
        Storage.fruits.put(new Fruit("mango"), 11);
        String expected = "fruit,quantity"
                + "\n"
                + "banana,34"
                + "\n"
                + "apple,56"
                + "\n"
                + "mango,11"
                + "\n";
        String actual = fruitService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clearAfterEachTest() {
        Storage.fruits.clear();
    }
}
