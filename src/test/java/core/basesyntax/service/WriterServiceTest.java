package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String TRUE_PATH = "src/test/resources/report.csv";
    private static final String FALSE_PATH = "";
    private static final String TITLE = "fruit,quantity";
    private static final String FIRST_LINE = "banana,20";
    private static final String SECOND_LINE = "apple,90";
    private static WriterService writerService;
    private static List<String> expected;
    private static StringBuilder report;
    private static File file;

    public WriterServiceTest() {
        file = new File(pathFix(TRUE_PATH));
    }

    @BeforeClass
    public static void beforeClass() {
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
        writerService.writeToFile(pathFix(TRUE_PATH), report.toString());
        List<String> actual;
        try {
            actual = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + TRUE_PATH, e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_emptyPath_NotOk() {
        writerService.writeToFile(FALSE_PATH, report.toString());
    }

    @Test
    public void writerService_writeEmptyReport_Ok() {
        writerService.writeToFile(pathFix(TRUE_PATH), TITLE);
        List<String> actual;
        List<String> emptyExpected = List.of(TITLE);
        try {
            actual = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + TRUE_PATH, e);
        }
        assertEquals(emptyExpected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeNullReport_NotOk() {
        writerService.writeToFile(pathFix(TRUE_PATH), null);
    }

    private String pathFix(String filePath) {
        filePath = filePath.replace("\\", File.separator);
        filePath = filePath.replace("/", File.separator);
        return filePath;
    }
}
