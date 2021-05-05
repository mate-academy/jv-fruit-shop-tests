package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.interfaces.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterServiceImplTest {
    private static final String INVALID_PATH = "/invalidPath/invalidFileName";
    private static final String VALID_FILEPATH = "src/test/resources/ValidOutputFile.csv";
    private static final String WRITE_EXCEPTION = "Can't write to a file";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void writeData_Ok() throws IOException {
        Path filePath = Path.of(VALID_FILEPATH);
        String expectedData = "1, 2, 3" + System.lineSeparator()
                + "4, 5, 6" + System.lineSeparator()
                + "7, 8, 9";
        fileWriterService.writeData(expectedData, VALID_FILEPATH);
        List<String> lines = Files.readAllLines(filePath);
        String actualData = String.join(System.lineSeparator(), lines);
        assertEquals(expectedData, actualData);
    }

    @Test
    public void writeData_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(WRITE_EXCEPTION);
        fileWriterService.writeData("", INVALID_PATH);
    }
}
