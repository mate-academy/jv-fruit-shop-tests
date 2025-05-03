package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.DailyReportFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DailyReportFileWriterImplTest {
    private static final Path FILE_TO_WRITE_TEST = Path.of(
            "src/test/resources/statisticReportTest.csv");
    private static DailyReportFileWriter dailyReportFileWriter;

    @BeforeAll
    static void beforeAll() {
        dailyReportFileWriter = new DailyReportFileWriterImpl();
    }

    @Test
    void writeDailyStatistic_writeOneLine_ok() {
        String expected = "banana, 700";
        dailyReportFileWriter.writeDailyStatistic(FILE_TO_WRITE_TEST, expected);

        String actual = readFile(FILE_TO_WRITE_TEST);
        assertEquals(expected, actual);
    }

    @Test
    void writeDailyStatistic_writeThreeLines_ok() {
        String expected = "banana, 700" + System.lineSeparator()
                + "peach, 500" + System.lineSeparator()
                + "grape, 800";
        dailyReportFileWriter.writeDailyStatistic(FILE_TO_WRITE_TEST, expected);

        String actual = readFile(FILE_TO_WRITE_TEST);
        assertEquals(expected, actual);
    }

    @Test
    void writeDailyStatistic_isFileExist_ok() {
        String report = "avocado, 50";
        dailyReportFileWriter.writeDailyStatistic(FILE_TO_WRITE_TEST, report);

        assertTrue(Files.exists(FILE_TO_WRITE_TEST));
    }

    private String readFile(Path path) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> lines = Files.readAllLines(path);
            lines.forEach(l -> stringBuilder.append(l).append(System.lineSeparator()));
            return stringBuilder.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + path);
        }
    }
}
