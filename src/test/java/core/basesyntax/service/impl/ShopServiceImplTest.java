package core.basesyntax.service.impl;

import static core.basesyntax.service.impl.TestConstants.DEFAULT_APPLE_TRANSACTION;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.FruitStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final ShopServiceImpl shopServiceImpl;
    private static List<FruitTransaction> fruitTransactions;

    static {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        FruitStrategy operationStrategy = new FruitStrategyImpl(operationHandlers);
        shopServiceImpl = new ShopServiceImpl(operationStrategy);
    }

    @BeforeAll
    static void beforeAll() {
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(DEFAULT_APPLE_TRANSACTION);
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void read_validInput_ok() {
        shopServiceImpl.process(fruitTransactions);
        for (Map.Entry<String, Integer> entry : Storage.storage.entrySet()) {
            assert (compareTransaction(DEFAULT_APPLE_TRANSACTION, entry));
        }
    }

    private boolean compareTransaction(FruitTransaction expected,
                                       Map.Entry<String, Integer> actual) {
        return expected.getFruitName().equals(actual.getKey())
                && expected.getQuantity() == actual.getValue();
    }
}
