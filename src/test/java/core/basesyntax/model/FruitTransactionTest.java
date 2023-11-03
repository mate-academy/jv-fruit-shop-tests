package core.basesyntax.model;

import core.basesyntax.service.OperationHandler;
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
    void operationGetByOperationIs_Ok() {
        FruitTransaction.Operation b = FruitTransaction.Operation.getByCode("b");
        Assertions.assertEquals(b, (FruitTransaction.Operation.BALANCE));
        FruitTransaction.Operation r = FruitTransaction.Operation.getByCode("r");
        Assertions.assertEquals(r, (FruitTransaction.Operation.RETURN));
    }

    @Test
    void operationGetByOperationIs_Not_Ok() {
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
