package strategy;

import dao.FruitDaoImpl;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.operation.OperationHandler;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(new FruitDaoImpl()),
                FruitTransaction.Operation.RETURN,
                new ReturnOperation(new FruitDaoImpl()));
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_purchaseOperation_isOk() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertInstanceOf(PurchaseOperation.class, actual);
    }

    @Test
    void get_returnOperation_isOk() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assertions.assertInstanceOf(ReturnOperation.class, actual);
    }
}
