package core.basesyntax.service.handler.impl;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.model.enums.Operation;
import core.basesyntax.service.handler.OperationHandler;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest extends BaseOperationHandlerTest {
    @Test
    void getOperationHandler_balance_ok() {
        OperationHandler handler = operationStrategy.getOperationHandler(Operation.BALANCE);
        assertInstanceOf(BalanceOperationHandler.class, handler);
    }

    @Test
    void getOperationHandler_supply_ok() {
        OperationHandler handler = operationStrategy.getOperationHandler(Operation.SUPPLY);
        assertInstanceOf(SupplyOperationHandler.class, handler);
    }

    @Test
    void getOperationHandler_purchase_ok() {
        OperationHandler handler = operationStrategy.getOperationHandler(Operation.PURCHASE);
        assertInstanceOf(PurchaseOperationHandler.class, handler);
    }

    @Test
    void getOperationHandler_return_ok() {
        OperationHandler handler = operationStrategy.getOperationHandler(Operation.RETURN);
        assertInstanceOf(ReturnOperationHandler.class, handler);
    }
}
