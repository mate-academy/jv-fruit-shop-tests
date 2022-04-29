package core.basesyntax.service;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.service.reader.ReaderServiceICsvImpl;
import core.basesyntax.service.writer.WriterServiceCsvImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static final String NORMAL_FILE = "test2.csv";
    private static final String BALANCE_RETURN_FILE = "test3.csv";
    private String[] expectedResult;
    private String[] resultExpected;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMapTest =
            new HashMap<>();
    private FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoImpl();
    private FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl(
            new ReaderServiceICsvImpl(),
            new WriterServiceCsvImpl(),
            new OperationStrategyImpl(operationHandlerMapTest));

    @Before
    public void setUp() {
        operationHandlerMapTest.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMapTest.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMapTest.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMapTest.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));
    }

    @Test
    public void balanceReturnFile_Ok() {
        String outputFile = "resultBalanceReturnFile.csv";
        expectedResult = new String[] {"banana,20", "apple,130"};
        String[] actual = new String[2];
        fruitTransactionService.process(BALANCE_RETURN_FILE, outputFile);
        actual[0] = "banana," + Storage.fruits.get("banana").getQuantity();
        actual[1] = "apple," + Storage.fruits.get("apple").getQuantity();
        Assert.assertArrayEquals(expectedResult, actual);
    }

    @Test
    public void outputFileIsCorrect_Ok() {
        String outputFile = "resultTest.csv";
        resultExpected = new String[] {"banana,152", "apple,90"};
        fruitTransactionService.process(NORMAL_FILE, outputFile);
        String[] actual = new String[2];
        actual[0] = "banana," + Storage.fruits.get("banana").getQuantity();
        actual[1] = "apple," + Storage.fruits.get("apple").getQuantity();
        Assert.assertArrayEquals(resultExpected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
