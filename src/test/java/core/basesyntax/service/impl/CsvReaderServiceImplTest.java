package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvReaderServiceImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/testFileInput.csv";
    private static final List<String> EXPECTED_LIST = List.of("type,fruit,quantity", "b,banana,20");
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final CsvReaderServiceImpl readerService = new CsvReaderServiceImpl();

    @Test
    public void getDataIncorrectPath_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(("Can't read from file by path: "));
        String inputSourcePath = "test";
        readerService.readFile(inputSourcePath);
    }

    @Test
    public void readFile_Ok() {
        List<String> actual = readerService.readFile(TEST_FILE_PATH);
        assertEquals(EXPECTED_LIST, actual);
    }
}
