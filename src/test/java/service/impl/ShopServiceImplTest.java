package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;

public class ShopServiceImplTest {

    @BeforeEach
    void setUp() throws Exception {
        Map<String, Integer> map = getFruitStorage();
        map.clear();
    }

    private Map<String, Integer> getFruitStorage() throws Exception {
        var field = Storage.class.getDeclaredField("fruitStorage");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> map = (Map<String, Integer>) field.get(null);
        return map;
    }

    static class BalanceHandler implements OperationHandler {
        @Override
        public void apply(FruitTransaction transaction) {
            Storage.setQuantity(transaction.getFruit(), transaction.getQuantity());
        }
    }

    static class SupplyHandler implements OperationHandler {
        @Override
        public void apply(FruitTransaction transaction) {
            Storage.addQuantity(transaction.getFruit(), transaction.getQuantity());
        }
    }

    static class PurchaseHandler implements OperationHandler {
        @Override
        public void apply(FruitTransaction transaction) {
            Storage.addQuantity(transaction.getFruit(), -transaction.getQuantity());
        }
    }

    static class ReturnHandler implements OperationHandler {
        @Override
        public void apply(FruitTransaction transaction) {
            Storage.addQuantity(transaction.getFruit(), transaction.getQuantity());
        }
    }

    private ShopService createShopService() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler()
        );
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        return new ShopServiceImpl(strategy);
    }

    @Test
    void process_transactions_updatesStorageCorrectly() {
        ShopService shopService = createShopService();

        FruitTransaction t1 = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction t2 = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5);
        FruitTransaction t3 = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 3);
        FruitTransaction t4 = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 2);

        shopService.process(List.of(t1, t2, t3, t4));

        assertEquals(7, Storage.getQuantity("apple"));
        assertEquals(7, Storage.getQuantity("banana"));
    }
}
