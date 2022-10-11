package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileWriterServiceImplTest {

    @Test
    public void writeToFile_validFilePath_Ok() throws IOException {
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        String validFilePath =
                "/home/nata/Java/Projects/jv-fruit-shop-tests/src/test/resources/testFile.csv";
        String checkedText = "newTextForTest";
        fileWriterService.writeToFile(validFilePath, checkedText);

        assertEquals(checkedText, Files.readString(
                Path.of("/home/nata/Java/Projects/jv-fruit-shop-tests"
                        + "/src/test/resources/testFile.csv")));
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_notOk() {
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        String invalidFilePath =
                "/home/nata/Java/Projects/jv-fruit-shop-tets/src/test/resources/testFile.csv";
        fileWriterService.writeToFile(invalidFilePath, "invalidTestText");
    }

    @Test
    public void read_correctExceptionMessage_ok() {
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        String invalidFilePath =
                "/home/nata/Java/Projects/jv-fruit-shop-tets/src/test/resources/testFile.csv";
        try {
            fileWriterService.writeToFile(invalidFilePath, "invalidTestText");
            fail("Exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Can't write to file " + invalidFilePath, e.getMessage());
        }
    }
}
