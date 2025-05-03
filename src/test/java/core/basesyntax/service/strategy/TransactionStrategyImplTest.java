package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitModel;
import core.basesyntax.model.OperationModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionStrategyImplTest {
    private TransactionStrategy transactionStrategy;

    @BeforeEach
    void setUp() {
        transactionStrategy = new TransactionStrategyImpl();
    }

    @Test
    void transactionOperator_validOperation_callsHandler_ok() {
        List<OperationModel> operationList = new ArrayList<>();
        operationList.add(new OperationModel(OperationModel.Operation.SUPPLY,
                FruitModel.BANANA, 25));
        transactionStrategy.transactionOperator(operationList);
        assertEquals(25, Storage.fruitStorage.get(FruitModel.BANANA));
    }

    @Test
    void transactionOperator_nullOperation_throwsException() {
        List<OperationModel> operationList = new ArrayList<>();
        operationList.add(new OperationModel(null,
                FruitModel.PEACH, 78));
        assertThrows(RuntimeException.class,
                () -> transactionStrategy.transactionOperator(operationList));
    }

    @Test
    void transactionOperator_emptyOperationList() {
        List<OperationModel> operationList = new ArrayList<>();
        transactionStrategy.transactionOperator(operationList);
        assertTrue(Storage.fruitStorage.isEmpty());
    }

    @Test
    void transactionOperator_changesStorageState_OK() {
        List<OperationModel> operationList = new ArrayList<>();
        operationList.add(new OperationModel(OperationModel.Operation.BALANCE,
                FruitModel.APPLE, 20));
        operationList.add(new OperationModel(OperationModel.Operation.SUPPLY,
                FruitModel.BANANA, 15));
        operationList.add(new OperationModel(OperationModel.Operation.PURCHASE,
                FruitModel.APPLE, 8));
        transactionStrategy.transactionOperator(operationList);
        assertEquals(12, Storage.fruitStorage.get(FruitModel.APPLE));
        assertEquals(15, Storage.fruitStorage.get(FruitModel.BANANA));
    }

    @AfterEach
    void cleanUp() {
        Storage.fruitStorage.clear();
    }
}
