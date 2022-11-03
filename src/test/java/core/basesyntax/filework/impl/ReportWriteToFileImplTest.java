package core.basesyntax.filework.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.filework.ReportWriteToFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriteToFileImplTest {
    private static ReportWriteToFile reportWriteToFile;
    private static final String pathActual = "src/main/resources/testFile.csv";
    private static final String pathExpected = "src/main/resources/writeToFile.csv";
    private String report;

    @BeforeClass
    public static void beforeClass() {
        reportWriteToFile = new ReportWriteToFileImpl();
    }

    @Test
    public void reportWriteToFile_existedReport_ok() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        report = builder.toString();
        reportWriteToFile.writeToFile(report, pathActual);
        List<String> expected = Files.readAllLines(Path.of(pathExpected));
        List<String> actual = Files.readAllLines(Path.of(pathActual));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void reportWriteToFile_nonExistedReport_notOk() {
        reportWriteToFile.writeToFile(report, pathActual);
    }

    @Test(expected = RuntimeException.class)
    public void reportWriteToFile_InvalidPath_notOk() {
        String invalid = "src/main/resources/noFIle.csv";
        report = "some";
        reportWriteToFile.writeToFile(report, invalid);
    }
}
