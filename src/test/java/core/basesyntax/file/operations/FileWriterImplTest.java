package core.basesyntax.file.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String CORRECT_PATH = "src/test/java/resources/testReport.csv";
    private static final String INCORRECT_PATH = "src/test/java/resources/incorrectPath.csv";
    private ReportFileWriter reportFileWriter;

    @BeforeEach
    void setUp() {
        reportFileWriter = new FileWriterImpl();
    }

    @Test
    void writeToFile_Ok() throws IOException {
        String report = "type,fruit"
                + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator();
        reportFileWriter.writeToFile(CORRECT_PATH, report);

        assertEquals(report, Files.readString(Path.of(CORRECT_PATH)));
    }

    @Test
    void writeToFile_incorrectPath() throws IOException {
        String report = "type,fruit"
                + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator();
        reportFileWriter.writeToFile(INCORRECT_PATH, report);

        assertEquals(report, Files.readString(Path.of(INCORRECT_PATH)));
    }
}
