package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFile_validData_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,100");

        List<String> actual = readerService.readFile("input.csv");

        assertEquals(expected, actual, "Returned list must contains all lines from file!");
    }

    @Test
    void readFile_fileNotExist_throwException() {
        assertThrows(RuntimeException.class, () -> readerService.readFile("notExist.csv"),
                "Method must throw exception if file name is invalid!");
    }
}
