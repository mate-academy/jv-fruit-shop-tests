package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    @Test
    void shouldProcessBalanceOperation() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        FruitTransaction tx = new FruitTransaction();
        tx.setOperation(FruitTransaction.Operation.BALANCE);
        tx.setFruit("apple");
        tx.setQuantity(10);

        ShopServiceImpl service =
                new ShopServiceImpl(new OperationStrategyImpl(handlers));

        service.process(List.of(tx));

        assertEquals(10, service.getStorage().get("apple"));
    }

    @Test
    void shouldProcessAllOperations() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        FruitTransaction t1 = new FruitTransaction();
        t1.setOperation(FruitTransaction.Operation.BALANCE);
        t1.setFruit("apple");
        t1.setQuantity(20);

        FruitTransaction t2 = new FruitTransaction();
        t2.setOperation(FruitTransaction.Operation.SUPPLY);
        t2.setFruit("apple");
        t2.setQuantity(5);

        FruitTransaction t3 = new FruitTransaction();
        t3.setOperation(FruitTransaction.Operation.PURCHASE);
        t3.setFruit("apple");
        t3.setQuantity(3);

        FruitTransaction t4 = new FruitTransaction();
        t4.setOperation(FruitTransaction.Operation.RETURN);
        t4.setFruit("apple");
        t4.setQuantity(2);

        ShopServiceImpl service =
                new ShopServiceImpl(new OperationStrategyImpl(handlers));

        service.process(List.of(t1, t2, t3, t4));

        assertEquals(24, service.getStorage().get("apple"));
    }

    @Test
    void shouldThrowException_whenStrategyNull() {
        assertThrows(RuntimeException.class,
                () -> new ShopServiceImpl(null));
    }

    @Test
    void shouldThrowException_whenHandlerMapEmpty() {
        assertThrows(RuntimeException.class,
                () -> new OperationStrategyImpl(new HashMap<>()));
    }

    @Test
    void shouldSkipNullTransaction() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        ShopServiceImpl service =
                new ShopServiceImpl(new OperationStrategyImpl(handlers));

        List<FruitTransaction> input = new ArrayList<>();
        input.add(null);

        service.process(input);

        assertTrue(service.getStorage().isEmpty());
    }
}
