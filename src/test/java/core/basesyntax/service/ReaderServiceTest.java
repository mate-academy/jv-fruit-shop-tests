package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String DIRECTORY = "src/test/resources/input.csv";
    private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir")
            + "/empty_file";
    private static final List<String> EMPTY_LIST = new ArrayList<>();
    private static final List<String> RESULT_FROM_FILE =
            List.of("type,fruit,quantity",
                    "b,банан,20",
                    "b,яблуко,100",
                    "s,банан,100",
                    "p,банан,13",
                    "r,яблуко,10",
                    "p,яблуко,20",
                    "p,банан,5",
                    "s,банан,50");
    private ReaderService readerService = new ReaderServiceImpl();

    @BeforeAll
    static void beforeAll() throws Exception {
        Files.write(Path.of(TEMP_DIRECTORY), "".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void readDataFromFile_successful_ok() {
        List<String> actual = readerService.read(DIRECTORY);
        assertEquals(RESULT_FROM_FILE, actual);
    }

    @Test
    void readDataFromFile_notValidPath_notOk() {
        String path = "notValid";
        assertThrows(RuntimeException.class, () -> {
            readerService.read(path);
        });
    }

    @Test
    void readDataFromFile_emptyFile_ok() {
        List<String> actual = readerService.read(TEMP_DIRECTORY);
        assertEquals(EMPTY_LIST, actual);
    }
}
