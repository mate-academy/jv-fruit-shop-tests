package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReadServiceImpl;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReadServiceImplTest {
    private static final String VALID_PATH_TO_VALID_FILE
            = "src/test/java/resources/dataForReadTest.csv";
    private static final String PATH_TO_NONEXISTENT_FILE = "path/to/nonexistent/file.csv";

    private static ReadServiceImpl readService;

    @BeforeAll
    public static void beforeAll() {
        readService = new ReadServiceImpl();
    }

    @Test
    public void test_ReadFile_Ok() throws IOException {
        List<String> expected = List.of("type,fruit,quantity","b,banana,20",
                "b,apple,100");
        String existFile = VALID_PATH_TO_VALID_FILE;
        List<String> actual = readService.readFromFile(existFile);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_ReadNonexistentFile_notOk() {
        assertThrows(RuntimeException.class, ()
                -> readService.readFromFile(PATH_TO_NONEXISTENT_FILE));
    }
}
