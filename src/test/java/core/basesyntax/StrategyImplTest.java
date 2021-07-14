package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Record;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyImplTest {
    private static final String FRUIT = "orange";
    private static final int QUANTITY = 5;
    private static final int DOUBLE_QUANTITY = 10;
    private static final OperationHandler balanceHandler = new BalanceOperationHandler();
    private static final OperationHandler purchaseHandler = new PurchaseOperationHandler();
    private static final OperationHandler returnHandler = new ReturnOperationHandler();
    private static final OperationHandler supplyHandler = new SupplyOperationHandler();
    private static final Map<Record.OperationType, OperationHandler>
            operationHandlersMap = new HashMap<>();
    private static final Record.OperationType balanceOperation = Record.OperationType.BALANCE;
    private static final Record.OperationType purchaseOperation = Record.OperationType.PURCHASE;
    private static final Record.OperationType returnOperation = Record.OperationType.RETURN;
    private static final Record.OperationType supplyOperation = Record.OperationType.SUPPLY;

    @BeforeClass
    public static void setup() {
        operationHandlersMap.put(balanceOperation, new BalanceOperationHandler());
        operationHandlersMap.put(purchaseOperation, new PurchaseOperationHandler());
        operationHandlersMap.put(returnOperation, new ReturnOperationHandler());
        operationHandlersMap.put(supplyOperation, new SupplyOperationHandler());
    }

    @Test
    public void operationStrategyImpl_get_goodValue_ok() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        assertEquals(operationStrategy.get(balanceOperation).getClass(),
                BalanceOperationHandler.class);
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        assertEquals(operationStrategy.get(purchaseOperation).getClass(),
                PurchaseOperationHandler.class);
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        assertEquals(operationStrategy.get(returnOperation).getClass(),
                ReturnOperationHandler.class);
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        assertEquals(operationStrategy.get(supplyOperation).getClass(),
                SupplyOperationHandler.class);
    }

    @Test
    public void balanceOperationHandler_updateValue_goodValue_ok() {
        Record record = new Record(Record.OperationType.BALANCE, FRUIT, QUANTITY);
        assertEquals(balanceHandler.updateValue(record, 0).intValue(), QUANTITY);
    }

    @Test (expected = IllegalArgumentException.class)
    public void balanceOperationHandler_updateValue_notFirstOperation_notOk() {
        Record record = new Record(Record.OperationType.BALANCE, FRUIT, QUANTITY);
        assertEquals(balanceHandler.updateValue(record, QUANTITY).intValue(), QUANTITY);
    }

    @Test
    public void purchaseOperationHandler_updateValue_goodValue_ok() {
        Record record = new Record(Record.OperationType.PURCHASE, FRUIT, QUANTITY);
        assertEquals(purchaseHandler.updateValue(record, DOUBLE_QUANTITY).intValue(), QUANTITY);
    }

    @Test (expected = IllegalArgumentException.class)
    public void purchaseOperationHandler_updateValue_outOfStock_notOk() {
        Record record = new Record(Record.OperationType.PURCHASE, FRUIT, DOUBLE_QUANTITY);
        assertEquals(purchaseHandler.updateValue(record, QUANTITY).intValue(), QUANTITY);
    }

    @Test
    public void supplyOperationHandler_updateValue_goodValue_ok() {
        Record record = new Record(Record.OperationType.PURCHASE, FRUIT, QUANTITY);
        assertEquals(supplyHandler.updateValue(record, QUANTITY).intValue(), DOUBLE_QUANTITY);
    }

    @Test
    public void returnOperationHandler_updateValue_goodValue_ok() {
        Record record = new Record(Record.OperationType.PURCHASE, FRUIT, QUANTITY);
        assertEquals(returnHandler.updateValue(record, QUANTITY).intValue(), DOUBLE_QUANTITY);
    }

}
