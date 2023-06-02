package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.FruitHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceFruitHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseFruitHandlerImpl;
import core.basesyntax.strategy.impl.ReturnFruitHandlerImpl;
import core.basesyntax.strategy.impl.SupplyFruitHandlerImpl;
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
    void processAllTransations_validDataSupplyOperation_ok() {
        List<FruitTransaction> transactions = List.of(new FruitTransaction(Operation.SUPPLY,
                NAME_OF_FRUIT, INITIAL_QUANTITY));
        fruitService.processAllTransations(transactions, fruitHandlerStrategy);
        assertEquals(Storage.getFruitStorage().get(NAME_OF_FRUIT), 80);
    }

    @Test
    void processAllTransations_validDataPurchaseOperation_ok() {
        List<FruitTransaction> transactions = List.of(new FruitTransaction(Operation.PURCHASE,
                NAME_OF_FRUIT, 10));
        fruitService.processAllTransations(transactions, fruitHandlerStrategy);
        assertEquals(Storage.getFruitStorage().get(NAME_OF_FRUIT), 30);
    }

    @Test
    void processAllTransations_validData_ok() {
        List<FruitTransaction> transactions = List.of(new FruitTransaction(Operation.PURCHASE,
                        NAME_OF_FRUIT, 10),
                new FruitTransaction(Operation.RETURN, NAME_OF_FRUIT, 10),
                new FruitTransaction(Operation.SUPPLY, NAME_OF_FRUIT, 15),
                new FruitTransaction(Operation.PURCHASE, NAME_OF_FRUIT, 20));
        fruitService.processAllTransations(transactions, fruitHandlerStrategy);
        assertEquals(Storage.getFruitStorage().get(NAME_OF_FRUIT), 35);
    }

    @Test
    void processAllTransations_withNull_notOk() {
        Assertions.assertThrows(NullPointerException.class, () -> fruitService
                .processAllTransations(null, fruitHandlerStrategy));
    }
}
