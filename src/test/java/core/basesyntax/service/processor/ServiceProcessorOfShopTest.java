package core.basesyntax.service.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.TypeStrategy;
import core.basesyntax.service.strategy.TypeStrategyImpl;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.OperationHandler;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceProcessorOfShopTest {
    private ShopProcessor serviceProcessorOfShop;
    private TypeStrategy typeStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitRecord.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitRecord.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlers.put(FruitRecord.Operation.PURCHASE, new PurchaseOperationHandler());

        typeStrategy = new TypeStrategyImpl(operationHandlers);
        serviceProcessorOfShop = new ShopProcessorImpl(typeStrategy);
    }

    @Test
    void process_validTransactions_noExceptionsThrown() {
        Storage.storage.put("apple", 100);
        List<FruitRecord> transactions = new ArrayList<>();
        transactions.add(new FruitRecord(FruitRecord.Operation.BALANCE, "banana", 100));
        transactions.add(new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 50));
        assertDoesNotThrow(() -> serviceProcessorOfShop.process(transactions));

    }

    @Test
    void process_emptyTransactions_noExceptionsThrown() {
        List<FruitRecord> emptyTransactions = new ArrayList<>();

        assertDoesNotThrow(() -> serviceProcessorOfShop.process(emptyTransactions));
    }
}
