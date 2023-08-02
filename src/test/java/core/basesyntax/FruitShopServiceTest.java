package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitsQuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.implementations.FruitShopServiceImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.DataHandlerStrategy;
import core.basesyntax.strategy.handler.BalanceDataHandler;
import core.basesyntax.strategy.handler.DataHandler;
import core.basesyntax.strategy.handler.PurchaseDataHandler;
import core.basesyntax.strategy.handler.ReturnDataHandler;
import core.basesyntax.strategy.handler.SupplyDataHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopServiceTest {
    private FruitShopService fruitShopService;
    private Map<FruitTransaction.Operation, DataHandler> enumHandlerMap;

    @BeforeEach
    void setUp() {
        enumHandlerMap = initializeEnumHandlerMap();
        DataHandlerStrategy dataHandlerStrategy = new TestDataHandlerStrategy(enumHandlerMap);
        List<FruitTransaction> transactions = new ArrayList<>();
        fruitShopService = new FruitShopServiceImpl(transactions, dataHandlerStrategy);
        Storage.createMap();
    }

    private Map<FruitTransaction.Operation, DataHandler> initializeEnumHandlerMap() {
        Map<FruitTransaction.Operation, DataHandler> enumHandlerMap = new HashMap<>();
        enumHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceDataHandler());
        enumHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyDataHandler());
        enumHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseDataHandler());
        enumHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnDataHandler());
        return enumHandlerMap;
    }

    @Test
    void testUpdateDataWithValidTransactions() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 5));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50));
        fruitShopService = new FruitShopServiceImpl(transactions,
                new TestDataHandlerStrategy(enumHandlerMap));
        fruitShopService.updateData();
        assertEquals(105, Storage.getStorage().get("apple"));
        assertEquals(50, Storage.getStorage().get("banana"));
    }

    @Test
    void testUpdateDataWithNegativeQuantity() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", -50));
        fruitShopService = new FruitShopServiceImpl(transactions,
                new TestDataHandlerStrategy(enumHandlerMap));
        assertThrows(FruitsQuantityException.class, fruitShopService::updateData);
    }

    @Test
    void testUpdateDataWithEmptyTransactions() {
        fruitShopService.updateData();
        assertEquals(0, Storage.getStorage().size());
    }

    // Custom DataHandlerStrategy for testing purposes
    private static class TestDataHandlerStrategy implements DataHandlerStrategy {
        private final Map<FruitTransaction.Operation, DataHandler> operationDataHandlerMap;

        public TestDataHandlerStrategy(Map<FruitTransaction.Operation, DataHandler>
                                               operationDataHandlerMap) {
            this.operationDataHandlerMap = operationDataHandlerMap;
        }

        @Override
        public DataHandler getHandler(FruitTransaction.Operation operation) {
            return operationDataHandlerMap.get(operation);
        }
    }
}
