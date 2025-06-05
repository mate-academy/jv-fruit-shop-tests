package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.impl.BalanceOperation;
import core.basesyntax.operation.impl.OperationStrategyImpl;
import core.basesyntax.operation.impl.PurchaseOperation;
import core.basesyntax.operation.impl.ReturnOperation;
import core.basesyntax.operation.impl.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                        FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                        FruitTransaction.Operation.RETURN, new ReturnOperation()));
    }

    @Test
    void process() {
        ShopServiceImpl service = new ShopServiceImpl(strategy);
        service.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 5)));
        assertEquals(5, FruitStorage.getStorage().get("apple"));
    }
}
