package core.basesyntax.services.impl;

import core.basesyntax.db.FruitDataBase;
import core.basesyntax.dto.TransactionDto;
import core.basesyntax.handlers.DecrementFruit;
import core.basesyntax.handlers.IncrementFruit;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.services.FruitService;
import core.basesyntax.strategy.FruitStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static List<TransactionDto> validDtoList;
    private static List<TransactionDto> emptyList;
    private static final String CHERRY = "cherry";
    private static final String BANANA = "banana";
    private static final Integer CHERRY_AMOUNT = 115;
    private static final Integer BANANA_AMOUNT = 18;
    private static FruitService fruitService;
    private String expected;
    private String actual;

    @BeforeClass
    public static void beforeClass() {
        validDtoList = new ArrayList<>();
        emptyList = new ArrayList<>();
        Map<Operation, FruitStrategy> fruitStrategies = new HashMap<>();
        FruitStrategy fruitsIncreasing = new IncrementFruit();
        FruitStrategy fruitsDecreasing = new DecrementFruit();
        fruitStrategies.put(Operation.getOperation("b"), fruitsIncreasing);
        fruitStrategies.put(Operation.getOperation("r"), fruitsIncreasing);
        fruitStrategies.put(Operation.getOperation("s"), fruitsIncreasing);
        fruitStrategies.put(Operation.getOperation("p"), fruitsDecreasing);

        fruitService = new FruitServiceImpl(fruitStrategies);

        validDtoList.add(new TransactionDto(Operation.BALANCE, new Fruit(CHERRY), CHERRY_AMOUNT));
        validDtoList.add(new TransactionDto(Operation.SUPPLY, new Fruit(BANANA), BANANA_AMOUNT));
        validDtoList.add(new TransactionDto(Operation.RETURN, new Fruit(CHERRY), CHERRY_AMOUNT));
        validDtoList.add(new TransactionDto(Operation.PURCHASE, new Fruit(CHERRY), CHERRY_AMOUNT));
    }

    @After
    public void tearDown() {
        FruitDataBase.getFruitMap().clear();
    }

    @Test
    public void applyOperationOnFruitDto_TestWithValidData_OK() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit(CHERRY), CHERRY_AMOUNT);
        expected.put(new Fruit(BANANA), BANANA_AMOUNT);
        fruitService.applyOperation(validDtoList);
        Map<Fruit, Integer> actual = FruitDataBase.getFruitMap();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperationOnFruitDto_TestWithEmptyList_OK() {
        Map<Fruit, Integer> expected = new HashMap<>();
        fruitService.applyOperation(emptyList);
        Map<Fruit, Integer> actual = FruitDataBase.getFruitMap();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_TestWithValidData_OK() {
        FruitDataBase.getFruitMap().put(new Fruit(CHERRY), CHERRY_AMOUNT);
        FruitDataBase.getFruitMap().put(new Fruit(BANANA), BANANA_AMOUNT);
        expected = "fruit,quantity" + System.lineSeparator()
                + "cherry, 115" + System.lineSeparator()
                + "banana, 18";
        actual = fruitService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_TestDatabaseIsEmpty_OK() {
        expected = "fruit,quantity";
        actual = fruitService.createReport();
        Assert.assertEquals(expected, actual);
    }

}
