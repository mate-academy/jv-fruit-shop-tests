package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.model.exception.InvalidDataException;
import core.basesyntax.model.handler.OperationHandler;
import core.basesyntax.model.handler.impl.BalanceOperationHandler;
import core.basesyntax.model.handler.impl.PurchaseOperationHandler;
import core.basesyntax.model.handler.impl.ReturnOperationHandler;
import core.basesyntax.model.handler.impl.SupplyOperationHandler;
import core.basesyntax.model.service.OperatorService;
import core.basesyntax.model.service.ReaderService;
import core.basesyntax.model.service.ReportService;
import core.basesyntax.model.service.WriterService;
import core.basesyntax.model.service.impl.OperatorServiceImpl;
import core.basesyntax.model.service.impl.ReaderServiceImpl;
import core.basesyntax.model.service.impl.ReportServiceImpl;
import core.basesyntax.model.service.impl.WriterServiceImpl;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitShopTest {
    private static final String TEST_DATA = "src/main/resources/input.csv";
    private static final String REPORT = "src/main/resources/report.csv";
    private static final String EXPECTED1 = "src/main/resources/expectedreport1.csv";
    private static final String EXPECTED2 = "src/main/resources/expectedreport2.csv";
    private static final String EXPECTED3 = "src/main/resources/expectedreport3.csv";
    private static final FruitStorageDao FRUIT_STORAGE_DAO = new FruitStorageDaoImpl();

    private static final Map<Operation, OperationHandler> OPERATION_MAP = new HashMap<>();

    @BeforeAll
    static void setUp() {
        OPERATION_MAP.put(Operation.BALANCE, new BalanceOperationHandler(FRUIT_STORAGE_DAO));
        OPERATION_MAP.put(Operation.PURCHASE, new PurchaseOperationHandler(FRUIT_STORAGE_DAO));
        OPERATION_MAP.put(Operation.SUPPLY, new SupplyOperationHandler(FRUIT_STORAGE_DAO));
        OPERATION_MAP.put(Operation.RETURN, new ReturnOperationHandler(FRUIT_STORAGE_DAO));
    }

    @AfterEach
    void cleanUp() throws IOException {
        FruitStorage.FRUIT_STORAGE.clear();
        new FileWriter(REPORT).flush();
    }

    @Test
    public void testReadData_Ok() {
        ReaderService readerService = new ReaderServiceImpl();

        String expectedLines = "b,banana,20\n"
                + "b,apple,100";

        List<String> lines = readerService.readFile(TEST_DATA);

        assertEquals(expectedLines, lines.stream().collect(Collectors.joining("\n")));
    }

    @Test
    public void testGenerateEmptyReport_Ok() {
        ReportService reportService = new ReportServiceImpl(FRUIT_STORAGE_DAO);
        String expectedReport = "fruit,quantity\n";

        String report = reportService.createReport();

        assertEquals(expectedReport, report);
    }

    @Test
    public void testWriteEmptyReport_Ok() throws IOException {
        ReportService reportService = new ReportServiceImpl(FRUIT_STORAGE_DAO);
        WriterService writerService = new WriterServiceImpl();
        String report = reportService.createReport();
        writerService.writeReport(REPORT, report);

        String expectedLines = Files.lines(Paths.get(EXPECTED1))
                .collect(Collectors.joining());
        String lines = Files.lines(Paths.get(REPORT))
                .collect(Collectors.joining());

        assertEquals(expectedLines, lines);
    }

    @Test
    public void testProcessFruitTransactions_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(Operation.BALANCE,"banana", 100));
        list.add(new FruitTransaction(Operation.SUPPLY,"banana", 100));
        list.add(new FruitTransaction(Operation.PURCHASE,"banana", 200));
        list.add(new FruitTransaction(Operation.RETURN,"banana", 50));

        OperatorService operatorService = new OperatorServiceImpl(OPERATION_MAP);
        operatorService.operateTransactions(list);
        assertEquals(50, (int) FRUIT_STORAGE_DAO.getFruitQuantity("banana"));
    }

    @Test
    public void testGenerateReport_Ok() throws IOException {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(Operation.BALANCE,"banana", 100));
        list.add(new FruitTransaction(Operation.SUPPLY,"banana", 100));
        list.add(new FruitTransaction(Operation.PURCHASE,"banana", 200));
        list.add(new FruitTransaction(Operation.RETURN,"banana", 50));

        OperatorService operatorService = new OperatorServiceImpl(OPERATION_MAP);
        operatorService.operateTransactions(list);

        ReportService reportService = new ReportServiceImpl(FRUIT_STORAGE_DAO);
        String expectedReport = Files.lines(Paths.get(EXPECTED2))
                .collect(Collectors.joining("\n"));

        String report = reportService.createReport();

        assertEquals(expectedReport, report);
    }

    @Test
    public void testWriteReport_Ok() throws IOException {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(Operation.BALANCE,"banana", 100));
        list.add(new FruitTransaction(Operation.PURCHASE,"banana", 100));

        OperatorService operatorService = new OperatorServiceImpl(OPERATION_MAP);
        operatorService.operateTransactions(list);

        ReportService reportService = new ReportServiceImpl(FRUIT_STORAGE_DAO);
        WriterService writerService = new WriterServiceImpl();
        String report = reportService.createReport();
        writerService.writeReport(REPORT, report);

        String expectedLines = Files.lines(Paths.get(EXPECTED3))
                .collect(Collectors.joining("\n"));
        String lines = Files.lines(Paths.get(REPORT))
                .collect(Collectors.joining("\n"));

        assertEquals(expectedLines, lines);
    }

    @Test
    public void testPurchaseTooMuch_throwsException() throws IOException {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(Operation.BALANCE,"banana", 100));
        list.add(new FruitTransaction(Operation.PURCHASE,"banana", 101));

        OperatorService operatorService = new OperatorServiceImpl(OPERATION_MAP);
        Exception exception = assertThrows(InvalidDataException.class, () ->
                                                        operatorService.operateTransactions(list));

        String expectedMessage = "Not enough bananas in storage: 100. You want to purchase: 101.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testReadNullData_throwsException() {
        ReaderService readerService = new ReaderServiceImpl();
        Exception exception = assertThrows(InvalidDataException.class, () ->
                readerService.readFile(""));

        String expectedMessage = "Incorrect file path: ";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testWriteNullData_throwsException() {
        WriterService writerService = new WriterServiceImpl();
        Exception exception = assertThrows(InvalidDataException.class, () ->
                writerService.writeReport("", "report"));

        String expectedMessage = "Can't find file by path: ";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
