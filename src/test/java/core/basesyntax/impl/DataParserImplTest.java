package core.basesyntax.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationHandlerBalanceImpl;
import core.basesyntax.operation.OperationHandlerPurchaseImpl;
import core.basesyntax.operation.OperationHandlerReturnImpl;
import core.basesyntax.operation.OperationHandlerSupplyImpl;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.OperationType;
import core.basesyntax.service.DataParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserImplTest {
    private static DataParser dataParser;
    private static Map<OperationType, OperationHandler> operationMap;
    private static OperationStrategy strategy;
    private static List<FruitRecordDto> fruitList;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void initializer() {
        expected = new HashMap<>();
        actual = new HashMap<>();
        fruitList = new ArrayList<>();
        fruitList.add(FruitRecordDto.builder()
                .type(OperationType.BALANCE)
                .fruit(Fruit.builder().name("banana").build())
                .amount(20).build());
        fruitList.add(FruitRecordDto.builder()
                .type(OperationType.SUPPLY)
                .fruit(Fruit.builder().name("banana").build())
                .amount(100).build());
        fruitList.add(FruitRecordDto.builder()
                .type(OperationType.PURCHASE)
                .fruit(Fruit.builder().name("banana").build())
                .amount(13).build());
        operationMap = new HashMap<>();
        operationMap.put(OperationType.BALANCE, new OperationHandlerBalanceImpl());
        operationMap.put(OperationType.PURCHASE, new OperationHandlerPurchaseImpl());
        operationMap.put(OperationType.RETURN, new OperationHandlerReturnImpl());
        operationMap.put(OperationType.SUPPLY, new OperationHandlerSupplyImpl());
        strategy = new OperationStrategyImpl(operationMap);
        dataParser = new DataParserImpl(strategy);
    }

    @After
    public void clear() {
        expected.clear();
        actual.clear();
    }

    @Test
    public void parseDto_RightInput_Ok() {
        expected.put(Fruit.builder().name("banana").build(), 107);
        actual = dataParser.parseDto(fruitList);
        Assert.assertEquals("Expected map with key: banana"
                + "and value: 107, but was: " + actual, expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parseDto_nullInput_NotOk() {
        List<FruitRecordDto> fruits = null;
        dataParser.parseDto(fruits);
    }

    @Test
    public void parseDto_emptyFile_Ok() {
        List<FruitRecordDto> fruits = new ArrayList<>();
        actual = dataParser.parseDto(fruits);
        Assert.assertEquals("Expected empty map with empty input, but was: "
                + actual, expected, actual);
    }
}
