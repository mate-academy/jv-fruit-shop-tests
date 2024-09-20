package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationBalance;
    private static OperationStrategy operationPurchase;
    private static OperationStrategy operationSupply;
    private static OperationStrategy operationReturn;
    private static FruitTransaction balTransaction;
    private static FruitTransaction purTransaction;
    private static FruitTransaction supTransaction;
    private static FruitTransaction retTransaction;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeAll
    static void beforeAll() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationBalance = new OperationStrategyImpl(operationHandlers);
        operationPurchase = new OperationStrategyImpl(operationHandlers);
        operationSupply = new OperationStrategyImpl(operationHandlers);
        operationReturn = new OperationStrategyImpl(operationHandlers);
        balTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        purTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        supTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);
        retTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20);
    }

    @Test
    void getHandler_Balance_Ok() {
        OperationHandler balanceOp = new BalanceOperation();

        assertEquals(balanceOp, operationBalance.getHandler(balTransaction.getOperation()));
    }

    @Test
    void getHandler_Purchase_Ok() {
        OperationHandler purchaseOp = new PurchaseOperation();

        assertEquals(purchaseOp, operationPurchase.getHandler(purTransaction.getOperation()));
    }

    @Test
    void getHandler_Supply_Ok() {
        OperationHandler supplyOp = new SupplyOperation();

        assertEquals(supplyOp, operationSupply.getHandler(supTransaction.getOperation()));
    }

    @Test
    void getHandler_Return_Ok() {
        OperationHandler returnOp = new ReturnOperation();

        assertEquals(returnOp, operationReturn.getHandler(retTransaction.getOperation()));
    }
}
