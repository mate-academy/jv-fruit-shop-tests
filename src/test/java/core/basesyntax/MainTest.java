package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.db.ReportServiceImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.db.service.ReportService;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.BalanceTransactionHandler;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.PurchaseTransactionHandler;
import core.basesyntax.service.impl.ReturnTransactionHandler;
import core.basesyntax.service.impl.SupplyTransactionHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private static final String DATA_SOURCE_PATH =
            "src/main/java/core/basesyntax/resources/data.csv";
    private static final String REPORT_PATH =
            "src/main/java/core/basesyntax/resources/report.csv";
    private static final String EXPECTED_PATH =
            "src/test/resources/expected.csv";

    @Test
    void mainTest_ok() {
        Map<String, TransactionHandler> operationMap = Map.of(
                Operation.BALANCE.getCode(), new BalanceTransactionHandler(),
                Operation.SUPPLY.getCode(), new SupplyTransactionHandler(),
                Operation.PURCHASE.getCode(), new PurchaseTransactionHandler(),
                Operation.RETURN.getCode(), new ReturnTransactionHandler());

        FileReader reader = new FileReaderImpl();
        FileWriter writer = new FileWriterImpl();
        TransactionParser parser = new ParserImpl();
        OperationStrategy operationStrategy = new OperationStrategy(operationMap);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);
        ReportService reportService = new ReportServiceImpl();
        List<String> dataFromFile = reader.readData(DATA_SOURCE_PATH);
        List<FruitTransaction> parsedTransactions = parser.parseAll(dataFromFile);
        fruitShopService.processTransaction(parsedTransactions);
        String report = reportService.generate();
        writer.writeToFile(report, REPORT_PATH);
        assertNotNull(reader);
        assertNotNull(writer);
        assertNotNull(parser);
        assertNotNull(operationStrategy);
        assertNotNull(fruitShopService);
        assertNotNull(reportService);
        List<String> expected = reader.readData(EXPECTED_PATH);
        List<String> actual = reader.readData(REPORT_PATH);
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
