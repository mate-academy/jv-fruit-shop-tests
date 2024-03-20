package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.StorageDao;
import core.basesyntax.model.impl.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.PurchaseOperationHandler;
import core.basesyntax.service.strategy.ReturnOperationHandler;
import core.basesyntax.service.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorTest {
    private static FruitTransactionProcessor processor;
    private static Map<OperationType, OperationHandler<FruitTransaction>> strategyMap;
    private static FruitTransaction transaction;
    private static final OperationType VALID_OPERATION_TYPE = OperationType.BALANCE;
    private static final String VALID_PRODUCT_TYPE = "apple";
    private static final int VALID_OPERATION_VALUE = 100;

    @BeforeAll
    public static void initAllFields() {
        strategyMap = new HashMap<>();
        strategyMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        strategyMap.put(OperationType.RETURN, new ReturnOperationHandler());
        strategyMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        strategyMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        processor = new FruitTransactionProcessor(strategyMap);
    }

    @BeforeEach
    public void setValidParam() {
        StorageDao.storage.clear();
        transaction = new FruitTransaction(VALID_OPERATION_TYPE,
                VALID_PRODUCT_TYPE, VALID_OPERATION_VALUE);
    }

    @Test
    public void processValidTransaction_ok() {
        int numberOfAdds = 10;
        for (int i = 0; i < numberOfAdds; i++) {
            processor.process(transaction);
        }
        Assertions.assertEquals(transaction.getTransactionValue() * numberOfAdds,
                StorageDao.storage.get(transaction.getProductType()));
    }

    @Test
    public void processWithNullTransaction_notOk() {
        transaction = null;
        Throwable exception = assertThrows(RuntimeException.class,
                () -> processor.process(transaction));
        Assertions.assertEquals("Error: transaction is null!", exception.getMessage());
    }

    @Test
    public void processWithNullTransactionOperationType_notOk() {
        transaction.setOperationType(null);
        Throwable exception = assertThrows(RuntimeException.class,
                () -> processor.process(transaction));
        Assertions.assertEquals("Error: transaction type is null!", exception.getMessage());
    }

    @Test
    public void processWithNullTransactionProductType_notOk() {
        transaction.setProductType(null);
        Throwable exception = assertThrows(RuntimeException.class,
                () -> processor.process(transaction));
        Assertions.assertEquals("Error: product type is null!", exception.getMessage());
    }

    @Test
    public void processWithTransactionValueLessThenZero_notOk() {
        transaction.setTransactionValue(-100);
        Throwable exception = assertThrows(RuntimeException.class,
                () -> processor.process(transaction));
        Assertions.assertEquals("Error: transaction value can not be less then zero!",
                exception.getMessage());
    }
}
