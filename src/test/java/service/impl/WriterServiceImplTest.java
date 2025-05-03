package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import service.WriterService;

public class WriterServiceImplTest {
    private static final String REPORT_FOR_TEST = new StringBuilder()
            .append("fruit,quantity")
            .append(System.lineSeparator())
            .append("banana,152")
            .append(System.lineSeparator())
            .append("apple,90")
            .toString();
    private static final String FILE_TO_WRITE_FOR_TEST = "src/test/resources/output-test.csv";
    private static final String WRONG_FILE = "";
    private static WriterService writerService;
    private static final List<String> expected =
            List.of("fruit,quantity","banana,152","apple,90");

    @Before
    public void setUp() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_ToFileSuccessful_Ok() {
        writerService.write(REPORT_FOR_TEST, FILE_TO_WRITE_FOR_TEST);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(FILE_TO_WRITE_FOR_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file");
        }
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void write_ToNullFile_NotOk() {
        writerService.write(REPORT_FOR_TEST, null);
        fail(NullPointerException.class.getName());
    }

    @Test(expected = NullPointerException.class)
    public void write_NullWrongFile_NotOk() {
        writerService.write(null, FILE_TO_WRITE_FOR_TEST);
        fail(NullPointerException.class.getName());
    }

    @Test(expected = RuntimeException.class)
    public void write_ToWrongFile_NotOk() {
        writerService.write(REPORT_FOR_TEST, WRONG_FILE);
        fail(RuntimeException.class.getName());
    }
}
