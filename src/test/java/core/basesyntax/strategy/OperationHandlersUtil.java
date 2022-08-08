package core.basesyntax.strategy;

import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;

public class OperationHandlersUtil {
    public static final BalanceOperationHandler BALANCE_OPERATION_HANDLER =
            new BalanceOperationHandler();
    public static final PurchaseOperationHandler PURCHASE_OPERATION_HANDLER =
            new PurchaseOperationHandler();
    public static final ReturnOperationHandler RETURN_OPERATION_HANDLER =
            new ReturnOperationHandler();
    public static final SupplyOperationHandler SUPPLY_OPERATION_HANDLER =
            new SupplyOperationHandler();
}
