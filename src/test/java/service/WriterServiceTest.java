package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.WriterServiceImpl;

public class WriterServiceTest {
    private static final String FILE_NAME_OK = "src/main/resources/tofiletest.csv";
    private static final String LANE_TO_WRITE = "JUnit test so trash";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_ok() {
        writerService.writeToFile(LANE_TO_WRITE, FILE_NAME_OK);
        try {
            assertEquals(LANE_TO_WRITE, Files.readAllLines(Path.of(FILE_NAME_OK)).get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void write_emptyInput_ok() {
        writerService.writeToFile("", FILE_NAME_OK);
        try {
            assertTrue(Files.readAllLines(Path.of(FILE_NAME_OK)).isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_null_arguments_notOk() {
        writerService.writeToFile(null,null);
    }

    @Test(expected = RuntimeException.class)
    public void write_first_argumentNull_notOk() {
        writerService.writeToFile(null, FILE_NAME_OK);
    }

    @Test(expected = RuntimeException.class)
    public void write_second_argumentNull_notOk() {
        writerService.writeToFile(LANE_TO_WRITE, null);
    }
}
