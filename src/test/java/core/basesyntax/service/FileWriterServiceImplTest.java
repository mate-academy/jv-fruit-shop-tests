package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void writeToFile_validFilePath_Ok() throws IOException {
        String validFilePath = this.getClass().getClassLoader()
                .getResource("FileWriterTestFile.csv").getPath();
        String checkedText = "newTextForTest";
        fileWriterService.writeToFile(validFilePath, checkedText);

        assertEquals(checkedText, Files.readString(Path.of(validFilePath)));
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_notOk() {
        String invalidFilePath = "/jv-fruit-shop-tests/src/test/resources/FileWriterTestFile.csv";
        fileWriterService.writeToFile(invalidFilePath, "invalidTestText");
    }

    @Test
    public void read_correctExceptionMessage_ok() {
        String invalidFilePath = "/jv-fruit-shop-tests/src/test/resources/FileWriterTestFile.csv";
        try {
            fileWriterService.writeToFile(invalidFilePath, "invalidTestText");
            fail("Exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Can't write to file " + invalidFilePath, e.getMessage());
        }
    }
}
