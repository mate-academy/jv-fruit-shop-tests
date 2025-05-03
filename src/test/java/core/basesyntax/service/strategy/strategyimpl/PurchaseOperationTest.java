package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exception.DataNotFoundException;
import core.basesyntax.exception.NegativeBalanceException;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final Operation OPERATION_CODE = Operation.PURCHASE;
    private static final String FRUIT_NAME = "apple";
    private static final String INVALID_FRUIT_NAME = "banbana";
    private static final int INITIAL_QUANTITY = 20;
    private static final int PURCHASE_QUANTITY = 21;
    private static final int QUANTITY = 10;

    private StorageDaoImpl storageDao;
    private PurchaseOperation purchaseOperation;
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        purchaseOperation = new PurchaseOperation(storageDao);
        storageDao.add(FRUIT_NAME, INITIAL_QUANTITY);
    }

    @Test
    void handle_validTransaction_Ok() {
        FruitTransactionDto transaction = new FruitTransactionDto(OPERATION_CODE,
                FRUIT_NAME,
                QUANTITY);
        purchaseOperation.handle(transaction);
        int expected = INITIAL_QUANTITY - QUANTITY;
        assertEquals(expected, storageDao.get(FRUIT_NAME));
    }

    @Test
    public void handle_purchaseNonExistingFruit_notOk() {
        FruitTransactionDto transaction = new FruitTransactionDto(OPERATION_CODE,
                INVALID_FRUIT_NAME,
                QUANTITY);
        assertThrows(DataNotFoundException.class, () -> {
            purchaseOperation.handle(transaction);
        });
    }

    @Test
    public void handle_purchaseNonNotEnoughtQuantity_notOk() {
        FruitTransactionDto transaction = new FruitTransactionDto(OPERATION_CODE,
                FRUIT_NAME,
                PURCHASE_QUANTITY);
        assertThrows(NegativeBalanceException.class, () -> {
            purchaseOperation.handle(transaction);
        });
    }
}
