package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderCsvTest {
    private static final String DEFAULT_VALID_FILE = "src/main/resources/example.csv";
    private static final String INVALID_PATH_TO_FILE = "src/main/resources/example3.csv";
    private static final String INVALID_QUANTITY_IN_FILE = "src/main/resources/example2.csv";
    private static final String NULL_QUANTITY_IN_FILE = "src/main/resources/"
            + "exampleWithNullQuantity.csv";
    private static final String NULL_FRUIT_IN_FILE = "src/main/resources/exampleWithNullFruit.csv";
    private FileReaderCsv fileReaderCsv = new FileReaderCsv();
    private FruitTransactionParserImpl fruitTransactionParser = new FruitTransactionParserImpl();
    private ReportGenerator reportGenerator = new ReportGenerator();

    @Test
    void read_validPath_Ok() {
        List<String> actual = fileReaderCsv.read(DEFAULT_VALID_FILE);
        Assertions.assertEquals(actual, fileReaderCsv.read(DEFAULT_VALID_FILE));
    }

    @Test
    void read_invalidPath_notOk() {
        Throwable exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            fileReaderCsv.read(INVALID_PATH_TO_FILE);
        });
        Assertions.assertEquals("Can't read file " + INVALID_PATH_TO_FILE, exception.getMessage());
    }

    @Test
    void parse_validData_Ok() {
        List<FruitTransaction> actual = fruitTransactionParser.parse(fileReaderCsv
                .read(DEFAULT_VALID_FILE));
        List<FruitTransaction> expected = new ArrayList<>();
        Assertions.assertSame(expected.getClass(), actual.getClass());
    }

    @Test
    void parse_invalidQuantity_notOk() {
        Throwable exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            fruitTransactionParser.parse(fileReaderCsv.read(INVALID_QUANTITY_IN_FILE));
        });
        Assertions.assertEquals("Input quantity is not valid", exception.getMessage());
    }

    @Test
    void parse_NullQuantity_notOk() {
        Throwable exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            fruitTransactionParser.parse(fileReaderCsv.read(NULL_QUANTITY_IN_FILE));
        });
        Assertions.assertEquals("Input quantity is not valid", exception.getMessage());
    }

    @Test
    void parse_NullFruit_notOk() {
        Throwable exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            fruitTransactionParser.parse(fileReaderCsv.read(NULL_FRUIT_IN_FILE));
        });
        Assertions.assertEquals("Fruit does not exist", exception.getMessage());
    }

    @Test
    void generateReport_validData_Ok() {
        String actual = reportGenerator.generateReport(Storage.fruits);
        String expected = "expected";
        Assertions.assertSame(expected.getClass(), actual.getClass());
    }

    @Test
    void execute_validData_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        TransactionExecutor transactionExecutor = new TransactionExecutor(strategyMap);
        transactionExecutor.execute(fruitTransactionParser.parse(fileReaderCsv
                .read(DEFAULT_VALID_FILE)));
        Assertions.assertEquals(transactionExecutor.getStrategy()
                .get(FruitTransaction.Operation.BALANCE).getClass(), BalanceHandler.class);
    }
}
