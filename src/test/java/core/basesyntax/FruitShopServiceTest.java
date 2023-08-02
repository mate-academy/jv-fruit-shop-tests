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
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 50;
    private static final int EXPECTED_APPLE_QUANTITY = 100;
    private static final String BANANA = "banana";
    private static final int BANANA_QUANTITY = 24;
    private static final int EXPECTED_BANANA_QUANTITY = 24;
    private static final int WRONG_QUANTITY = -50;
    private static final short EMPTY_STORAGE_SIZE = 0;
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
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, APPLE_QUANTITY));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, APPLE_QUANTITY));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE, APPLE_QUANTITY));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, BANANA_QUANTITY));
        fruitShopService = new FruitShopServiceImpl(transactions,
                new TestDataHandlerStrategy(enumHandlerMap));
        fruitShopService.updateData();
        assertEquals(EXPECTED_APPLE_QUANTITY, Storage.getStorage().get(APPLE));
        assertEquals(EXPECTED_BANANA_QUANTITY, Storage.getStorage().get(BANANA));
    }

    @Test
    void testUpdateDataWithNegativeQuantity() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, WRONG_QUANTITY));
        fruitShopService = new FruitShopServiceImpl(transactions,
                new TestDataHandlerStrategy(enumHandlerMap));
        assertThrows(FruitsQuantityException.class, fruitShopService::updateData);
    }

    @Test
    void testUpdateDataWithEmptyTransactions() {
        fruitShopService.updateData();
        assertEquals(EMPTY_STORAGE_SIZE, Storage.getStorage().size());
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
