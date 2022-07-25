package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterImplTest {
    private static final String FILENAME = "src/test/resources/testWriterFile.csv";

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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

        assertEquals("Wrote lines to file " + FILENAME + " don't equals to expected",
                expectedLines, actualLines);
    }

    @Test(expected = RuntimeException.class)
    public void writeFileExceptionTest_NotOk() {
        String invalidFile = FILENAME + "/%";
        String report = "fruit,quantity\n"
                + "cherry,47\n"
                + "banana,152\n"
                + "apple,110";
        outputCsvFile.writeToFile(invalidFile, report);
    }

    @Test
    public void exceptionMessageWriteToInvalidFileName_NotOk() {
        String invalidFile = FILENAME + "/%";
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Can't write to file " + invalidFile);
        String report = "fruit,quantity\n"
                + "cherry,47\n"
                + "banana,152\n"
                + "apple,110";
        outputCsvFile.writeToFile(invalidFile, report);
        throw new RuntimeException("Can't write to file " + invalidFile);
    }
}
