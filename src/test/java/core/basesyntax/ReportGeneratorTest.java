package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.TransactionExecutorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private final TransactionExecutor transactionExecutor = new TransactionExecutorImpl();
    private final FileReader fileReader = new FileReaderImpl();
    private final TransactionParser transactionParser = new TransactionParserImpl();
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private final FruitsDao fruitsDao = new FruitsDaoImpl();

    @AfterEach
    void tearDown() {
        fruitsDao.storageAccess().clear();
    }

    @Test
    void generate_Report_isOk() {
        File inputDataFile = new File("src/test/resources/isOkGenerateReportFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(inputDataFile);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        Map<String, Integer> fruitsDataStorage = transactionExecutor.executeAll(fruits);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "orange,50" + System.lineSeparator()
                + "apple,200" + System.lineSeparator()
                + "kiwi,150" + System.lineSeparator();
        String actual = reportGenerator.generateReport(fruitsDataStorage);
        assertEquals(expected, actual);
    }
}
