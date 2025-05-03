package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlersMapInput;
    private static OperationHandler balanceOperation;
    private static OperationHandler purchaseOperation;
    private static OperationHandler returnOperation;
    private static OperationHandler supplyOperation;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationHandlersMapInput = new HashMap<>();
        balanceOperation = new BalanceOperation();
        purchaseOperation = new PurchaseOperation();
        returnOperation = new ReturnOperation();
        supplyOperation = new SupplyOperation();

        operationHandlersMapInput.put(FruitTransaction.Operation.BALANCE, balanceOperation);
        operationHandlersMapInput.put(FruitTransaction.Operation.PURCHASE, purchaseOperation);
        operationHandlersMapInput.put(FruitTransaction.Operation.RETURN, returnOperation);
        operationHandlersMapInput.put(FruitTransaction.Operation.SUPPLY, supplyOperation);
        operationStrategy = new OperationStrategyImpl(operationHandlersMapInput);
    }

    @Test
    void get_validDataInput_ok() {
        OperationHandler expected = balanceOperation;
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected, actual);

        expected = purchaseOperation;
        actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expected, actual);

        expected = returnOperation;
        actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(expected, actual);

        expected = supplyOperation;
        actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected, actual);
    }

    @Test
    void get_nullInput_ok() {
        OperationHandler actual = operationStrategy.get(null);
        assertNull(actual);
    }
}

