package core.basesyntax.service;

import static org.hamcrest.CoreMatchers.is;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String CORRECT_FILE_PATH = "src/main/resources/correct-file.Csv";
    private static final String INCORRECT_FILE_PATH = "src/java/resources/incorrect-file.Csv";
    private static final String CORRECT_FILE_DATA = "type,fruit,quantity";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
        Files.createFile(Path.of(CORRECT_FILE_PATH));
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Files.delete(Path.of(CORRECT_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void write_incorrectFilePath_notOk() {
        writerService.write(CORRECT_FILE_DATA, INCORRECT_FILE_PATH);
    }

    @Test
    public void write_nullData_notOk() {
        try {
            writerService.write(null, CORRECT_FILE_PATH);
            Assert.fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Data or path can't be null. Data: "
                    + null + ", path: " + CORRECT_FILE_PATH));
        }
    }

    @Test
    public void write_nullPath_notOk() {
        try {
            writerService.write(CORRECT_FILE_DATA, null);
            Assert.fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Data or path can't be null. Data: "
                    + CORRECT_FILE_DATA + ", path: " + null));
        }
    }

    @Test
    public void write_validValues_ok() {
        Assert.assertTrue(writerService.write(CORRECT_FILE_DATA, CORRECT_FILE_PATH));
    }
}
