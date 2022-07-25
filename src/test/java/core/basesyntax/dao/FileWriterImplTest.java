package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String FILENAME = "src/test/resources/testWriterFile.csv";
    private final core.basesyntax.dao.FileWriter outputCsvFile = new FileWriterImpl();

    @Test
    public void writeToFile_Ok() {
        Path filePath = Path.of(FILENAME);
        String expectedLines = "fruit,quantity\n"
                + "cherry,47\n"
                + "banana,152\n"
                + "apple,110";

        outputCsvFile.writeToFile(filePath.toString(), expectedLines);
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + FILENAME, e);
        }
        String actualLines = String.join("\n", lines);

        assertEquals(expectedLines, actualLines,
                "Wrote lines to file " + FILENAME + " don't equals to expected");
    }

    @Test
    public void writeFileExceptionTest_NotOk() {
        String invalidFile = FILENAME + "/%";
        String report = "fruit,quantity\n"
                + "cherry,47\n"
                + "banana,152\n"
                + "apple,110";

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                        outputCsvFile.writeToFile(invalidFile, report),
                "Exception is expecting");
        assertEquals("Can't write to file " + invalidFile, thrown.getMessage());
    }
}
