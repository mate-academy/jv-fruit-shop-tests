package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.util.List;
import org.junit.Test;

public class FileWriterTest {
    private static final String testTransactionFilePath = "src/main/resources/transaction.csv";
    private static final String dayReportTestFilePath = "src/main/resources/dayreport.csv";

    @Test
    public void fileWriter_isReportFileExist_OK() {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> testList = fileReaderService.readFromFile(testTransactionFilePath);
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(dayReportTestFilePath, testList);
        File reportFile = new File(dayReportTestFilePath);
        assertTrue(reportFile.exists());
    }
}
