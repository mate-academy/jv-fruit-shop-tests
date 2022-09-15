package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandlerBalance = new BalanceHandler(fruitDao);
        OperationHandler operationHandlerPurchase = new PurchaseHandler(fruitDao);
        OperationHandler operationHandlerReturn = new ReturnHandler(fruitDao);
        OperationHandler operationHandlerSupply = new SupplyHandler(fruitDao);

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, operationHandlerBalance);
        handlerMap.put(FruitTransaction.Operation.PURCHASE, operationHandlerPurchase);
        handlerMap.put(FruitTransaction.Operation.RETURN, operationHandlerReturn);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, operationHandlerSupply);

        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void getHandle_Ok() {
        Assert.assertEquals(operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass(),
                BalanceHandler.class);
    }
}
