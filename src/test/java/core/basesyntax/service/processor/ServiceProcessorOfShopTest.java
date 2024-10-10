package core.basesyntax.service.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.TypeStrategy;
import core.basesyntax.service.strategy.TypeStrategyImpl;
import core.basesyntax.service.strategy.strategyimpl.BalanceOperation;
import core.basesyntax.service.strategy.strategyimpl.OperationHandler;
import core.basesyntax.service.strategy.strategyimpl.PurchaseOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceProcessorOfShopTest {
    private ServiceProcessorOfShop serviceProcessorOfShop;
    private TypeStrategy typeStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitRecord.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitRecord.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitRecord.Operation.PURCHASE, new PurchaseOperation());

        typeStrategy = new TypeStrategyImpl(operationHandlers);
        serviceProcessorOfShop = new ServiceProcessorOfShopImpl(typeStrategy);
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
