package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.FruitHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceFruitHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseFruitHandlerImpl;
import core.basesyntax.strategy.impl.ReturnFruitHandlerImpl;
import core.basesyntax.strategy.impl.SupplyFruitHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceTest {
    private static FruitService fruitService;
    private static FruitHandlerStrategy fruitHandlerStrategy;
    private static List<FruitTransaction> transactions;
    private static final int INITIAL_QUANTITY = 40;
    private static final String NAME_OF_FRUIT = "banana";

    @BeforeAll
    static void setUp() {
        fruitService = new FruitServiceImpl();
        fruitHandlerStrategy = new FruitHandlerStrategy((new HashMap<>() {
            {
                put(Operation.BALANCE, new BalanceFruitHandlerImpl());
                put(Operation.SUPPLY, new SupplyFruitHandlerImpl());
                put(Operation.PURCHASE, new PurchaseFruitHandlerImpl());
                put(Operation.RETURN, new ReturnFruitHandlerImpl());
            }
        }));
        transactions = new ArrayList<>();
    }

    @BeforeEach
    void initialStorage() {
        Storage.getFruitStorage().put(NAME_OF_FRUIT, INITIAL_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void fruitService_validDataSupplyOperation_ok() {
        transactions = List.of(new FruitTransaction(Operation.SUPPLY,
                NAME_OF_FRUIT, INITIAL_QUANTITY));
        fruitService.processAllTransations(transactions, fruitHandlerStrategy);
        Assertions.assertEquals(Storage.getFruitStorage().get(NAME_OF_FRUIT), 80);
    }

    @Test
    void fruitService_validDataPurchaseOperation_ok() {
        transactions = List.of(new FruitTransaction(Operation. PURCHASE,
                NAME_OF_FRUIT, 10));
        fruitService.processAllTransations(transactions, fruitHandlerStrategy);
        Assertions.assertEquals(Storage.getFruitStorage().get(NAME_OF_FRUIT), 30);
    }

    @Test
    void fruitService_withNullHandler_notOk() {
        transactions = List.of(new FruitTransaction(null, NAME_OF_FRUIT, INITIAL_QUANTITY));
        fruitService.processAllTransations(transactions, fruitHandlerStrategy);
        Assertions.assertEquals(Storage.getFruitStorage().get(NAME_OF_FRUIT), INITIAL_QUANTITY);
    }
}
