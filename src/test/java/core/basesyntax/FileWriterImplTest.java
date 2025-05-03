package core.basesyntax;

import core.basesyntax.file.FileWriter;
import core.basesyntax.file.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE =
            "src/test/resourcesForTest/testReport";
    private static final String INVALID_PATH =
            "src/test/resourcesForTest/nonExistentDir/testReport.csv";
    private FileWriter fileWriter;

    @BeforeEach
    public void setup() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeReportInFile_ok() throws IOException {
        String testReport = "fruit,quantity" + System.lineSeparator()
                + "apple, 10"
                + System.lineSeparator()
                + "banana, 20";
        fileWriter.write(testReport,TEST_FILE);

        List<String> writtenData = Files.readAllLines(Path.of(TEST_FILE));

        Assert.assertEquals(3, writtenData.size());
        Assert.assertEquals("fruit,quantity", writtenData.get(0));
        Assert.assertEquals("apple, 10", writtenData.get(1));
        Assert.assertEquals("banana, 20", writtenData.get(2));
    }

    @Test
    public void writeInvalidPath_notOk() {
        String testReport = "fruit,quantity" + System.lineSeparator()
                + "apple, 10" + System.lineSeparator()
                + "banana, 20";
        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> {
            fileWriter.write(testReport, INVALID_PATH);
        });

        Assert.assertTrue(exception.getMessage()
                .contains("Failed to write to file " + INVALID_PATH));
    }
}
