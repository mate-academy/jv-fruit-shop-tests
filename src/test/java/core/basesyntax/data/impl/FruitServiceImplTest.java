package core.basesyntax.data.impl;

import core.basesyntax.data.FruitService;
import core.basesyntax.dto.TransactionDto;
import core.basesyntax.handlers.FruitsDecrement;
import core.basesyntax.handlers.FruitsIncrement;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.FruitDataBase;
import core.basesyntax.strategy.FruitsStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final List<TransactionDto> validDtoList = new ArrayList<>();
    private static final List<TransactionDto> emptyList = new ArrayList<>();
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final Integer APPLE_AMOUNT = 100;
    private static final Integer BANANA_AMOUNT = 20;
    private static FruitService fruitService;
    private String expected;
    private String actual;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, FruitsStrategy> fruitStrategies = new HashMap<>();
        FruitsStrategy fruitsIncreasing = new FruitsIncrement();
        FruitsStrategy fruitsDecreasing = new FruitsDecrement();
        fruitStrategies.put(Operation.getEnum("b"), fruitsIncreasing);
        fruitStrategies.put(Operation.getEnum("r"), fruitsIncreasing);
        fruitStrategies.put(Operation.getEnum("s"), fruitsIncreasing);
        fruitStrategies.put(Operation.getEnum("p"), fruitsDecreasing);

        fruitService = new FruitServiceImpl(fruitStrategies);

        validDtoList.add(new TransactionDto(Operation.BALANCE, new Fruit(APPLE), APPLE_AMOUNT));
        validDtoList.add(new TransactionDto(Operation.SUPPLY, new Fruit(BANANA), BANANA_AMOUNT));
        validDtoList.add(new TransactionDto(Operation.RETURN, new Fruit(APPLE), APPLE_AMOUNT));
        validDtoList.add(new TransactionDto(Operation.PURCHASE, new Fruit(APPLE), APPLE_AMOUNT));
    }

    @After
    public void tearDown() {
        FruitDataBase.getFruitData().clear();
    }

    @Test
    public void applyOperationOnFruitDto_TestWithValidData() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit(APPLE), APPLE_AMOUNT);
        expected.put(new Fruit(BANANA), BANANA_AMOUNT);
        fruitService.applyOperationsOnFruitsDto(validDtoList);
        Map<Fruit, Integer> actual = FruitDataBase.getFruitData();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperationOnFruitDto_TestWithEmptyList() {
        Map<Fruit, Integer> expected = new HashMap<>();
        fruitService.applyOperationsOnFruitsDto(emptyList);
        Map<Fruit, Integer> actual = FruitDataBase.getFruitData();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_TestWithValidData() {
        FruitDataBase.getFruitData().put(new Fruit(APPLE), APPLE_AMOUNT);
        FruitDataBase.getFruitData().put(new Fruit(BANANA), BANANA_AMOUNT);
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana, 20" + System.lineSeparator()
                + "apple, 100";
        actual = fruitService.generateReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_TestDatabaseIsEmpty() {
        expected = "fruit,quantity";
        actual = fruitService.generateReport();
        Assert.assertEquals(expected, actual);
    }
}
