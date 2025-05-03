package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.DataProcessingService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessingServiceImplTest {
    private DataProcessingService dataProcessingService;

    @BeforeEach
    void setUp() {
        dataProcessingService = new DataProcessingServiceImpl(new TransactionStrategyImpl());
        Storage.clear();
    }

    @Test
    void processData_OK() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitShopOperation.BALANCE, "apple", 40)
        );
        Map<String, Integer> expected = new HashMap<>(Map.of("apple", 40));
        dataProcessingService.processData(transactions);
        assertEquals(expected, Storage.getAll(),
                "Storage doesn't have correct data after processing transactions!");
    }
}
