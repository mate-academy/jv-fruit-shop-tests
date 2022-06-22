package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT_FILE_PATH_TEST =
            "src/test/resources/fruit_shop_report-test.csv";
    private static final String REPORT_FILE_PATH =
            "src/test/resources/fruit_shop_report.csv";
    private static final String EMPTY_FILE_TEST =
            "src/test/resources/fruit_shop_empty_file_test.csv";

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static String report;
    private static FileWriterService fileWriter;

    private List<String> readFileTest(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath);
        }
    }

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterServiceImpl();
        StringBuilder builder = new StringBuilder();
        report = builder.append("fruit, quantity")
                .append(LINE_SEPARATOR)
                .append("banana, 152")
                .append(LINE_SEPARATOR)
                .append("apple, 90")
                .toString();
    }

    @Test
    public void writeFile_writeReport_ok() {
        fileWriter.writeFile(REPORT_FILE_PATH_TEST, report);
        List<String> expected = readFileTest(REPORT_FILE_PATH);
        List<String> actual = readFileTest(REPORT_FILE_PATH_TEST);
        assertEquals(expected, actual);
    }

    @Test
    public void writeFile_fromEmptyFile_ok() {
        fileWriter.writeFile(REPORT_FILE_PATH_TEST, "");
        List<String> expected = readFileTest(EMPTY_FILE_TEST);
        List<String> actual = readFileTest(REPORT_FILE_PATH_TEST);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_toInvalidFile_notOk() {
        fileWriter.writeFile("", report);
    }
}
