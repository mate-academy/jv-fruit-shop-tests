package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import java.util.List;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private final OperationHandler balance = new BalanceOperationHandler();
    private final OperationHandler supply = new SupplyOperationHandler();
    private final OperationHandler purchase = new PurchaseOperationHandler();
    private final OperationHandler returning = new ReturningOperationHandler();
    private final FruitTransaction balanceLemon = new FruitTransaction(
            Operation.BALANCE, "lemon", 2);
    private final FruitTransaction supplyLemon = new FruitTransaction(
            Operation.SUPPLY, "lemon", 2);
    private final FruitTransaction purchaseLemon = new FruitTransaction(
            Operation.PURCHASE, "lemon", 2);
    private final FruitTransaction returnLemon = new FruitTransaction(
            Operation.RETURN, "lemon", 2);
    private final List<OperationHandler> goodOperationList = List.of(
            balance, supply, purchase, returning);
    private final OperationStrategy actualStrategy = new OperationStrategyImpl(goodOperationList);
    private List<OperationHandler> actual;

    @Test
    void operationStrategy_InputOk_BalanceTrue() {
        actual = actualStrategy.getHandlers(balanceLemon);
        assertEquals(1, actual.size());
        assertEquals("BalanceOperationHandler", actual.get(0).getClass().getSimpleName());
    }

    @Test
    void operationStrategy_InputOk_SupplyTrue() {
        actual = actualStrategy.getHandlers(supplyLemon);
        assertEquals(1, actual.size());
        assertEquals("SupplyOperationHandler", actual.get(0).getClass().getSimpleName());
    }

    @Test
    void operationStrategy_InputOk_PurchaseTrue() {
        actual = actualStrategy.getHandlers(purchaseLemon);
        assertEquals(1, actual.size());
        assertEquals("PurchaseOperationHandler", actual.get(0).getClass().getSimpleName());
    }

    @Test
    void operationStrategy_InputOk_ReturnTrue() {
        actual = actualStrategy.getHandlers(returnLemon);
        assertEquals(1, actual.size());
        assertEquals("ReturningOperationHandler", actual.get(0).getClass().getSimpleName());
    }
}
