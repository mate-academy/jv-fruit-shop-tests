package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private FileReader fileReader;
    private FileWriter fileWriter;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_NullReport_NotOk() {
        String filepath = "src/main/resources/OutputFile.csv";
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(null, filepath));
    }

    @Test
    public void writeToFile_FilePathNull_NotOk() {
        String report = "report";
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(report, null));
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(report, ""));
    }

    @Test
    public void writeToFile_ValidDataValidFilePath_Ok() {
        String report = "report1";
        String filepath = "src/main/resources/TestFile.csv";
        fileWriter.writeToFile(report, filepath);
        List<String> actual = fileReader.readFromFile(filepath);
        assertEquals(1, actual.size());
        assertEquals("report1", actual.get(0));
    }

    @AfterClass
    public static void afterClass() throws IOException {
        String filepath = "src/main/resources/TestFile.csv";
        Files.delete(Path.of(filepath));
    }
}

