package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.serviceimpl.FileWriterImpl;
import core.basesyntax.serviceimpl.ReportGeneratorImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private FileWriter fileWriter;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void write_validFile_correct() throws Exception {
        final String testPath = "src/test/java/resources/testOutput.csv";
        Storage.add("pineapple", 40);
        Storage.add("apple", 30);
        String resultingReport = reportGenerator.getReport();

        fileWriter.write(resultingReport, testPath);

        Path path = Paths.get(testPath);
        String actual = Files.readString(path).trim();

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,30" + System.lineSeparator()
                + "pineapple,40";
        assertEquals(expected, actual);
    }

    @Test
    void write_invalidFile_throwsException() {
        String invalidFilePath = "src/test/java/resources/";
        RuntimeException thrown = assertThrows(
                RuntimeException.class, () -> fileWriter.write("banana, 20", invalidFilePath));
        assertTrue(thrown.getMessage().contains("Can't write file to path: " + invalidFilePath));
    }

    @AfterEach
    void clear() {
        Storage.fruits.clear();
    }
}
