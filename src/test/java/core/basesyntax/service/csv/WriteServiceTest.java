package core.basesyntax.service.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.csv.impl.CsvWriteServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteServiceTest {
    private static WriteService writeService;
    private static final String path = "src/test/resources/output/report.csv";

    @BeforeAll
    static void beforeAll() {
        writeService = new CsvWriteServiceImpl();
    }

    @Test
    public void save_validData_ok() {
        String expected = "column1,column2" + System.lineSeparator() + "value1,value2";
        writeService.save(expected, path);
        List<String> actualList = readFromFile();
        String actual = String.join(System.lineSeparator(), actualList);
        assertEquals(expected, actual);
    }

    @Test
    public void save_null_notOk() {
        assertThrows(NullPointerException.class, () -> writeService.save(null, path));
    }

    private List<String> readFromFile() {
        List<String> rows;
        try {
            rows = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file by path: " + path, e);
        }
        return rows;
    }
}
