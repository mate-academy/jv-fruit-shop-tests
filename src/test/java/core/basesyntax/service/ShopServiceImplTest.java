package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.service.OperationHandlerProvider;
import core.basesyntax.basesyntax.service.ShopService;
import core.basesyntax.basesyntax.service.impl.OperationHandlerProviderImpl;
import core.basesyntax.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.basesyntax.strategy.BalanceOperation;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.basesyntax.strategy.ReturnOperation;
import core.basesyntax.basesyntax.strategy.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_ORANGE = "orange";
    private static final String FRUIT_GRAPES = "grapes";
    private static final int QUANTITY_2 = 2;
    private static final int QUANTITY_3 = 3;
    private static final int QUANTITY_4 = 4;
    private static final int QUANTITY_5 = 5;
    private static final int QUANTITY_10 = 10;
    private static final int QUANTITY_15 = 15;
    private static final int QUANTITY_18 = 18;
    private static final int QUANTITY_20 = 20;

    private ShopService service;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
            );
        OperationHandlerProvider provider = new OperationHandlerProviderImpl(handlers);
        StorageImpl storage = new StorageImpl();
        service = new ShopServiceImpl(provider, storage);
    }

    @Test
    void process_validScenario_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_APPLE, QUANTITY_5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        FRUIT_APPLE, QUANTITY_15),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_APPLE, QUANTITY_4),
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        FRUIT_APPLE, QUANTITY_2)
        );
        StorageImpl result = service.process(transactions);
        assertEquals(QUANTITY_18, result.getQuantity(FRUIT_APPLE));
    }

    @Test
    void process_multipleFruits_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_ORANGE, QUANTITY_10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, QUANTITY_20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_BANANA, QUANTITY_10)
        );
        StorageImpl result = service.process(transactions);
        assertEquals(QUANTITY_10, result.getQuantity(FRUIT_ORANGE));
        assertEquals(QUANTITY_10, result.getQuantity(FRUIT_BANANA));
    }

    @Test
    void process_notEnoughStock_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_GRAPES, QUANTITY_3),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_GRAPES, QUANTITY_5)
        );
        assertThrows(RuntimeException.class, () -> service.process(transactions));
    }

    @Test
    void process_nullTransactions_notOK() {
        assertThrows(RuntimeException.class,
                () -> service.process(null));
    }
}
