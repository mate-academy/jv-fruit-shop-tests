package core.basesyntax.service.strategy.strategyimpl;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exception.DataNotFoundException;
import core.basesyntax.exception.NegativeBalanceException;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReturnOperationTest {
    private static final Operation OPERATION_CODE = Operation.RETURN;
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 1;
    private static final int INITIAL_QUANTITY = 20;

    private OperationHandler operationHandler;
    private StorageDaoImpl storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new ReturnOperation(storageDao);
        storageDao.add(FRUIT_NAME, INITIAL_QUANTITY);
    }

    @Test
    public void handle_ReturnFruit_Ok() {
        FruitTransactionDto transaction = new FruitTransactionDto(OPERATION_CODE, FRUIT_NAME, QUANTITY);
        operationHandler.handle(transaction);
        assertEquals(INITIAL_QUANTITY + QUANTITY, storageDao.get(FRUIT_NAME));
    }

    @Test
    public void handle_ReturnNonExistingFruit_notOk() {
        FruitTransactionDto transaction = new FruitTransactionDto(OPERATION_CODE,"banbana", QUANTITY);
        assertThrows(DataNotFoundException.class, () -> {
            operationHandler.handle(transaction);
        });
    }

}