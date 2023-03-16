package core.basesyntax;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final File TEST_WRITE_TO_FILE
            = new File("src/test/resources/fileForWriterTest.csv");
    private static final File INVALID_PATH = new File("stepan bandera");
    private static final String INPUT = "fruit,quantity\n"
            + "durian,72\n"
            + "papaya,100";
    private static List<String> excepted;

    @BeforeClass
    public static void createInputList() {
        excepted = new ArrayList<>();
        excepted.add("fruit,quantity");
        excepted.add("durian,72");
        excepted.add("papaya,100");
    }

    @Test
    public void write_validInputData_ok() {
        FileWriterService writerService = new FileWriterServiceImpl();
        writerService.write(TEST_WRITE_TO_FILE, INPUT);
        List<String> actual;
        try {
            actual = Files.readAllLines(TEST_WRITE_TO_FILE.toPath());
        } catch (IOException e) {
            throw new RuntimeException("File doesn't exist! Or maybe some another problem)");
        }
        for (int i = 0; i < excepted.size(); i++) {
            Assert.assertEquals(excepted.get(i), actual.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPathToFile_notOk() {
        FileWriterService writerService = new FileWriterServiceImpl();
        writerService.write(INVALID_PATH, INPUT);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullInputData_notOk() {
        FileWriterService writerService = new FileWriterServiceImpl();
        writerService.write(null, INPUT);
    }
}
