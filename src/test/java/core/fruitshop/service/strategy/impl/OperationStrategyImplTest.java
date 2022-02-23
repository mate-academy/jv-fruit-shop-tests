package core.fruitshop.service.strategy.impl;

import static org.junit.Assert.*;

import core.fruitshop.dao.FruitDao;
import core.fruitshop.dao.FruitDaoImpl;
import core.fruitshop.model.FruitTransaction;
import core.fruitshop.model.FruitTransaction.Operation;
import core.fruitshop.service.FruitShopService;
import core.fruitshop.service.impl.DataHandlerImpl;
import core.fruitshop.service.impl.FileReaderImpl;
import core.fruitshop.service.impl.FileWriterImpl;
import core.fruitshop.service.impl.FruitShopServiceImpl;
import core.fruitshop.service.impl.ReportCreatorImpl;
import core.fruitshop.service.strategy.OperationHandler;
import core.fruitshop.service.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final Map<Operation, OperationHandler> operationHandlerMap
        = new HashMap<>();
    private static final OperationStrategy operationStrategy
        = new OperationStrategyImpl(operationHandlerMap);

    @BeforeClass
    public static void beforeClass() {
        FruitDao dao = new FruitDaoImpl();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler(dao));
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler(dao));
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler(dao));
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler(dao));
        FruitShopService fruitShopService = new FruitShopServiceImpl(new FileReaderImpl(),
            new DataHandlerImpl(new OperationStrategyImpl(operationHandlerMap)), new ReportCreatorImpl(),
            new FileWriterImpl());
    }

    @Test
    public void getBalanceOperationHandler_ok() {
        Class<?> actualClass = operationStrategy
            .getOperationHandler(FruitTransaction.Operation.BALANCE).getClass();
        Class<?> expectedClass = BalanceOperationHandler.class;
        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getPurchaseOperationHandler_ok() {
        Class<?> actualClass = operationStrategy
            .getOperationHandler(FruitTransaction.Operation.PURCHASE).getClass();
        Class<?> expectedClass = PurchaseOperationHandler.class;
        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getReturnOperationHandler_ok() {
        Class<?> actualClass = operationStrategy
            .getOperationHandler(FruitTransaction.Operation.RETURN).getClass();
        Class<?> expectedClass = ReturnOperationHandler.class;
        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getSupplyOperationHandler_ok() {
        Class<?> actualClass = operationStrategy
            .getOperationHandler(FruitTransaction.Operation.SUPPLY).getClass();
        Class<?> expectedClass = SupplyOperationHandler.class;
        assertEquals(expectedClass, actualClass);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationHandlerOfNullOperation_notOk() {
        operationStrategy
            .getOperationHandler(null);
    }
}