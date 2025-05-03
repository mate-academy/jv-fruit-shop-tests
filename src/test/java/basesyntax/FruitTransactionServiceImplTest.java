package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import basesyntax.db.Storage;
import basesyntax.exceptions.TransactionException;
import basesyntax.model.Fruit;
import basesyntax.service.impl.ReaderServiceImpl;
import basesyntax.strategy.FruitBalance;
import basesyntax.strategy.FruitHandler;
import basesyntax.strategy.FruitPurchase;
import basesyntax.strategy.FruitReturn;
import basesyntax.strategy.FruitSupply;
import basesyntax.strategy.FruitsTransactionStrategy;
import basesyntax.strategy.FruitsTransactionStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static final String FILE_TO_READ_PATH = "src/test/resources/file.csv";
    private static final String FILE_TO_WRITE_PATH = "src/test/resources/report.csv";
    private static final Map<String, FruitHandler> fruitHandlerMap = Map.of(
            FruitTransactionServiceImpl.Operation.BALANCE.getCode(), new FruitBalance(),
            FruitTransactionServiceImpl.Operation.PURCHASE.getCode(), new FruitPurchase(),
            FruitTransactionServiceImpl.Operation.SUPPLY.getCode(), new FruitSupply(),
            FruitTransactionServiceImpl.Operation.RETURN.getCode(), new FruitReturn()
    );
    private static final Map<String, Fruit> fruitsData = Storage.getFruits();
    private static FruitTransactionServiceImpl fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        FruitsTransactionStrategy fruitsTransactionStrategy = new FruitsTransactionStrategyImpl(
                fruitHandlerMap
        );
        fruitTransaction = new FruitTransactionServiceImpl(
                fruitsTransactionStrategy
        );
    }

    @AfterAll
    static void afterAll() {
        fruitsData.clear();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction.handleTransactions(FILE_TO_READ_PATH);
    }

    @Test
    void handleTransactions_filepathNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitTransaction.handleTransactions(null);
        });
    }

    @Test
    void writeReportToFile_filepathNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitTransaction.writeReportToFile(null);
        });
    }

    @Test
    void handleTransactions_StorageMap_Ok() {
        String bananaKey = "banana";
        int bananaQuantity = 152;
        String appleKey = "apple";
        int appleQuantity = 90;
        assertEquals(fruitsData.get(bananaKey).getQuantity(), bananaQuantity);
        assertEquals(fruitsData.get(appleKey).getQuantity(), appleQuantity);
    }

    @Test
    void writeReportToFile_Ok() {
        fruitTransaction.writeReportToFile(FILE_TO_WRITE_PATH);
        List<String> transactionsData = new ReaderServiceImpl().readFromFile(FILE_TO_WRITE_PATH);
        int resultReportDataSize = 3;
        assertEquals(resultReportDataSize, transactionsData.size());
    }
}
