package core.basesyntax.service.fileservice;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static String pathToTestReportFile;
    private static String invalidFilePath;
    private static String report;
    private static FileWriterService writerService;
    private static String separator = System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        pathToTestReportFile = "src/test/resources/testReport.csv";
        invalidFilePath = "";
        report = "fruit,quantity"
                + separator
                + "banana,352"
                + separator
                + "apple,90";
        writerService = new FileWriterServiceImpl();
    }

    @Test
    public void writeDataToFile_IsOk() {
        writerService.writeDataToFile(pathToTestReportFile,report);
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(pathToTestReportFile));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        String actualLines = String.join(separator, lines);
        assertEquals("There is error: read and expected data differ among themselves",
                report, actualLines);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_NotOk() {
        writerService.writeDataToFile(invalidFilePath, report);
    }
}
