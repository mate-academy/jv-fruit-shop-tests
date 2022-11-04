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

    @BeforeClass
    public static void beforeClass() {
        pathToTestReportFile = "src/main/resources/testReport.csv";
        invalidFilePath = "";
        report = "fruit,quantity\n"
                + "banana,352\n"
                + "apple,90";
        writerService = new FileWriterServiceImpl();
    }

    @Test
    public void writeDataToFileIs_Ok() {
        writerService.writeDataToFile(pathToTestReportFile,report);
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(pathToTestReportFile));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        String actualLines = String.join("\n", lines);
        assertEquals("There is error: read and expected data differ among themselves",
                report, actualLines);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_NotOk() {
        writerService.writeDataToFile(invalidFilePath, report);
    }
}
