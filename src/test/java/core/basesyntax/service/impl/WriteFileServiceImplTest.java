package core.basesyntax.service.impl;

import static java.nio.file.Files.readString;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteFileServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/write_test_data";
    private static FileWriterService writeFileService;

    @BeforeClass
    public static void setUp() {
        writeFileService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validData_ok() throws IOException {
        String expected = "This string must be in write_test_data file";
        Path path = Path.of(VALID_FILE_PATH);
        writeFileService
                .writeToFile("This string must be in write_test_data file", path);
        try {
            String actual = readString(path);
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            throw new IOException("Invalid file path");
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_ok() {
        writeFileService.writeToFile("Some string", Path.of(null));
    }
}
