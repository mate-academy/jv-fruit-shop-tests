package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.SupplyOperation;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private OperationHandler balance;
    private OperationHandler supply;
    private OperationHandler purchase;
    private OperationHandler ret;
    private Map<Operation, OperationHandler> map;
    private OperationStrategyImpl strategy;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        balance = new BalanceOperation();
        supply = new SupplyOperation();
        purchase = new PurchaseOperation();
        ret = new ReturnOperation();
        map = new HashMap<>();
        map.put(Operation.BALANCE, balance);
        map.put(Operation.SUPPLY, supply);
        map.put(Operation.PURCHASE, purchase);
        map.put(Operation.RETURN, ret);
        strategy = new OperationStrategyImpl(map);
    }

    @Test
    void transactions_ok() {
        ShopServiceImpl service = new ShopServiceImpl(strategy);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("banana", Operation.BALANCE, 100),
                new FruitTransaction("banana", Operation.SUPPLY, 20),
                new FruitTransaction("banana", Operation.PURCHASE, 50),
                new FruitTransaction("banana", Operation.RETURN, 10));
        service.process(transactions);
        assertEquals(80, Storage.getStorage().get("banana"));
    }
}
