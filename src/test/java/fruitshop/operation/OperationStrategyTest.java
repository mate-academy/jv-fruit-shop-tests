package fruitshop.operation;

import fruitshop.model.Operation;
import fruitshop.operation.impl.PurchaseOperationHandlerImpl;
import fruitshop.suppliers.OperationStrategySupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeAll
    void setUp() {
        operationStrategy = new OperationStrategySupplier().get();
    }

    @Test
    void operationStrategySuppliesRightImpl_Ok() {
        Operation purchaseOperation = Operation.PURCHASE;
        Class<PurchaseOperationHandlerImpl> expectedOperation = PurchaseOperationHandlerImpl.class;
        Class<? extends OperationHandler> actualOperation = operationStrategy.get(purchaseOperation)
                .getClass();
        String message = "Operation strategy should have returned "
                + expectedOperation.getName()
                + " but returned "
                + actualOperation.getName();

        Assertions.assertTrue(expectedOperation.isAssignableFrom(operationStrategy
                        .get(purchaseOperation)
                        .getClass()),
                message);
    }
}
