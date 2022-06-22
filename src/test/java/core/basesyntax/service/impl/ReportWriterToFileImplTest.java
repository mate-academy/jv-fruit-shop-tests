package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportWriterToFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterToFileImplTest {
    private static final String FILE_VALID = "src/main/resources/TestReport.csv";
    private static ReportWriterToFile writeToFile;

    @BeforeClass
    public static void beforeClass() {
        writeToFile = new ReportWriterToFileImpl();
    }

    @Test
    public void createValidFile_Ok() {
        String data = "fruit,quantity" + System.lineSeparator() + "apple" + "," + "15";
        writeToFile.writeReportToFile(data, FILE_VALID);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("apple" + "," + "15");
        List<String> actual;
        try {
            actual = Files.readAllLines(Paths.get(FILE_VALID));
        } catch (IOException e) {
            throw new RuntimeException("Cant read file" + FILE_VALID, e);
        }
        assertEquals(expected, actual);
    }
}
