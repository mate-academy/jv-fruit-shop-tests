package core.basesyntax.service.impl;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class ShopServiceTest {
    private ShopService shopService;

    @BeforeEach
    public void setUp() {
        shopService = new ShopServiceImpl(new OperationStrategyImpl());
        ShopStorage storage = ShopStorage.getInstance();
        storage.clear();
    }

    @Test
    public void process_validTransactions_success() {
        FruitTransaction balance = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        FruitTransaction supply = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50);
        FruitTransaction purchase = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30);

        shopService.process(Arrays.asList(balance, supply, purchase));

        assertEquals(120, ShopStorage.getInstance().getFruitQuantity("banana"));
    }

    @Test
    public void process_purchaseNotEnoughStock_throwsException() {

        FruitTransaction balance = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction purchase = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);

        shopService.process(Arrays.asList(balance));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shopService.process(Arrays.asList(purchase));
        });

        assertEquals("Not enough banana in stock.", exception.getMessage());
    }

    @Test
    public void process_returnOperation_updatesStockCorrectly() {
        FruitTransaction balance = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction returnOperation = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5);

        shopService.process(Arrays.asList(balance, returnOperation));

        assertEquals(15, ShopStorage.getInstance().getFruitQuantity("banana"));
    }
}

