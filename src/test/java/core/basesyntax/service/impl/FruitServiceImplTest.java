package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitDataDto;
import core.basesyntax.operations.AddOperation;
import core.basesyntax.operations.Operation;
import core.basesyntax.operations.Operations;
import core.basesyntax.operations.SubtractOperation;
import core.basesyntax.service.FruitService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final Fruit CHERRY = new Fruit("cherry");
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit BLUEBERRY = new Fruit("blueberry");
    private static final Integer CHERRY_QUANTITY = 50;
    private static final Integer BANANA_QUANTITY = 25;
    private static final Integer BLUEBERRY_QUANTITY = 100;
    private static final Integer CHERRY_INCORRECT_QUANTITY = 400;
    private static Map<Operations, Operation> chooseOperation;
    private FruitService fruitService;
    private List<FruitDataDto> fruitDataDtoList;

    @BeforeClass
    public static void beforeClass() {
        chooseOperation = new HashMap<>();
        chooseOperation.put(Operations.BALANCE, new AddOperation());
        chooseOperation.put(Operations.PURCHASE, new SubtractOperation());
        chooseOperation.put(Operations.RETURN, new AddOperation());
        chooseOperation.put(Operations.SUPPLY, new AddOperation());
    }

    @Before
    public void before() {
        fruitService = new FruitServiceImpl(chooseOperation);
        Storage.getFruits().put(new Fruit("cherry"), 150);
        Storage.getFruits().put(new Fruit("banana"), 75);
        Storage.getFruits().put(new Fruit("blueberry"), 200);
        fruitDataDtoList = new ArrayList<>();
    }

    @Test
    public void applyOperationsOnFruitsDtoWithCorrectDataTest_Ok() {
        fruitDataDtoList.add(new FruitDataDto(Operations.SUPPLY, CHERRY, CHERRY_QUANTITY));
        fruitDataDtoList.add(new FruitDataDto(Operations.RETURN, BANANA, BANANA_QUANTITY));
        fruitDataDtoList.add(new FruitDataDto(Operations.PURCHASE, BLUEBERRY, BLUEBERRY_QUANTITY));

        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("cherry"), 200);
        expected.put(new Fruit("banana"), 100);
        expected.put(new Fruit("blueberry"), 100);

        fruitService.applyOperationsOnFruitsDto(fruitDataDtoList);
        Map<Fruit, Integer> actual = Storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void applyOperationsOnFruitsDtoWithIncorrectDataTest_NotOk() {
        fruitDataDtoList.add(new FruitDataDto(Operations
                .PURCHASE, CHERRY, CHERRY_INCORRECT_QUANTITY));
        fruitService.applyOperationsOnFruitsDto(fruitDataDtoList);
    }

    @Test
    public void generateReportTest_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "cherry,150" + System.lineSeparator()
                + "banana,75" + System.lineSeparator()
                + "blueberry,200";
        String actual = fruitService.generateReport();
        assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        Storage.getFruits().clear();
    }
}
