package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.ProcessService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProcessServiceImplTest {
    private static ProcessService processService;
    private static Map<OperationType, Operation> strategyMap;

    @BeforeAll
    static void setUp() {
        processService = new ProcessServiceImpl();
        strategyMap = new HashMap<>();
        strategyMap.put(OperationType.BALANCE, new BalanceOperation());
        strategyMap.put(OperationType.PURCHASE, new PurchaseOperation());
        strategyMap.put(OperationType.SUPPLY, new SupplyOperation());
        strategyMap.put(OperationType.RETURN, new ReturnOperation());
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(OperationType.BALANCE, "banana", 20),
                new FruitTransaction(OperationType.SUPPLY, "banana", 100),
                new FruitTransaction(OperationType.PURCHASE, "banana", 5),
                new FruitTransaction(OperationType.RETURN, "banana", 10)
        );
        processService.processTransactions(fruitTransactions, strategyMap);
        assertEquals(125, Storage.getFruits().get("banana"));
    }
}
