package core.basesyntax.service;

import static core.basesyntax.service.TestConstants.DEFAULT_APPLE_TRANSACTION;
import static core.basesyntax.service.TestConstants.DEFAULT_BANANA_TRANSACTION;
import static core.basesyntax.service.TestConstants.DEFAULT_ORANGE_TRANSACTION;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static ShopServiceImpl shopServiceImpl;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeAll
    static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopServiceImpl = new ShopServiceImpl(operationStrategy);

        // Initialize fruitTransactions
        fruitTransactions = new ArrayList<>();
    }

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        fruitTransactions.clear();
    }

    @Test
    public void process_validInputBalance_Ok() {
        fruitTransactions.add(DEFAULT_APPLE_TRANSACTION);
        shopServiceImpl.process(fruitTransactions);
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            assert (compareTransctions(DEFAULT_APPLE_TRANSACTION, entry));
        }
    }

    @Test
    public void process_validInputSupply_Ok() {
        fruitTransactions.add(DEFAULT_BANANA_TRANSACTION);
        shopServiceImpl.process(fruitTransactions);
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            assertTrue(compareTransctions(DEFAULT_BANANA_TRANSACTION, entry));
        }
    }

    @Test
    public void process_validInputReturn_Ok() {
        fruitTransactions.add(DEFAULT_ORANGE_TRANSACTION);
        shopServiceImpl.process(fruitTransactions);
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            assert (compareTransctions(DEFAULT_ORANGE_TRANSACTION, entry));
        }
    }

    private boolean compareTransctions(FruitTransaction expected,
                                       Map.Entry<String, Integer> actual) {
        return expected.getFruit().equals(actual.getKey())
                && expected.getQuantity() == actual.getValue();
    }
}
