package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.TestFruitStorageDaoImpl;
import core.basesyntax.db.TestStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.operation.BalanceOperation;
import core.basesyntax.service.impl.operation.OperationHandler;
import core.basesyntax.service.impl.operation.PurchaseOperation;
import core.basesyntax.service.impl.operation.ReturnOperation;
import core.basesyntax.service.impl.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFormatReportGeneratorTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/reportToRead.csv";
    private static TestFruitStorageDaoImpl fruitStorageDao;
    private static ReportGenerator csvFormatReportGenerator;
    private static FileReaderService fileReader;
    private static DataConverterService dataConverter;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> OPERATION_HANDLERS;
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new TestFruitStorageDaoImpl();
        csvFormatReportGenerator = new CsvFormatReportGenerator(fruitStorageDao);
        fileReader = new FileReaderServiceImpl();
        dataConverter = new DataConverterServiceImpl();
        OPERATION_HANDLERS = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitStorageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitStorageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(fruitStorageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitStorageDao));
        operationStrategy = new OperationStrategyImpl(OPERATION_HANDLERS);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        TestStorage.fruits.clear();
    }

    @Test
    void getReport_ok() {
        List<String> inputReport = fileReader.read(INPUT_FILE_PATH);
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        shopService.process(transactions);

        String expected = "fruit,quantity\n" + "banana,152\n" + "apple,90";
        String actual = csvFormatReportGenerator.getReport();

        assertEquals(expected, actual);
    }
}
