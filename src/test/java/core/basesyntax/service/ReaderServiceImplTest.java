package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void read_validFile_Ok() throws IOException {
        Path tempFile = Files.createTempFile(TEST_RESOURCES_PATH + "test", ".csv");
        List<String> result = readerService.read(tempFile.toString());
        assertNotNull("Result should not be null", result);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistentFile_notOk() {
        String nonExistentFileName = "non_existed.txt";
        readerService.read(nonExistentFileName);
    }

    @Test
    public void read_emptyFile_notOk() throws IOException {
        Path emptyFile = Files.createTempFile(TEST_RESOURCES_PATH + "empty", ".csv");
        List<String> result = readerService.read(emptyFile.toString());
        assertNotNull("Result should not be null", result);
        assertEquals("Result should be an empty list", 0, result.size());
    }

    @Test(expected = NullPointerException.class)
    public void read_nullParameter_notOk() {
        readerService.read(null);
    }
}
