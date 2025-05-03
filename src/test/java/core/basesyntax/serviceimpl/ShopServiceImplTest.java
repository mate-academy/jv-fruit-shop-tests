package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.ShopInventory;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String TEST_FRUIT = "banana";
    private static final int SMALLER_EDGE_FRUIT_QUANTITY = 1;
    private static final int POSITIVE_FRUIT_QUANTITY_VALUE = 100;
    private static final int NEGATIVE_FRUIT_QUANTITY_VALUE = -100;
    private ShopService shopService;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        ShopInventory.inventory.clear();
    }

    @Test
    void processTransactionsWithBalance_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        TEST_FRUIT,
                        POSITIVE_FRUIT_QUANTITY_VALUE));

        shopService.process(transactions);

        assertEquals(POSITIVE_FRUIT_QUANTITY_VALUE, ShopInventory.inventory.get(TEST_FRUIT));
    }

    @Test
    void processTransactionsWithBalance_NegativeQuantity_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        TEST_FRUIT,
                        NEGATIVE_FRUIT_QUANTITY_VALUE));

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    void processTransactionWithSupply_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        TEST_FRUIT,
                        POSITIVE_FRUIT_QUANTITY_VALUE));

        shopService.process(transactions);

        assertEquals(POSITIVE_FRUIT_QUANTITY_VALUE, ShopInventory.inventory.get(TEST_FRUIT));
    }

    @Test
    void processTransactionWithSupply_NegativeQuantity_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        TEST_FRUIT,
                        NEGATIVE_FRUIT_QUANTITY_VALUE));

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    void processTransactionWithReturnOperation_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        TEST_FRUIT,
                        POSITIVE_FRUIT_QUANTITY_VALUE));

        shopService.process(transactions);

        assertEquals(POSITIVE_FRUIT_QUANTITY_VALUE, ShopInventory.inventory.get(TEST_FRUIT));
    }

    @Test
    void processTransactionWithReturn_NegativeQuantity_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        TEST_FRUIT,
                        NEGATIVE_FRUIT_QUANTITY_VALUE));

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    void processTransactionWithPurchase_ok() {
        int purchaseFruitQuantity = 10;
        int expectedFruitQuantity = 90;
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        TEST_FRUIT,
                        POSITIVE_FRUIT_QUANTITY_VALUE),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        TEST_FRUIT,
                        purchaseFruitQuantity));

        shopService.process(transactions);

        assertEquals(expectedFruitQuantity, ShopInventory.inventory.get(TEST_FRUIT));
    }

    @Test
    void processTransactionWithPurchase_BiggerQuantityThanExist_NotOk() {
        int purchaseFruitQuantity = 10;
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        TEST_FRUIT,
                        SMALLER_EDGE_FRUIT_QUANTITY),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        TEST_FRUIT,
                        purchaseFruitQuantity));

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    void processTransactionWithPurchase_NegativeQuantity_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        TEST_FRUIT,
                        POSITIVE_FRUIT_QUANTITY_VALUE),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        TEST_FRUIT,
                        NEGATIVE_FRUIT_QUANTITY_VALUE));

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }
}
