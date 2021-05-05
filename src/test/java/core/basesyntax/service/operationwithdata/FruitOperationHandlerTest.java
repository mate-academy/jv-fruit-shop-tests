package core.basesyntax.service.operationwithdata;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.fruitoperationstrategy.FruitStrategy;
import core.basesyntax.fruitoperationstrategy.FruitStrategyImpl;
import core.basesyntax.operations.Operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitOperationHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final Integer QUANTITY_FIRST = 30;
    private static final Integer QUANTITY_SECOND = 16;
    private static OperationHandler operationHandler;
    private static FruitStrategy fruitStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, FruitOperationService> fruitOperationServiceMap = new HashMap<>();
        fruitOperationServiceMap.put(Operation.BALANCE, new AddOperation());
        fruitOperationServiceMap.put(Operation.RETURN, new AddOperation());
        fruitOperationServiceMap.put(Operation.PURCHASE, new SubtractOperation());
        fruitOperationServiceMap.put(Operation.SUPPLY, new AddOperation());
        fruitStrategy = new FruitStrategyImpl(fruitOperationServiceMap);
        operationHandler = new FruitOperationHandler(fruitStrategy);
    }

    @Test
    public void operationProcessing_Ok() {
        List<FruitDto> fruitDtoList = new ArrayList<>();
        fruitDtoList.add(new FruitDto(Operation.BALANCE, FRUIT_NAME, QUANTITY_FIRST));
        fruitDtoList.add(new FruitDto(Operation.PURCHASE, FRUIT_NAME, QUANTITY_SECOND));
        int expected = 14;
        int actual = operationHandler.operationProcessing(fruitDtoList);
        assertEquals(expected, actual);
    }
}
