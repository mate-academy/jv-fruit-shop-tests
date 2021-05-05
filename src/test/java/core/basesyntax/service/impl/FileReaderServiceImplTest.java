package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.interfaces.FileReaderService;
import java.nio.file.Path;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderServiceImplTest {
    private static final String NON_EXISTING_FILE =
            "src/test/resources/NonExistingTestFile.csv";
    private static final String VALID_INPUT_FILE = "src/test/resources/ValidInputFile.csv";
    private static final List<String> VALID_LIST = List.of("type,fruit,quantity");
    private static final String READ_EXCEPTION = "Can't read file";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    public void readLines_Ok() {
        Path validFilePath = Path.of(VALID_INPUT_FILE);
        List<String> actual = fileReaderService.readLines(validFilePath);
        assertEquals(VALID_LIST, actual);
    }

    @Test
    public void readLines_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(READ_EXCEPTION);
        Path invalidFilePath = Path.of(NON_EXISTING_FILE);
        fileReaderService.readLines(invalidFilePath);
    }
}
