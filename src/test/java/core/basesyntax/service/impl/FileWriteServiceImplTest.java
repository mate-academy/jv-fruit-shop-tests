package core.basesyntax.service.impl;

import core.basesyntax.service.FileReadService;
import core.basesyntax.service.FileWriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriteServiceImplTest {
    private static final String INPUT_PATH = "src/test/resources/file_read_service_test.csv";
    private static final String OUTPUT_PATH = "src/test/resources/file_writer_service_test.csv";
    private static FileReadService fileReadService;
    private static FileWriteService fileWriteService;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileWriteService = new FileWriteServiceImpl();
        fileReadService = new FileReadServiceImpl();
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can`t correctly clear result file after test ", e);
        }
    }

    @Test
    public void writeFileService_ok() {
        String data = fileReadService.readFromFile(INPUT_PATH);
        fileWriteService.writeToFile(OUTPUT_PATH, data);
        String actual = fileReadService.readFromFile(OUTPUT_PATH);
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,apple,40" + System.lineSeparator() + "b,banana,10";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeFileService_nullPath_notOk() {
        String data = fileReadService.readFromFile(INPUT_PATH);
        expectedEx.expect(NullPointerException.class);
        fileWriteService.writeToFile(null, data);
    }
}
