package core.basesyntax;

import static core.basesyntax.model.FruitTransaction.valueOfLabel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionMapper;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.Reader;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FruitTransactionMapperImpl;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.WriterImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitShopTest {
    private static final String REPORT_FILE_NAME = "src\\test\\resources\\Report file.csv";
    private static final String FILE_FOR_TEST = "src\\test\\resources\\File for test.csv";
    private static final String dataFromReader =
            "b,banana,20 b,apple,100 s,banana,100 p,banana,13 "
                    + "r,apple,10 p,apple,20 p,banana,5 s,banana,50 ";
    private static final Map<FruitTransaction.Operation, OperationHandler>
            operationHandlerMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler()
    );
    private static final OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationHandlerMap);
    private static FruitDao dao = new FruitDaoImpl();
    private static Reader reader = new FileReaderImpl();
    private static FruitTransactionMapper converter = new FruitTransactionMapperImpl();
    private static FruitTransactionService fruitTransactionService =
            new FruitTransactionServiceImpl(dao, operationStrategy);
    private static ReportCreator creator = new ReportCreatorImpl();
    private static Writer writer = new WriterImpl();
    private static String readerFileName_Ok;
    private static String getReaderFileName_Invalid;
    private static String dataForMapper_Ok;
    private static String dataForMapper_EmptyString;
    private static String dataForMapper_notEnoughParameters;
    private static FruitTransaction[] fruitTransactions_Ok;
    private static FruitTransaction[] fruitTransactions_Null;
    private static String reportFile_OK;
    private static String reportFile_Null;

    @BeforeAll
    static void beforeAll() {
        // Reader
        readerFileName_Ok = "src\\test\\resources\\During the day.csv";
        getReaderFileName_Invalid = "hello";

        // FruitTransactionMapper
        dataForMapper_Ok = "b,apple,100 s,banana,100";
        dataForMapper_EmptyString = "";
        dataForMapper_notEnoughParameters = "b,apple s,banana,100";

        // FruitTransactionService
        fruitTransactions_Ok = new FruitTransaction[] {
                new FruitTransaction(valueOfLabel("b"),"apple", 100),
                new FruitTransaction(valueOfLabel("s"),"banana", 100),
        };
        fruitTransactions_Null = null;

        // Writer
        reportFile_OK = "banana,100" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        reportFile_Null = null;
    }

    @Test
    void reader_Ok() {
        String data = reader.readFile(readerFileName_Ok);
        assertEquals(data, dataFromReader);
    }

    @Test
    void reader_NotOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFile(getReaderFileName_Invalid),
                "Can't find file by path: ");
    }

    @Test
    void converter_Ok() {
        FruitTransaction[] expectedArray = new FruitTransaction[] {
                new FruitTransaction(valueOfLabel("b"),"apple", 100),
                new FruitTransaction(valueOfLabel("s"),"banana", 100),
        };
        FruitTransaction[] actualArray = converter.buildFruitTransactions(dataForMapper_Ok);
        Boolean check = true;
        for (int i = 0; i < expectedArray.length; i++) {
            FruitTransaction actual = actualArray[i];
            FruitTransaction expected = expectedArray[i];
            if (actual.getOperation().equals(expected.getOperation())
                    || actual.getFruit().equals(expected.getFruit())
                    || actual.getQuantity() == expected.getQuantity()) {
                continue;
            }
            check = false;
            break;
        }
        assertTrue(check);
    }

    @Test
    void converterEmptyData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> converter.buildFruitTransactions(dataForMapper_EmptyString));
    }

    @Test
    void converterInvalidString_NotOk() {
        assertThrows(RuntimeException.class,
                () -> converter.buildFruitTransactions(dataForMapper_notEnoughParameters));
    }

    @Test
    void fruitTransactions_Ok() {
        fruitTransactionService.processData(fruitTransactions_Ok);
        assertEquals(2, Storage.fruits.size());
        Storage.fruits.clear();
    }

    @Test
    void fruitTransactionsNull_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fruitTransactionService.processData(fruitTransactions_Null));
    }

    @Test
    void creator_Ok() {
        fruitTransactionService.processData(fruitTransactions_Ok);
        String report = creator.createReport();
        assertEquals(reportFile_OK, report);
        Storage.fruits.clear();
    }

    @Test
    void writer_Ok() {
        writer.write(reportFile_OK, REPORT_FILE_NAME);
        String expected = reader.readFile(FILE_FOR_TEST);
        String actual = reader.readFile(REPORT_FILE_NAME);
        assertEquals(expected, actual);
        writer.write("", REPORT_FILE_NAME);
    }

    @Test
    void writerNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writer.write(reportFile_Null, REPORT_FILE_NAME));
    }
}
