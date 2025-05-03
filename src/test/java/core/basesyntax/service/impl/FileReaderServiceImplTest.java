package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.EmptyFileException;
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
    private static final String EMPTY_FILE = "src/test/resources/EmptyFile.csv";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    public void readLines_validPath_Ok() {
        Path validFilePath = Path.of(VALID_INPUT_FILE);
        List<String> expected = List.of("type,fruit,quantity");
        List<String> actual = fileReaderService.readLines(validFilePath);
        assertEquals(expected, actual);
    }

    @Test
    public void readLines_invalidPath_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't read file");
        Path invalidFilePath = Path.of(NON_EXISTING_FILE);
        fileReaderService.readLines(invalidFilePath);
    }

    @Test
    public void readLines_emptyFile_notOk() {
        expectedException.expect(EmptyFileException.class);
        expectedException.expectMessage("is empty!");
        Path emptyFilePath = Path.of(EMPTY_FILE);
        fileReaderService.readLines(emptyFilePath);
    }
}
