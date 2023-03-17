package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String TRUE_PATH = "src" + File.separator
                                            + "test" + File.separator
                                            + "resources" + File.separator
                                            + "report.csv";
    private static final String FALSE_PATH = "";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String TITLE = "fruit,quantity";
    private static final String FIRST_LINE = "banana,20";
    private static final String SECOND_LINE = "apple,90";
    private static WriterService writerService;
    private static List<String> expected;
    private static StringBuilder report;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.fruits.clear();
        Storage.fruits.put(BANANA, 20);
        Storage.fruits.put(APPLE, 90);
        expected = new ArrayList<>();
        expected.add(TITLE);
        expected.add(FIRST_LINE);
        expected.add(SECOND_LINE);
        report = new StringBuilder(TITLE);
        report.append(System.lineSeparator()).append(FIRST_LINE);
        report.append(System.lineSeparator()).append(SECOND_LINE);
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writerService_writeToFile_Ok() {
        writerService.writeToFile(TRUE_PATH, report.toString());
        File file = new File(TRUE_PATH);
        List<String> actual;
        try {
            actual = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_emptyPath_NotOk() {
        writerService.writeToFile(FALSE_PATH, report.toString());
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
