package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.Operation;
import core.basesyntax.service.impl.AddOperation;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.RemoveOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static Map<OperationType, Operation> handlersOperation = new HashMap<>();
    private static FruitService fruitService;

    @BeforeClass
    public static void initialization() {
        fruitService = new FruitServiceImpl(handlersOperation);

        handlersOperation.put(OperationType.BALANCE, new AddOperation());
        handlersOperation.put(OperationType.SUPPLY, new AddOperation());
        handlersOperation.put(OperationType.RETURN, new AddOperation());
        handlersOperation.put(OperationType.PURCHASE, new RemoveOperation());
    }

    @Test
    public void save_correctData_OK() {
        final Fruit banana = new Fruit("banana");
        List<FruitRecordDto> correctData = new ArrayList<>();
        correctData.add(new FruitRecordDto(OperationType.BALANCE, "banana", 75));
        correctData.add(new FruitRecordDto(OperationType.SUPPLY, "banana", 25));
        fruitService.saveData(correctData);
        int expected = 100;
        int actual = Storage.fruits.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void save_incorrectInput_notOK() {
        List<FruitRecordDto> incorrectData = new ArrayList<>();
        incorrectData.add(new FruitRecordDto(OperationType.BALANCE, "apple", 40));
        incorrectData.add(new FruitRecordDto(OperationType.SUPPLY, "apple", 15));
        incorrectData.add(new FruitRecordDto(OperationType.PURCHASE, "apple", 60));
        fruitService.saveData(incorrectData);
    }

    @Test
    public void getReport_oneLine_OK() {
        Storage.fruits.put(new Fruit("apple"), 37);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,37"
                + System.lineSeparator();
        String actual = fruitService.getReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getReport_threeLines_OK() {
        Storage.fruits.put(new Fruit("banana"), 34);
        Storage.fruits.put(new Fruit("apple"), 56);
        Storage.fruits.put(new Fruit("grape"), 11);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,34"
                + System.lineSeparator()
                + "apple,56"
                + System.lineSeparator()
                + "grape,11"
                + System.lineSeparator();
        String actual = fruitService.getReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clearAfterEach() {
        Storage.fruits.clear();
    }
}
