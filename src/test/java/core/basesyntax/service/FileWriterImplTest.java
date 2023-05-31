package core.basesyntax.service;

import static java.io.File.separator;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static String pathToTestReportFile;
    private static String invalidFilePath;
    private static String report;
    private static FileWriter testReportFile;

    @BeforeClass
    public static void beforeClass() {
        pathToTestReportFile = "src"
                + separator + "test"
                + separator + "resources"
                + separator + "testReportFile.csv";
        invalidFilePath = "";
        report = "fruit,quantity\n"
                + "banana,352\n"
                + "apple,90";
        testReportFile = new FileWriterImpl();
    }

    @Test
    public void writeToFile_Ok() {
        testReportFile.writeToFile(pathToTestReportFile, report);
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(pathToTestReportFile));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file.", e);
        }
        String actualLines = String.join("\n", lines);
        assertEquals("There is error: read and expected data differ among themselves",
                report, actualLines);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_NotOk() {
        testReportFile.writeToFile(invalidFilePath, report);
    }
}
