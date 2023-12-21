package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ProcessServiceImpl;
import core.basesyntax.strategy.OperationBalancePerformer;
import core.basesyntax.strategy.OperationPerformer;
import core.basesyntax.strategy.OperationPurchasePerformer;
import core.basesyntax.strategy.OperationReturnPerformer;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.OperationSupplyPerformer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessServiceTest {
    private static Map<FruitTransaction.Operation, OperationPerformer> operationPerformerMap;
    private static OperationStrategy operationStrategy;
    private static ProcessService processService;
    private static List<FruitTransaction> data;

    @BeforeAll
    public static void setUp() {
        operationPerformerMap = new HashMap<>();
        operationPerformerMap.put(FruitTransaction.Operation.BALANCE,
                new OperationBalancePerformer());
        operationPerformerMap.put(FruitTransaction.Operation.PURCHASE,
                new OperationPurchasePerformer());
        operationPerformerMap.put(FruitTransaction.Operation.RETURN,
                new OperationReturnPerformer());
        operationPerformerMap.put(FruitTransaction.Operation.SUPPLY,
                new OperationSupplyPerformer());

        operationStrategy = new OperationStrategyImpl(operationPerformerMap);

        processService = new ProcessServiceImpl(operationStrategy);

        data = new ArrayList<>();
        data.add(new FruitTransaction("b", "banana", 20));
        data.add(new FruitTransaction("b", "apple", 100));
        data.add(new FruitTransaction("s", "banana", 10));
    }

    @BeforeEach
    public void initEach() {
        Storage.getFruits().clear();
    }

    @Test
    void processObjects_nullDataList_notOk() {
        assertThrows(RuntimeException.class, () -> processService.processObjects(null));
    }

    @Test
    void processObjects_emptyDataList_ok() {
        assertDoesNotThrow(() -> processService.processObjects(new ArrayList<>()));
    }

    @Test
    void processObjects_allOk() {
        assertDoesNotThrow(() -> processService.processObjects(data));
    }
}
