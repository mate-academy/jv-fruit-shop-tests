package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.activities.BalanceHandler;
import core.basesyntax.strategy.activities.OperationHandler;
import core.basesyntax.strategy.activities.PurchaseHandler;
import core.basesyntax.strategy.activities.ReturnHandler;
import core.basesyntax.strategy.activities.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/testFile.csv";
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            Map.of(
                    FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnHandler()
            );
    private static final Map<String,Integer> TEST_DATA = Map.of(
            "banana", 70
    );
    private static final String BANANA_FRUIT = "banana";
    private OperationStrategy operationStrategy;
    private FruitService fruitService;
    private FileReaderService reader;
    private TransactionMapperService transactionMapperService;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitService = new FruitServiceImpl(operationStrategy);
        reader = new FileReaderServiceImpl();
        transactionMapperService = new TransactionMapperService();
        storage = new StorageImpl();
    }

    @Test
    public void processTransactions_Test() {
        String inputFileLines = reader.read(FILE_PATH);
        List<FruitTransaction> transactions =
                transactionMapperService.stringToFruitTransaction(inputFileLines);
        fruitService.processTransactions(transactions);

        int expected = TEST_DATA.get(BANANA_FRUIT);
        int actual = storage.getValue(BANANA_FRUIT);

        assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        new StorageImpl().clear();
    }
}
