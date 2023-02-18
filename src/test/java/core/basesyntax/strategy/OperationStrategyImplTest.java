package core.basesyntax.strategy;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.dao.WarehouseDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.operation.BalanceOperationHandler;
import core.basesyntax.service.impl.operation.PurchaseOperationHandler;
import core.basesyntax.service.impl.operation.ReturnOperationHandler;
import core.basesyntax.service.impl.operation.SupplyOperationHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class OperationStrategyImplTest {
    private WarehouseDao warehouseDao;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap;

    @Before
    public void setUp() {
        warehouseDao = new WarehouseDaoImpl();
        operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(warehouseDao));
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
    }

    @Test
    public void getHandlerBalance_ok(){
        Class<? extends OperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getHandlerSupply_ok(){
        Class<? extends OperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getHandlerPurchase_ok(){
        Class<? extends OperationHandler> expected = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getHandlerReturn_ok(){
        Class<? extends OperationHandler> expected = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy.getHandler(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getNullHandler_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.getHandler(null);
        });
    }
}