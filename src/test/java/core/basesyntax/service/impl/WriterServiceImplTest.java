package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String FILE_PATH = "src/test/resources/output";
    private static final String SEPARATOR = System.lineSeparator();
    private static final String EMPTY_REPORT = "";
    private static final String VALID_REPORT = "fruit,quantity" + SEPARATOR
            + "banana,152" + SEPARATOR + "apple,90";

    @BeforeClass
    public static void setUpBeforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_reportIsNull_notOk() {
        writerService.write(null, FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void write_filePathIsNull_notOk() {
        writerService.write(VALID_REPORT, null);
    }

    @Test
    public void write_emptyReport_ok() throws IOException {
        writerService.write(EMPTY_REPORT, FILE_PATH);
        List<String> expected = List.of();
        List<String> actual = Files.readAllLines(Path.of(FILE_PATH));
        assertEquals(expected, actual);
    }

    @Test
    public void write_ValidReport_ok() throws IOException {
        writerService.write(VALID_REPORT, FILE_PATH);
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90");
        List<String> actual = Files.readAllLines(Path.of(FILE_PATH));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }
}
