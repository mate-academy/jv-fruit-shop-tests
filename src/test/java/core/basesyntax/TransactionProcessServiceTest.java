package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.TransactionProcessService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionProcessServiceTest {
    private static OperationStrategy operationStrategy;
    private static TransactionProcessService dataProcess;

    @AfterEach
    public void clearStorage() {
        Storage.Storage.clear();
    }

    @BeforeAll
    static void setUp() {
        operationStrategy = new OperationStrategyImpl(
                Map.of(Operation.BALANCE.getCode(), new BalanceOperationHandler(),
                        Operation.PURCHASE.getCode(), new PurchaseOperationHandler(),
                        Operation.RETURN.getCode(), new ReturnOperationHandler(),
                        Operation.SUPPLY.getCode(), new SupplyOperationHandler()));
        dataProcess = new TransactionProcessService(operationStrategy);
    }

    @Test
    public void process_validInput_ok() {
        String[] data = {"b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50"};
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("banana", 152);
        expectedResult.put("apple", 90);

        dataProcess.process(data);
        assertTrue(expectedResult.equals(Storage.Storage));
    }
}
