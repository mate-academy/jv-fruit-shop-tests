package core.basesyntax.service.strategy.strategyimpl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplyOperationTest {
    private static final Operation OPERATION_CODE = Operation.SUPPLY;
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private static final int INITIAL_QUANTITY = 20;
    private StorageDaoImpl storageDao;
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        supplyOperation = new SupplyOperation(storageDao);
    }

    @Test
    void handle_validTransaction_Ok() {
        storageDao.add(FRUIT_NAME, INITIAL_QUANTITY);
        FruitTransactionDto transaction = new FruitTransactionDto(OPERATION_CODE, FRUIT_NAME, QUANTITY);
        int expected = INITIAL_QUANTITY + QUANTITY;
        supplyOperation.handle(transaction);
        assertEquals(expected, storageDao.get(FRUIT_NAME));
    }
}