package core.basesyntax.service.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.csv.impl.CsvReadServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReadServiceTest {
    private static ReadService readService;

    @BeforeAll
    static void beforeAll() {
        readService = new CsvReadServiceImpl();
    }

    @Test
    public void read_validInput_ok() {
        String standardInputPath = "src/test/resources/input/standardInput.csv";
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = readService.read(standardInputPath);
        assertEquals(expected, actual);
    }

    @Test
    public void read_emptyFile_ok() {
        String standardInputPath = "src/test/resources/input/empty.csv";
        List<String> expected = Collections.emptyList();
        List<String> actual = readService.read(standardInputPath);
        assertEquals(expected, actual);
    }

    @Test
    public void read_nonExistentFile_notOk() {
        String standardInputPath = "src/test/resources/input/doesntExist.csv";
        assertThrows(RuntimeException.class, () -> readService.read(standardInputPath));
    }

    @Test
    public void read_null_notOk() {
        assertThrows(NullPointerException.class, () -> readService.read(null));
    }
}
