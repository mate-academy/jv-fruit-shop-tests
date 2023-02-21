package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;
    private static final String TEST_FILE_PATH = "transactions.csv";
    private static final String INVALID_FILE_PATH = "/invalid_path/fruit";
    private static final String[] VALID_INPUT_DATA =
            {"type,fruit,quantity",
                    "b,banana,20",
                    "b,apple,100",
                    "s,banana,100",
                    "p,banana,13"};
    private static final List<String> EXPECTED_RESULT =
            List.of("type,fruit,quantity",
                    "b,banana,20",
                    "b,apple,100",
                    "s,banana,100",
                    "p,banana,13");

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @After
    public void clearResults() throws Exception {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    public void readFile_getListOfStringsFromValidFile_Ok() throws IOException {
        File file = new File(TEST_FILE_PATH);
        Files.write(file.toPath(), List.of(VALID_INPUT_DATA));

        List<String> actual = fileReaderService.readFile(file.getPath());
        assertEquals("Test failed! The method must return the input file's correct List<String>.",
                EXPECTED_RESULT, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_readInvalidFilePath_NotOk() {
        fileReaderService.readFile(INVALID_FILE_PATH);
        fail("Test failed! The method must throw " + RuntimeException.class
                + " if the file does not exist");
    }
}
