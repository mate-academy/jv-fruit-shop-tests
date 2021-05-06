package core.basesyntax.service.fruitservice;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.service.handlers.AddOperationStrategy;
import core.basesyntax.service.handlers.RecordHandler;
import core.basesyntax.service.handlers.RemoveOperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordStrategyImplTest {
    private static RecordHandler addOperation;
    private static FruitRecordStrategy fruitRecordStrategy;
    private static RecordHandler removeOperation;
    private RecordHandler actual;

    @BeforeClass
    public static void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        addOperation = new AddOperationStrategy(fruitDao);
        removeOperation = new RemoveOperationStrategy(fruitDao);
        Map<Operation, RecordHandler> fruitOperationHandler = new HashMap<>();
        fruitOperationHandler.put(Operation.getOperationByLetter("s"), addOperation);
        fruitOperationHandler.put(Operation.getOperationByLetter("r"), addOperation);
        fruitOperationHandler.put(Operation.getOperationByLetter("b"), addOperation);
        fruitOperationHandler.put(Operation.getOperationByLetter("p"), removeOperation);
        fruitRecordStrategy = new FruitRecordStrategyImpl(fruitOperationHandler);
    }

    @Test
    public void check_getOperation_OK() {
        actual = fruitRecordStrategy.get(Operation.BALANCE);
        Assert.assertEquals(addOperation, actual);
        actual = fruitRecordStrategy.get(Operation.RETURN);
        Assert.assertEquals(addOperation, actual);
        actual = fruitRecordStrategy.get(Operation.SUPPLY);
        Assert.assertEquals(addOperation, actual);

        actual = fruitRecordStrategy.get(Operation.PURCHASE);
        Assert.assertEquals(removeOperation, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_getOperationWithNull_Not_OK() {
        actual = fruitRecordStrategy.get(Operation.getOperationByLetter(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_getInvalidOperation_Not_OK() {
        actual = fruitRecordStrategy.get(Operation.getOperationByLetter("x"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_getOperationWithEmptyLine_Not_OK() {
        actual = fruitRecordStrategy.get(Operation.getOperationByLetter(" "));
    }
}
