package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final File TEST_WRITE_TO_FILE
            = new File("src/test/resources/fileForWriterTest.csv");
    private static final File INVALID_PATH = new File("stepan bandera");
    private static final String INPUT = "fruit,quantity" + System.lineSeparator()
            + "durian,72" + System.lineSeparator()
            + "papaya,100";
    private static FileWriterService writerService;
    private List<String> excepted;

    @BeforeClass
    public static void beforeClass() {
        writerService = new FileWriterServiceImpl();
    }

    @Before
    public void createInputList() {
        excepted = new ArrayList<>();
        excepted.add("fruit,quantity");
        excepted.add("durian,72");
        excepted.add("papaya,100");
    }

    @Test
    public void write_validInputData_ok() {
        writerService.write(TEST_WRITE_TO_FILE, INPUT);
        List<String> actual;
        try {
            actual = Files.readAllLines(TEST_WRITE_TO_FILE.toPath());
        } catch (IOException e) {
            throw new RuntimeException("File doesn't exist! Or maybe some another problem)");
        }
        Assert.assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPathToFile_notOk() {
        writerService.write(INVALID_PATH, INPUT);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullInputData_notOk() {
        writerService.write(null, INPUT);
    }
}
