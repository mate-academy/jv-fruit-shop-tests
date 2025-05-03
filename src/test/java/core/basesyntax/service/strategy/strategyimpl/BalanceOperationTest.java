package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final Operation OPERATION_CODE = Operation.BALANCE;
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private StorageDaoImpl storageDao;
    private BalanceOperation balanceOperation;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        balanceOperation = new BalanceOperation(storageDao);
    }

    @Test
    void handle_validTransaction_Ok() {
        FruitTransactionDto transaction = new FruitTransactionDto(OPERATION_CODE,
                FRUIT_NAME,
                QUANTITY);
        balanceOperation.handle(transaction);
        assertEquals(QUANTITY, storageDao.get(FRUIT_NAME));
    }
}
