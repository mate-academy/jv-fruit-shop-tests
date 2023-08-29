package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerImplTest {
    private static FruitService fruitService;

    @BeforeAll
    public static void setUp() {
        fruitService = new FruitServiceImpl();
    }

    @Test
    public void handleOperation_Ok() {
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 120);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", 20);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        fruitService.processTransactions(transactions);
        int quantity = FruitStorage.getQuantity("banana");
        Assertions.assertEquals(quantity, 140);
    }

    @Test
    public void handleOperationWithNegativeValue_Ok() {
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", 120);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", 140);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        Assertions.assertThrows(RuntimeException.class, () -> {
            FruitStorage.getQuantity("banana");
        });
    }

    @AfterEach
    public void afterEachTest() {
        FruitStorage.getFruits().clear();
    }
}
