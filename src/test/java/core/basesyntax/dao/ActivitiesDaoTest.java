package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ActivitiesDaoTest {
    private static final String REPORT_PATH = "src/test/resources/report.csv";
    private static ActivitiesDao activitiesDao;
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";

    @BeforeAll
    public static void setUp() {
        activitiesDao = new ActivitiesDaoImpl();
    }

    @Test
    void write_emptyReport_ok() {
        assertDoesNotThrow(() -> activitiesDao.write("", REPORT_PATH));
    }

    @Test
    void write_allOk() {
        assertDoesNotThrow(() -> activitiesDao.write(REPORT, REPORT_PATH));

        try {
            List<String> reportLines = Files.readAllLines(Path.of(REPORT_PATH));
            String actualReport = reportLines.stream()
                    .collect(Collectors.joining(System.lineSeparator()));
            assertEquals(REPORT, actualReport);
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + REPORT_PATH, e);
        }
    }

    @Test
    void write_nullReport_notOk() {
        assertThrows(RuntimeException.class, () -> activitiesDao.write(null, REPORT_PATH));
    }

    @Test
    void write_wrongPath_notOk() {
        assertThrows(RuntimeException.class, () -> activitiesDao.write(REPORT, ""));
    }
}
