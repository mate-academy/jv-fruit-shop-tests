package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.TransactionExecutorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private final TransactionExecutor transactionExecutor = new TransactionExecutorImpl();
    private final FileReader fileReader = new FileReaderImpl();
    private final FileWriter fileWriter = new FileWriterImpl();
    private final TransactionParser transactionParser = new TransactionParserImpl();
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private final FruitsDao fruitsDao = new FruitsDaoImpl();

    @AfterEach
    void tearDown() {
        fruitsDao.storageAccess().clear();
    }

    @Test
    void write_File_isOk() {
        File inputDataFile = new File("src/test/resources/isOkWriteFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(inputDataFile);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        Map<String, Integer> fruitsDataStorage = transactionExecutor.executeAll(fruits);
        String report = reportGenerator.generateReport(fruitsDataStorage);
        fileWriter.writeToFile(report);
        File result = new File("src/main/resources/result.csv");
        List<String> fruitsList = fileReader.readFile(result);
        StringBuilder builder = new StringBuilder();
        for (String fruit: fruitsList) {
            builder.append(fruit).append(System.lineSeparator());
        }
        String actual = builder.toString();
        assertEquals(report, actual);
    }

    @Test
    void write_File_NotOk() {
        String report = null;
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(report));
    }
}
