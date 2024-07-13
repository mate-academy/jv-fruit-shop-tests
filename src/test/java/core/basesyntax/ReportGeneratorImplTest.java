package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.DataConverterImpl;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String FILE_TO_READ = "src/test/resources/reportToRead.csv";
    private static ReportGenerator generator;
    private static FileReader reader;
    private static ShopService service;
    private static DataConverter converter;

    @BeforeAll
    static void beforeAll() {
        FruitTransactionDao dao = new FruitTransactionDaoImpl();
        OperationStrategy strategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(dao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(dao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(dao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(dao)
        ));
        reader = new FileReaderImpl();
        generator = new ReportGeneratorImpl(dao);
        service = new ShopServiceImpl(strategy);
        converter = new DataConverterImpl();
    }

    @Test
    void generateReport_existentFile_Ok() {
        String expectedReport = "fruit, quantity\n"
                + "banana,152\n"
                + "apple,90";
        List<String> strings = reader.readLinesFromFile(FILE_TO_READ);
        List<FruitTransaction> transactions = converter.convertToTransaction(strings);
        service.process(transactions);
        String actualReport = generator.getReport();
        assertEquals(expectedReport, actualReport);
    }
}
