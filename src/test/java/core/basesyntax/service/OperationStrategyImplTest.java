package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.type.service.BalanceHandler;
import core.basesyntax.service.type.service.OperationHandler;
import core.basesyntax.service.type.service.PurchaseHandler;
import core.basesyntax.service.type.service.ReturnHandler;
import core.basesyntax.service.type.service.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitRecordDto.Activities, OperationHandler> mapTypeHandler;
    private static final OperationHandler BALANCE = new BalanceHandler();
    private static final OperationHandler PURCHASE = new PurchaseHandler();
    private static final OperationHandler RETURN = new ReturnHandler();
    private static final OperationHandler SUPPLY = new SupplyHandler();
    private Class<? extends OperationHandler> expected;
    private Class<? extends OperationHandler> actual;

    @Before
    public void setUp() throws Exception {
        mapTypeHandler = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(mapTypeHandler);
    }

    @Test
    public void getHandler_getBalance_Ok() {
        mapTypeHandler.put(FruitRecordDto.Activities.BALANCE, new BalanceHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.PURCHASE, new PurchaseHandler());
        expected = BALANCE.getClass();
        actual = operationStrategy.getHandler(FruitRecordDto.Activities.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getHandler_getPurchase_Ok() {
        mapTypeHandler.put(FruitRecordDto.Activities.BALANCE, new BalanceHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.PURCHASE, new PurchaseHandler());
        expected = PURCHASE.getClass();
        actual = operationStrategy.getHandler(FruitRecordDto.Activities.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getHandler_getReturn_Ok() {
        mapTypeHandler.put(FruitRecordDto.Activities.RETURN, new ReturnHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.PURCHASE, new PurchaseHandler());
        expected = RETURN.getClass();
        actual = operationStrategy.getHandler(FruitRecordDto.Activities.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getHandler_getSupply_Ok() {
        mapTypeHandler.put(FruitRecordDto.Activities.SUPPLY, new SupplyHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.PURCHASE, new PurchaseHandler());
        expected = SUPPLY.getClass();
        actual = operationStrategy.getHandler(FruitRecordDto.Activities.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getHandler_getIncorrectHandler_Ok() {
        mapTypeHandler.put(FruitRecordDto.Activities.SUPPLY, new SupplyHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.PURCHASE, new PurchaseHandler());
        expected = RETURN.getClass();
        try {
            actual = operationStrategy.getHandler(FruitRecordDto.Activities.RETURN).getClass();
        } catch (NullPointerException e) {
            return;
        }
        fail("Should throw NullPointerException");
    }
}
