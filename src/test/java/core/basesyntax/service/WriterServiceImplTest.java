package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String LINE_OK = "b,banana,100";
    private static final String PATH_TO_TEST = "src/test/java/core/basesyntax/test.txt";
    private static final String WRONG_PATH = " ";
    private static WriterService writerService;
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test
    void write_To_File_isOk() {
        writerService.write(HEADER + LINE_OK, PATH_TO_TEST);
        assertEquals(readerService.getListOfDataFromFile(PATH_TO_TEST),
                List.of(HEADER + LINE_OK));
    }

    @Test
    void wrong_Name_File_isNotOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write(" ", WRONG_PATH);
        });
    }
}
