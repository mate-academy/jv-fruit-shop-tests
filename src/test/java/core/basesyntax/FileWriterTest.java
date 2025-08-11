package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.writer.FileWriter;
import core.basesyntax.writer.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private FileWriter fileWriter;

    @BeforeEach
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeValidDataFileContainsThatData_Ok() throws IOException {
        Path tempFile = Files.createTempFile("testReport", ".txt");

        String report = "Apple,10\nBanana,5";
        fileWriter.write(report, tempFile.toString());

        String fileContent = Files.readString(tempFile);
        assertEquals(report, fileContent);
    }

    @Test
    public void writeInvalidPath_throwsException() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("Some data", "/invalid-path/does-not-exist/report.txt"));
    }

}
