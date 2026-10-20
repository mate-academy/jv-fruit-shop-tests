package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        shopService = new ShopServiceImpl(new OperationStrategyImpl(handlers));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction balance = new FruitTransaction();
        balance.setOperation(FruitTransaction.Operation.BALANCE);
        balance.setFruit("banana");
        balance.setQuantity(100);

        FruitTransaction purchase = new FruitTransaction();
        purchase.setOperation(FruitTransaction.Operation.PURCHASE);
        purchase.setFruit("banana");
        purchase.setQuantity(50);

        shopService.process(List.of(balance, purchase));

        assertEquals(50, Storage.fruits.get("banana"));
    }

    @Test
    void process_supplyTransaction_ok() {
        FruitTransaction balance = new FruitTransaction();
        balance.setOperation(FruitTransaction.Operation.BALANCE);
        balance.setFruit("apple");
        balance.setQuantity(50);

        FruitTransaction supply = new FruitTransaction();
        supply.setOperation(FruitTransaction.Operation.SUPPLY);
        supply.setFruit("apple");
        supply.setQuantity(30);

        shopService.process(List.of(balance, supply));

        assertEquals(80, Storage.fruits.get("apple"));
    }

    @Test
    void process_returnTransaction_ok() {
        FruitTransaction balance = new FruitTransaction();
        balance.setOperation(FruitTransaction.Operation.BALANCE);
        balance.setFruit("apple");
        balance.setQuantity(50);

        FruitTransaction returnTr = new FruitTransaction();
        returnTr.setOperation(FruitTransaction.Operation.RETURN);
        returnTr.setFruit("apple");
        returnTr.setQuantity(10);

        shopService.process(List.of(balance, returnTr));

        assertEquals(60, Storage.fruits.get("apple"));
    }
}
