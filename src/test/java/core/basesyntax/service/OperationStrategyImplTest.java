package core.basesyntax.service;

import java.util.Map;
import org.junit.jupiter.api.Test;
import core.basesyntax.model.FruitTransaction;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationStrategyImplTest {

    @Test
    void getHandler_validOperation_ok() {
        OperationHandler balanceHandler = new BalanceOperation();
        // Ініціалізація OperationStrategyImpl з мапою,
        // де операція BALANCE пов'язана з баланс-обробником
        OperationStrategy operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, balanceHandler));
        //Викликається метод getHandler для операції BALANCE,
        // результат зберігається у змінній result
        OperationHandler result = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE);
        //Перевіряється, що отриманий обробник
        // відповідає очікуваному (handlerMock)
        assertEquals(balanceHandler, result);
    }

    @Test
    void getHandler_invalidOperation_throwsException() {
        //Ініціалізується OperationStrategyImpl без жодних зареєстрованих обробників
        OperationStrategy operationStrategy = new OperationStrategyImpl(Map.of());
        //Перевіряється, що виклик getHandler для операції
        // PURCHASE призводить до винятку RuntimeException
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE),
                "Expected getHandler to throw RuntimeException for an invalid operation"
        );
    }
}
