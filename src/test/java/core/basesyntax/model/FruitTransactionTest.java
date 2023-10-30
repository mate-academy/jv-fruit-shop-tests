package core.basesyntax.model;

import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.CalculateStrategy;
import core.basesyntax.strategy.OperationHandlerBalance;
import core.basesyntax.strategy.OperationHandlerIn;
import core.basesyntax.strategy.OperationHandlerOut;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    private Map<FruitTransaction.Operation, OperationHandler>
            correspondenceTable = Map.of(
            FruitTransaction.Operation.BALANCE, new OperationHandlerBalance(),
            FruitTransaction.Operation.SUPPLY, new OperationHandlerIn(),
            FruitTransaction.Operation.RETURN, new OperationHandlerIn(),
            FruitTransaction.Operation.PURCHASE, new OperationHandlerOut());

    @Test
    void getOperationByOperationCodeIs_Ok() {
        CalculateStrategy calculateStrategy = new CalculateStrategy(correspondenceTable);
        OperationHandler handlerBalance = calculateStrategy.getHandler(FruitTransaction
                .Operation.BALANCE);
        Assertions.assertEquals(handlerBalance.getClass(), new OperationHandlerBalance()
                .getClass());
        OperationHandler handlerReturn = calculateStrategy.getHandler(FruitTransaction
                .Operation.RETURN);
        Assertions.assertEquals(handlerReturn.getClass(), new OperationHandlerIn()
                .getClass());
        OperationHandler handlerPurchase = calculateStrategy.getHandler(FruitTransaction
                .Operation.PURCHASE);
        Assertions.assertEquals(handlerPurchase.getClass(), new OperationHandlerOut()
                .getClass());
    }

    @Test
    void operationGetByOperationIS_Ok() {
        FruitTransaction.Operation b = FruitTransaction.Operation.getByCode("b");
        Assertions.assertTrue(b.equals(FruitTransaction.Operation.BALANCE));
        FruitTransaction.Operation r = FruitTransaction.Operation.getByCode("r");
        Assertions.assertTrue(r.equals(FruitTransaction.Operation.RETURN));
        Assertions.assertThrows(RuntimeException.class, () -> FruitTransaction
                .Operation.getByCode("x"));
    }

    @Test
    void enumTestIs_Ok() {
        Assertions.assertEquals(4,FruitTransaction.Operation.values().length);
    }

    @Test
    void fieldsOfFruitTransactionIs_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals("banana", fruitTransaction.getFruit());
        Assertions.assertEquals(10, fruitTransaction.getQuantity());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction
                .getOperation());
    }
}
