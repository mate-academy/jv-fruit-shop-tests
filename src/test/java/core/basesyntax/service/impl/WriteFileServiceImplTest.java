package core.basesyntax.service.impl;

import core.basesyntax.service.WriteFileService;
import static java.nio.file.Files.readString;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

public class WriteFileServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/write_test_data";
    private static WriteFileService writeFileService;

    @BeforeClass
    public static void setUp() {
        writeFileService = new WriteFileServiceImpl();
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

}