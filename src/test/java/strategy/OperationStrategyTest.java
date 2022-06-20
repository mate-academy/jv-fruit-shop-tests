package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import dao.FruitDao;
import dao.FruitDaoImpl;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.OperationHandler;
import service.OperationStrategy;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        FruitDao fruitDao = new FruitDaoImpl(new HashMap<>());
        Map<FruitTransaction.Operation, OperationHandler> mapOperation = new HashMap<>();
        mapOperation.put(FruitTransaction.Operation.BALANCE,
                new SetBalanceOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.PURCHASE,
                new SubtractOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.RETURN,
                new AddOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.SUPPLY,
                new AddOperationHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(mapOperation);
    }

    @Test
    public void getOperationHandler_obtainCorrespondingOperation_ok() {
        final String message = "method getOperationHandler returned incorrect handler";
        final OperationHandler operationForSupply = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY);
        final OperationHandler operationForReturn = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN);
        final OperationHandler operationForPurchase = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE);
        final OperationHandler operationForSetBalance = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(message, operationForSupply.getClass(), AddOperationHandler.class);
        assertEquals(message, operationForReturn.getClass(), AddOperationHandler.class);
        assertEquals(message, operationForPurchase.getClass(), SubtractOperationHandler.class);
        assertEquals(message, operationForSetBalance.getClass(), SetBalanceOperationHandler.class);
    }

    @Test
    public void getOperationHandler_getSomeHandlerIfOperationNull_notOk() {
        final String message = "method getOperationHandler shouldn't return any operationHandler"
                + " if input data is NULL. Result of method should be NULL also\n";
        assertNull(message, operationStrategy.getOperationHandler(null));
    }
}
