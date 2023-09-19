package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProcessService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.BalanceOperationsHandler;
import core.basesyntax.strategy.impl.PurchaseOperationsHandler;
import core.basesyntax.strategy.impl.ReturnOperationsHandler;
import core.basesyntax.strategy.impl.SupplyOperationsHandler;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProcessServiceImplTest {
    private static ProcessService processService;

    @BeforeAll
    static void beforeAll() {
        processService = new ProcessServiceImpl(Map.of(
                ActivityType.BALANCE, new BalanceOperationsHandler(),
                ActivityType.SUPPLY, new SupplyOperationsHandler(),
                ActivityType.PURCHASE, new PurchaseOperationsHandler(),
                ActivityType.RETURN, new ReturnOperationsHandler()
        ));

    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void process_ValidOperations_Ok() {
        List<FruitTransaction> data = List.of(
                new FruitTransaction(ActivityType.BALANCE, "banana", 20),
                new FruitTransaction(ActivityType.BALANCE, "apple", 100),
                new FruitTransaction(ActivityType.SUPPLY, "banana", 100)
        );
        assertDoesNotThrow(() -> processService.process(data));
    }

    @Test
    void process_EmptyMap_Ok() {
        assertDoesNotThrow(() -> processService.process(Collections.emptyList()));
    }
}
