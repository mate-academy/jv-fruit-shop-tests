package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceTest {

    @Test
    void shouldProcessAllOperationsCorrectly() {

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        FruitTransaction t1 = new FruitTransaction();
        t1.setOperation(FruitTransaction.Operation.BALANCE);
        t1.setFruit("banana");
        t1.setQuantity(100);

        FruitTransaction t2 = new FruitTransaction();
        t2.setOperation(FruitTransaction.Operation.PURCHASE);
        t2.setFruit("banana");
        t2.setQuantity(30);

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        ShopServiceImpl service = new ShopServiceImpl(strategy);
        service.process(List.of(t1, t2));

        assertEquals(70, service.getStorage().get("banana"));
    }
}
