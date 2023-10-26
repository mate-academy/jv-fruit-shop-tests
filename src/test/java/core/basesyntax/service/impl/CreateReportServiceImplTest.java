package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.DataConvertService;
import core.basesyntax.service.DataProcessService;
import core.basesyntax.service.ReadFromCsvFileService;
import java.util.Map;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateReportServiceImplTest {
    private static final String FIRST_FILENAME = "fruits1.csv";
    private static final String SECOND_FILENAME = "fruits2.csv";
    private static final String THIRD_FILENAME = "fruits3.csv";
    private static StorageDao storageDao;
    private static StringBuilder builder;
    private static CreateReportService reportCreator;
    private static DataConvertService dataConverter;
    private static ReadFromCsvFileService csvReader;
    private static DataProcessService dataProcessor;
    private static final Map<Operation, OperationHandler> operationPicker =
            Map.of(Operation.BALANCE, new BalanceOperationHandlerImpl(),
                    Operation.PURCHASE, new PurchaseOperationHandlerImpl(),
                    Operation.RETURN, new ReturnOperationHandlerImpl(),
                    Operation.SUPPLY, new SupplyOperationHandlerImpl());

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        dataConverter = new DataConvertServiceImpl();
        dataProcessor = new DataProcessServiceImpl(operationPicker);
    }

    @BeforeEach
    void setUp() {
        reportCreator = new CreateReportServiceImpl();
        csvReader = new ReadFromCsvFileServiceImpl();
        builder = new StringBuilder();
    }

    @Test
    void createReport_twoFruits_Ok() {
        dataProcessor.processFruits(
                dataConverter.convert(csvReader.readFile(FIRST_FILENAME)));
        builder.append("fruit,quantity");

        for (Map.Entry<String, Integer> entry : storageDao.getInfo().entrySet()) {
            builder.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(',')
                    .append(entry.getValue());
        }

        String expected = builder.toString();
        String actual = reportCreator.createReport();

        assertEquals(expected, actual);
    }

    @Test
    void createReport_threeFruits_Ok() {
        dataProcessor.processFruits(
                dataConverter.convert(csvReader.readFile(SECOND_FILENAME)));
        builder.append("fruit,quantity");

        for (Map.Entry<String, Integer> entry : storageDao.getInfo().entrySet()) {
            builder.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(',')
                    .append(entry.getValue());
        }

        String expected = builder.toString();
        String actual = reportCreator.createReport();

        assertEquals(expected, actual);
    }

    @Test
    void createReport_fourFruits_Ok() {
        dataProcessor.processFruits(
                dataConverter.convert(csvReader.readFile(THIRD_FILENAME)));
        builder.append("fruit,quantity");

        for (Map.Entry<String, Integer> entry : storageDao.getInfo().entrySet()) {
            builder.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(',')
                    .append(entry.getValue());
        }

        String expected = builder.toString();
        String actual = reportCreator.createReport();

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsCount.clear();
    }
}
