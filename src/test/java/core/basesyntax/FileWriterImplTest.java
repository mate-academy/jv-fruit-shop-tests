package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter = new FileWriterImpl();

    @BeforeAll
    public static void initialize() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeFile_validContent_Ok() throws IOException {
        String finalReportPath = "src/test/resources/finalReport.csv";
        fileWriter.writeFile("fruit,quantity", finalReportPath);
        Path path = Path.of(finalReportPath);
        assertTrue(Files.exists(path));
        String actual = Files.readString(path);
        assertEquals("fruit,quantity", actual);
    }

    @Test
    public void writeFile_emptyContent_Ok() throws IOException {
        String finalReportPath2 = "src/test/resources/finalReport2.csv";
        fileWriter.writeFile("", finalReportPath2);
        Path path = Path.of(finalReportPath2);
        assertTrue(Files.exists(path));

        String actual = Files.readString(path);
        assertEquals("", actual);
    }

    @Test
    public void writeFile_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.writeFile("fruit,quantity",
                "src/test/resources/missing-folder/finalReport.csv"));
    }
}
