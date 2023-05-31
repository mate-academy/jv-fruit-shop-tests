package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static FruitDao fruitDao;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperationHandler_Ok() {
        Class<BalanceOperationHandler> expectedBalanceOperationHandler
                = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actualBalanceOperationHandler
                = operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expectedBalanceOperationHandler, actualBalanceOperationHandler);
    }

    @Test
    public void get_purchaseOperationHandler_Ok() {
        Class<PurchaseOperationHandler> expectedPurchaseOperationHandler
                = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actualPurchaseOperationHandler
                = operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expectedPurchaseOperationHandler, actualPurchaseOperationHandler);
    }

    @Test
    public void get_supplyOperationHandler_Ok() {
        Class<SupplyOperationHandler> expectedSupplyOperationHandler = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actualSupplyOperationHandler = operationStrategy
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expectedSupplyOperationHandler, actualSupplyOperationHandler);
    }

    @Test
    public void get_returnOperationHandler_Ok() {
        Class<ReturnOperationHandler> expectedReturnOperationHandler = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actualReturnOperationHandler = operationStrategy
                .get(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expectedReturnOperationHandler, actualReturnOperationHandler);
    }
}
