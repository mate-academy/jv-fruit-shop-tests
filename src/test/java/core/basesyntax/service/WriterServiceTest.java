package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String DEFAULT_INPUT = "abcd";
    private static final String PATH_TO_FILE =
            "D:\\Progr\\jv-fruit-shop-tests\\src\\test\\resources\\toWrite.csv";
    private static final String EMPTY_DATA = "";
    private static ReaderService readerService;
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
        writerService = new WriterServiceImpl();
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(PATH_TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void write_correctCase_ok() {
        writerService.write(DEFAULT_INPUT, PATH_TO_FILE);
        assertEquals(readerService.readFrom(PATH_TO_FILE), DEFAULT_INPUT);
    }

    @Test
    void write_emptyData_ok() {
        writerService.write(EMPTY_DATA, PATH_TO_FILE);
        assertEquals(readerService.readFrom(PATH_TO_FILE), EMPTY_DATA);
    }

    @Test
    void write_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write(null, null);
        });
    }

    @Test
    void write_firstArgumentNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write(null, PATH_TO_FILE);
        });
    }

    @Test
    void write_secondArgumentNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write(DEFAULT_INPUT, null);
        });
    }
}
