package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    @Test
    void shouldProcessValidTransaction() {
        FruitTransaction valid = new FruitTransaction();
        valid.setOperation(FruitTransaction.Operation.BALANCE);
        valid.setFruit("apple");
        valid.setQuantity(10);

        Map<FruitTransaction.Operation,
                OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        ShopServiceImpl service =
                new ShopServiceImpl(new OperationStrategyImpl(handlers));
        service.process(List.of(valid));

        assertEquals(10, service.getStorage().get("apple"));
    }

    @Test
    void shouldThrowException_whenStrategyNull() {
        assertThrows(RuntimeException.class,
                () -> new ShopServiceImpl(null));
    }

    @Test
    void shouldThrowException_whenTransactionsNull() {
        Map<FruitTransaction.Operation,
                OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        ShopServiceImpl service =
                new ShopServiceImpl(new OperationStrategyImpl(handlers));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.process(null));

        assertEquals("Transactions cannot be null", ex.getMessage());
    }
}
