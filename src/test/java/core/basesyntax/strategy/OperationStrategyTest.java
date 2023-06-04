package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final Map<OperationType, OperationHandler> operationTypeMap =
            new HashMap<>();
    private static final OperationHandler balanceOperationHandler =
            new BalanceOperationHandlerImpl();
    private static final OperationHandler purchaseOperationHandler =
            new PurchaseOperationHandlerImpl();
    private static final OperationHandler supplyOperationHandler =
            new SupplyOperationHandlerImpl();
    private static final OperationHandler returnOperationHandler =
            new ReturnOperationHandlerImpl();

    @BeforeClass
    public static void beforeClass() {
        operationTypeMap.put(OperationType.BALANCE, balanceOperationHandler);
        operationTypeMap.put(OperationType.SUPPLY, supplyOperationHandler);
        operationTypeMap.put(OperationType.RETURN, returnOperationHandler);
        operationTypeMap.put(OperationType.PURCHASE, purchaseOperationHandler);
    }

    @Test
    public void add_nullToOperationStrategyImplConstructor_NotOk() {
        assertThrows(ValidationException.class, () ->
                new OperationStrategyImpl(null));
    }

    @Test
    public void add_emptyDataToOperationStrategyImplConstructor_NotOk() {
        assertThrows(ValidationException.class, () ->
                new OperationStrategyImpl(new HashMap<>()));
    }

    @Test
    public void get_balanceOperationHandler_Ok() {
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationTypeMap);
        OperationHandler actualBalanceOperationHandler =
                operationStrategy.getOperation(OperationType.BALANCE);
        Assert.assertEquals("Expected balanceOperationHandlerImpl but was: "
                        + actualBalanceOperationHandler.getClass().getSimpleName(),
                balanceOperationHandler, actualBalanceOperationHandler);
    }

    @Test
    public void get_purchaseOperationHandler_Ok() {
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationTypeMap);
        OperationHandler actualPurchaseOperationHandler =
                operationStrategy.getOperation(OperationType.PURCHASE);
        Assert.assertEquals("Expected purchaseOperationHandlerImpl but was: "
                        + actualPurchaseOperationHandler.getClass().getSimpleName(),
                purchaseOperationHandler, actualPurchaseOperationHandler);
    }

    @Test
    public void get_supplyOperationHandler_Ok() {
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationTypeMap);
        OperationHandler actualSupplyOperationHandler =
                operationStrategy.getOperation(OperationType.SUPPLY);
        Assert.assertEquals("Expected purchaseOperationHandlerImpl but was: "
                        + actualSupplyOperationHandler.getClass().getSimpleName(),
                supplyOperationHandler, actualSupplyOperationHandler);
    }

    @Test
    public void get_returnOperationHandler_Ok() {
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationTypeMap);
        OperationHandler actualReturnOperationHandler =
                operationStrategy.getOperation(OperationType.RETURN);
        Assert.assertEquals("Expected purchaseOperationHandlerImpl but was: "
                        + actualReturnOperationHandler.getClass().getSimpleName(),
                returnOperationHandler, actualReturnOperationHandler);
    }
}
