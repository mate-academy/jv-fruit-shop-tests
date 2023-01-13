package core.basesyntax.service.impl;

import core.basesyntax.service.FileReadService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReadServiceImplTest {

    private static final String INPUT_PATH = "src/test/resources/file_read_service_test.csv";
    private static final String OUTPUT_PATH = "src/test/resources/file_writer_service_test.csv";
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private final FileReadService fileReadService = new FileReadServiceImpl();

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can`t correctly clear result file after test ", e);
        }
    }

    @Test
    public void fileReadService_ok() {
        String actual = fileReadService.readFromFile(INPUT_PATH);
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,apple,40" + System.lineSeparator() + "b,banana,10";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fileReadService_notOk() {
        String invalidPath = "invalid path";
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Can`t read file " + invalidPath);
        fileReadService.readFromFile(invalidPath);

    }
}
