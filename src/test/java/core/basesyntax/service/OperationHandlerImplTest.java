package core.basesyntax.service;

import core.basesyntax.model.Operation;
import core.basesyntax.service.base.OperationHandlerTestBase;
import java.util.Map;

public class OperationHandlerImplTest extends OperationHandlerTestBase {
    @Override
    protected Map<Operation, ? extends OperationHandler> getInstances() {
        return Map.of(
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler()
        );
    }
}
