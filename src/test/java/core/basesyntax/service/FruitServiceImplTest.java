package core.basesyntax.service;

import core.basesyntax.model.Operation;
import core.basesyntax.service.base.FruitServiceTestBase;
import java.util.Map;

public class FruitServiceImplTest extends FruitServiceTestBase<FruitServiceImpl> {
    @Override
    protected FruitServiceImpl createInstance() {
        Map<Operation, OperationHandler> operations = Map.of(
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler()
        );
        return new FruitServiceImpl(operations);
    }
}