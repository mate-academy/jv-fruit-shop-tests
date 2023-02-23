package core.basesyntax.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
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

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @After
    public void clearResults() throws Exception {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    public void readFile_getListOfStringsFromValidFile_Ok() throws IOException {
        //arrange
        File file = new File(TEST_FILE_PATH);
        Files.write(file.toPath(), List.of(VALID_INPUT_DATA));

        //act
        List<String> actual = fileReaderService.readFile(file.getPath());

        //assert
        assertEquals("The list size from file is incorrect:",
                EXPECTED_RESULT.size(), actual.size());
        assertArrayEquals("The list from file is incorrect:",
                EXPECTED_RESULT.toArray(), actual.toArray());
    }

    @Test(expected = RuntimeException.class)
    public void readFile_readInvalidFilePath_NotOk() {
        //act
        fileReaderService.readFile(INVALID_FILE_PATH);

        //assert
        fail("Expected the file does not exist.");
    }
}
