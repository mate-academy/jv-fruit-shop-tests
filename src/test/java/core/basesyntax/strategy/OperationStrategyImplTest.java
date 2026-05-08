package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(Map.of(
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler()
    ));

    @Test
    void getHadler_balanceOperator_ok() {
        OperationHandler actual = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE
        );

        assertInstanceOf(BalanceOperationHandler.class, actual);
    }

    @Test
    void getHandler_returnOperator_ok() {
        OperationHandler actual = operationStrategy.getHandler(
                FruitTransaction.Operation.RETURN
        );

        assertInstanceOf(ReturnOperationHandler.class, actual);
    }

    @Test
    void getHandler_supplyOperator_ok() {
        OperationHandler actual = operationStrategy.getHandler(
                FruitTransaction.Operation.SUPPLY
        );

        assertInstanceOf(SupplyOperationHandler.class, actual);
    }

    @Test
    void getHandler_purchaseOperator_ok() {
        OperationHandler actual = operationStrategy.getHandler(
                FruitTransaction.Operation.PURCHASE
        );

        assertInstanceOf(PurchaseOperationHandler.class, actual);
    }
}
