package core.basesyntax;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.OperationHandlerBalanceImpl;
import core.basesyntax.strategy.OperationHandlerImplSupply;
import core.basesyntax.strategy.OperationHandlerPurchaseImpl;
import core.basesyntax.strategy.OperationHandlerReturnImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static TransactionService transactionService;
    private static List<FruitTransaction> testData;
    private static FruitTransaction validTransaction;
    private static FruitTransaction invalidTransaction;
    private static Storage storage;

    private static final String FRUIT = "apple";
    private static final int QUANTITY = 20;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new OperationHandlerBalanceImpl());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new OperationHandlerImplSupply());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new OperationHandlerPurchaseImpl());
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new OperationHandlerReturnImpl());
        storage = new Storage();
        validTransaction = new FruitTransaction();
        invalidTransaction = new FruitTransaction();
        validTransaction.setQuantity(QUANTITY);
        validTransaction.setFruit(FRUIT);
        validTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        invalidTransaction.setOperation(null);
        testData = new ArrayList<>();
        testData.add(validTransaction);
        transactionService = new TransactionServiceImpl(operationHandlers);
    }

    @Test
    public void handleTransation_Ok() {
        transactionService.handleTransaction(testData);
        int actual = storage.storage.get(FRUIT);
        int expected = QUANTITY;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handleTransation_EmptyStorage_NotOk() {
        storage.storage.clear();
        Integer actual = storage.storage.get(FRUIT);
        Assert.assertEquals(null, actual);
    }
}
