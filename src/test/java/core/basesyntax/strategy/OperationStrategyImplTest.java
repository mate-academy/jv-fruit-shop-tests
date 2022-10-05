package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.service.BalanceOperationHandler;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.PurchaseOperationHandler;
import core.basesyntax.service.ReturnOperationHandler;
import core.basesyntax.service.SupplyOperationHandler;
import core.basesyntax.strategy.base.OperationStrategyTestBase;
import java.util.Map;

public class OperationStrategyImplTest extends OperationStrategyTestBase<OperationStrategyImpl> {
    @Override
    protected OperationStrategyImpl createInstance() {
        Map<Operation, OperationHandler> operations = Map.of(
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler()
        );
        return new OperationStrategyImpl(operations);
    }
}
