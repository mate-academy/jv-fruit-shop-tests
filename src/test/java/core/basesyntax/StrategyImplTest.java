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
import org.junit.Test;

public class StrategyImplTest {
    private static final String FRUIT = "orange";
    private static final int QUANTITY = 5;
    private static final int DOUBLE_QUANTITY = 10;
    private static final OperationHandler BALANCE_HANDLER = new BalanceOperationHandler();
    private static final OperationHandler PURCHASE_HANDLER = new PurchaseOperationHandler();
    private static final OperationHandler RETURN_HANDLER = new ReturnOperationHandler();
    private static final OperationHandler SUPPLY_HANDLER = new SupplyOperationHandler();
    private static final Map<Record.OperationType, OperationHandler>
            OPERATION_HANDLER_MAP = new HashMap<>();
    private static final Record.OperationType BALANCE_OPERATION = Record.OperationType.BALANCE;
    private static final Record.OperationType PURCHASE_OPERATION = Record.OperationType.PURCHASE;
    private static final Record.OperationType RETURN_OPERATION = Record.OperationType.RETURN;
    private static final Record.OperationType SUPPLY_OPERATION = Record.OperationType.SUPPLY;

    @Test
    public void operationStrategyImpl_get_goodValue_ok() {
        OPERATION_HANDLER_MAP.put(BALANCE_OPERATION, new BalanceOperationHandler());
        OPERATION_HANDLER_MAP.put(PURCHASE_OPERATION, new PurchaseOperationHandler());
        OPERATION_HANDLER_MAP.put(RETURN_OPERATION, new ReturnOperationHandler());
        OPERATION_HANDLER_MAP.put(SUPPLY_OPERATION, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
        assertEquals(operationStrategy.get(BALANCE_OPERATION).getClass(),
                BalanceOperationHandler.class);
        operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
        assertEquals(operationStrategy.get(PURCHASE_OPERATION).getClass(),
                PurchaseOperationHandler.class);
        operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
        assertEquals(operationStrategy.get(RETURN_OPERATION).getClass(),
                ReturnOperationHandler.class);
        operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
        assertEquals(operationStrategy.get(SUPPLY_OPERATION).getClass(),
                SupplyOperationHandler.class);
    }

    @Test
    public void balanceOperationHandler_updateValue_goodValue_ok() {
        Record record = new Record(Record.OperationType.BALANCE, FRUIT, QUANTITY);
        assertEquals(BALANCE_HANDLER.updateValue(record, 0).intValue(), QUANTITY);
    }

    @Test (expected = IllegalArgumentException.class)
    public void balanceOperationHandler_updateValue_notFirstOperation_notOk() {
        Record record = new Record(Record.OperationType.BALANCE, FRUIT, QUANTITY);
        assertEquals(BALANCE_HANDLER.updateValue(record, QUANTITY).intValue(), QUANTITY);
    }

    @Test
    public void purchaseOperationHandler_updateValue_goodValue_ok() {
        Record record = new Record(Record.OperationType.PURCHASE, FRUIT, QUANTITY);
        assertEquals(PURCHASE_HANDLER.updateValue(record, DOUBLE_QUANTITY).intValue(), QUANTITY);
    }

    @Test (expected = IllegalArgumentException.class)
    public void purchaseOperationHandler_updateValue_outOfStock_notOk() {
        Record record = new Record(Record.OperationType.PURCHASE, FRUIT, DOUBLE_QUANTITY);
        assertEquals(PURCHASE_HANDLER.updateValue(record, QUANTITY).intValue(), QUANTITY);
    }

    @Test
    public void supplyOperationHandler_updateValue_goodValue_ok() {
        Record record = new Record(Record.OperationType.PURCHASE, FRUIT, QUANTITY);
        assertEquals(SUPPLY_HANDLER.updateValue(record, QUANTITY).intValue(), DOUBLE_QUANTITY);
    }

    @Test
    public void returnOperationHandler_updateValue_goodValue_ok() {
        Record record = new Record(Record.OperationType.PURCHASE, FRUIT, QUANTITY);
        assertEquals(RETURN_HANDLER.updateValue(record, QUANTITY).intValue(), DOUBLE_QUANTITY);
    }

}
