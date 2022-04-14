package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.Operation;
import core.basesyntax.services.handlers.BalanceFruitHandler;
import core.basesyntax.services.handlers.DecreaseFruitHandler;
import core.basesyntax.services.handlers.FruitHandler;
import core.basesyntax.services.handlers.IncreaseFruitHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStrategyImplTest {
    private static FruitStrategy fruitStrategy;
    private static Map<String, FruitHandler> fruitStrategyMap;

    @BeforeClass
    public static void beforeClass() {
        fruitStrategyMap = new HashMap<>();
    }

    @Before
    public void setUp() {
        fruitStrategyMap.put("r", new IncreaseFruitHandler());
        fruitStrategyMap.put("p", new DecreaseFruitHandler());
        fruitStrategyMap.put("b", new BalanceFruitHandler());
        fruitStrategy = new FruitStrategyImpl(fruitStrategyMap);
    }

    @Test(expected = ValidationException.class)
    public void getHandler_NotOk() {
        String typeOperation = "l";
        fruitStrategy.getHandler(typeOperation);
    }

    @Test
    public void getHandler_Ok() {
        FruitHandler actual = fruitStrategy.getHandler(Operation.TypeOperation.RETURN.getType());
        FruitHandler expected = fruitStrategyMap.get(Operation.TypeOperation.RETURN.getType());
        assertEquals("Test failed: incorrect type operation",actual,expected);
    }
}
