package core.basesyntax.strategy;

import static core.basesyntax.Main.OUTPUT_PATH;
import static core.basesyntax.Main.START_LINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.Reader;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.ReaderImpl;
import core.basesyntax.service.impl.ReportCreator;
import core.basesyntax.service.impl.WriterImpl;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionHandlerTest {
    private boolean balanceTransactionPresent;
    private boolean purchaseTransactionPresent;
    private boolean returnTransactionPresent;
    private boolean supplyTransactionPresent;
    private final Reader reader = new ReaderImpl();
    private final Writer writer = new WriterImpl();
    private OperationStrategy operationStrategy;
    private TransactionHandler transactionHandler;
    private String path;

    @BeforeEach
    void setUp() {
        Map<String, Transaction> transactionMap = Map.of(
                "b", new BalanceTransaction(),
                "s", new SupplyTransaction(),
                "p", new PurchaseTransaction(),
                "r", new ReturnTransaction()
        );
        operationStrategy = new OperationStrategyImpl(transactionMap);
        transactionHandler = new TransactionHandler(operationStrategy);
        boolean balanceTransactionPresent = false;
        boolean purchaseTransactionPresent = false;
        boolean returnTransactionPresent = false;
        boolean supplyTransactionPresent = false;
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void process_inputAllTransactions_ok() {
        path = "src/main/resources/testOk.csv";
        ArrayList<String> lines = reader.readFromFile(path);
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
        String report = ReportCreator.createReport();
        writer.writeToFile(OUTPUT_PATH, report);
        Reader resultReader = new ReaderImpl();
        ArrayList<String> resultLines = resultReader.readFromFile(OUTPUT_PATH);
        assertEquals("fruit,quantity", resultLines.get(0));
        assertEquals("banana,152", resultLines.get(1));
        assertEquals("apple,90", resultLines.get(2));
    }

    @Test
    void process_inputMissingElement_notOk() {
        path = "src/main/resources/testMissingElement.csv";
        ArrayList<String> lines = reader.readFromFile(path);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            for (String line : lines) {
                if (line.equals(START_LINE)) {
                    continue;
                }
                transactionHandler.process(line);
            }
        });
    }

    @Test
    void process_tooManyElements_notOk() {
        path = "src/main/resources/testTooManyElements.csv";
        ArrayList<String> lines = reader.readFromFile(path);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            for (String line : lines) {
                if (line.equals(START_LINE)) {
                    continue;
                }
                transactionHandler.process(line);
            }
        });
    }

    @Test
    void process_incorrectAmount_notOk() {
        path = "src/main/resources/testIncorrectAmount.csv";
        ArrayList<String> lines = reader.readFromFile(path);
        assertThrows(IllegalArgumentException.class, () -> {
            for (String line : lines) {
                if (line.equals(START_LINE)) {
                    continue;
                }
                transactionHandler.process(line);
            }
        });
    }
}
