package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.Instruction;
import core.basesyntax.service.OperationExecutor;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationExecutorImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private Map<FruitOperation, Operation> operationsHandlers = Map.of(
            FruitOperation.BALANCE, new BalanceOperation(storageDao),
            FruitOperation.SUPPLY, new SupplyOperation(storageDao),
            FruitOperation.PURCHASE, new PurchaseOperation(storageDao),
            FruitOperation.RETURN, new ReturnOperation(storageDao)
    );
    private OperationExecutor operationExecutor = new OperationExecutorImpl(operationsHandlers);
    private List<Instruction> list = List.of(
            new Instruction(FruitOperation.PURCHASE, "banana", 20),
            new Instruction(FruitOperation.SUPPLY, "orange", 20),
            new Instruction(FruitOperation.RETURN, "pineapple", 20)
            );

    @BeforeAll
    public static void storageSetUp() {
        Storage.fruits.put("banana", 50);
        Storage.fruits.put("orange", 100);
        Storage.fruits.put("pineapple", 200);
    }

    @Test
    void proceedAll_ok() {
        operationExecutor.proceedAll(list);
        int expectedBanana = 30;
        int expectedOrange = 120;
        int expectedPineapple = 220;
        assertEquals(expectedBanana, Storage.fruits.get("banana"),
                "Method proceedAll wrong execute operation");
        assertEquals(expectedOrange, Storage.fruits.get("orange"),
                "Method proceedAll wrong execute operation");
        assertEquals(expectedPineapple, Storage.fruits.get("pineapple"),
                "Method proceedAll wrong execute operation");
    }

    @Test
    void proceedAll_wrongHandler_notOk() {
        List<Instruction> modifiedList = List.of(
                new Instruction(FruitOperation.BALANCE, "banana", 1)
        );
        assertThrows(RuntimeException.class, () -> operationExecutor.proceedAll(modifiedList));
    }
}
