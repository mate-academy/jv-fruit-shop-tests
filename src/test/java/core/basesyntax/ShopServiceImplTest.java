package core.basesyntax;

import static core.basesyntax.db.Storage.clearFruitStock;
import static core.basesyntax.db.Storage.getFruitStock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        shopService = new ShopServiceImpl(operationHandlers);
    }

    @Test
    void process_emptyList_notOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shopService.process(new ArrayList<>()));
        assertEquals("Empty list of transactions!", exception.getMessage());
    }

    @Test
    void process_balanceThenPurchaseList_Ok() {
        List<FruitTransaction> validList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 99)
        );
        shopService.process(validList);
        System.out.println(getFruitStock());
        assertEquals(1, getFruitStock().get("apple"));
    }

    @Test
    void process_supplyThenPurchaseList_Ok() {
        List<FruitTransaction> validList = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 99),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 97)
        );
        shopService.process(validList);
        assertEquals(2, getFruitStock().get("banana"));
    }

    @Test
    void process_returnThenPurchaseList_Ok() {
        List<FruitTransaction> validList = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 4),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 1)
        );
        shopService.process(validList);
        assertEquals(3, getFruitStock().get("apple"));
    }

    @Test
    void process_purchaseAllStock_Ok() {
        List<FruitTransaction> validList = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "kiwi", 4),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 4),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "melon", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "melon", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "watermelon", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "watermelon", 50)
        );
        shopService.process(validList);
        assertEquals(0, getFruitStock().get("kiwi"));
        assertEquals(0, getFruitStock().get("melon"));
        assertEquals(0, getFruitStock().get("watermelon"));
    }

    @Test
    void process_purchaseMoreThanAvailable_notOk() {
        List<FruitTransaction> validList = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "cherry", 4),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "cherry", 99)
        );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shopService.process(validList));
        assertEquals("Not enough fruits in stock! Currently in stock: 4", exception.getMessage());
    }

    @AfterAll
    static void afterAll() {
        clearFruitStock();
    }
}
