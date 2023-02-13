package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.Test;

public class FileWriterImplTest {
    private final FileReader fileReader = new FileReaderImpl();
    private final FileWriter fileWriter = new FileWriterImpl();
    private String report;
    private String filepath;

    @Test
    public void reportNull_NotOk() {
        filepath = "src/main/resources/OutputFile.csv";
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(null, filepath));
    }

    @Test
    public void filePathNull_NotOk() {
        report = "report";
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(report, null));
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(report, ""));
    }

    @Test
    public void writeToFile_Ok() {
        report = "report1";
        filepath = "src/main/resources/TestFile.csv";
        fileWriter.writeToFile(report, filepath);
        List<String> actual = fileReader.readFromFile(filepath);
        assertEquals(1, actual.size());
        assertEquals("report1", actual.get(0));
    }
}

