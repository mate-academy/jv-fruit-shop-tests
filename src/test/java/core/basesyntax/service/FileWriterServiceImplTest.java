package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validFilePath_Ok() throws IOException {
        String validFilePath = this.getClass().getClassLoader()
                .getResource("FileWriterTestFile.csv").getPath();
        String expectedResult = "newTextForTest";
        fileWriterService.writeToFile(validFilePath, expectedResult);
        String actualResult = Files.readString(Path.of(validFilePath));
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_notOk() {
        String invalidFilePath = "/jv-fruit-shop-tests/src/test/resources/FileWriterTestFile.csv";
        fileWriterService.writeToFile(invalidFilePath, "invalidTestText");
    }

    @Test(expected = RuntimeException.class)
    public void read_correctExceptionMessage_ok() {
        String invalidFilePath = "/jv-fruit-shop-tests/src/test/resources/FileWriterTestFile.csv";
        fileWriterService.writeToFile(invalidFilePath, "invalidTestText");
    }
}
