package core.basesyntax;

import static core.basesyntax.Main.OUTPUT_PATH;
import static core.basesyntax.Main.START_LINE;
import static core.basesyntax.service.impl.ReportCreator.getReportHeader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.Reader;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.ReaderImpl;
import core.basesyntax.service.impl.ReportCreator;
import core.basesyntax.service.impl.WriterImpl;
import core.basesyntax.strategy.BalanceTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseTransaction;
import core.basesyntax.strategy.ReturnTransaction;
import core.basesyntax.strategy.SupplyTransaction;
import core.basesyntax.strategy.Transaction;
import core.basesyntax.strategy.TransactionHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private OperationStrategy operationStrategy;
    private TransactionHandler transactionHandler;

    @BeforeEach
    void setUp() {
        Storage.getFruits().clear();
        Map<String, Transaction> transactionMap = Map.of(
                "b", new BalanceTransaction(),
                "s", new SupplyTransaction(),
                "p", new PurchaseTransaction(),
                "r", new ReturnTransaction()
        );
        operationStrategy = new OperationStrategyImpl(transactionMap);
        transactionHandler = new TransactionHandler(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_PATH));
        } catch (IOException e) {
            System.out.println("Could not delete file: " + e.getMessage());
        }
    }

    @Test
    void transaction_improperOperation_notOk() {
        String line = "x,banana,10";
        assertThrows(IllegalArgumentException.class, () -> transactionHandler.process(line));
    }

    @Test
    void transaction_inputNotEnoughArguments_notOk() {
        String line = "b, banana";
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> transactionHandler.process(line));
    }

    @Test
    void transaction_inputTooManyArguments_notOk() {
        String line = "b, banana, 10, extra";
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> transactionHandler.process(line));
    }

    @Test
    void transaction_nullInput_notOk() {
        assertThrows(IllegalArgumentException.class, () -> transactionHandler.process(null));
    }

    @Test
    void transaction_purchaseMoreThanAvailable_notOk() {
        List<String> lines = List.of(
                "b,banana,10",
                "p,banana,15"
        );
        assertThrows(RuntimeException.class, () -> {
            for (String line : lines) {
                transactionHandler.process(line);
            }
        });
    }

    @Test
    void transaction_reportCreatedSuccessfully_Ok() {
        List<String> lines = List.of(
                "b,banana,10",
                "p,banana,5"
        );
        for (String line : lines) {
            transactionHandler.process(line);
        }

        Writer writer = new WriterImpl();
        String report = ReportCreator.createReport();
        writer.writeToFile(OUTPUT_PATH, report);

        Reader reader = new ReaderImpl();
        ArrayList<String> resultLines = reader.readFromFile(OUTPUT_PATH);

        assertFalse(resultLines.isEmpty(), "Report file should not be empty");

        boolean foundExpectedLine = false;
        for (String resultLine : resultLines) {
            if (!resultLine.equals(getReportHeader())) {
                assertEquals("banana,5", resultLine);
                foundExpectedLine = true;
            }
        }
        assertTrue(foundExpectedLine, "Expected data line was never found in report");
    }

    @Test
    void transaction_allOperationsFunction_Ok() {
        Reader reader = new ReaderImpl();
        ArrayList<String> lines = new ArrayList<>(List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"));
        boolean balanceTransactionPresent = false;
        boolean purchaseTransactionPresent = false;
        boolean returnTransactionPresent = false;
        boolean supplyTransactionPresent = false;
        for (String line : lines) {
            if (line.equals(START_LINE)) {
                continue;
            }
            if (line.charAt(0) == 'b') {
                balanceTransactionPresent = true;
            }
            if (line.charAt(0) == 'p') {
                purchaseTransactionPresent = true;
            }
            if (line.charAt(0) == 'r') {
                returnTransactionPresent = true;
            }
            if (line.charAt(0) == 's') {
                supplyTransactionPresent = true;
            }
            transactionHandler.process(line);
        }
        assertTrue(balanceTransactionPresent
                && purchaseTransactionPresent
                && returnTransactionPresent
                && supplyTransactionPresent,
                "All transaction types are not present in input file");
        Writer writer = new WriterImpl();
        String report = ReportCreator.createReport();
        writer.writeToFile(OUTPUT_PATH, report);
        Reader resultReader = new ReaderImpl();
        ArrayList<String> resultLines = resultReader.readFromFile(OUTPUT_PATH);
        assertEquals("fruit,quantity", resultLines.get(0));
        assertEquals("banana,152", resultLines.get(1));
        assertEquals("apple,90", resultLines.get(2));
    }
}
