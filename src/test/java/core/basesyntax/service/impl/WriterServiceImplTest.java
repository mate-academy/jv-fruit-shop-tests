package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private WriterServiceImpl writerService;
    private boolean expectedResult;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
        expectedResult = true;
    }

    @Test
    void write_writesDataIntoFile_Ok() {
        List<String> data = List.of("ABC");
        assertEquals(expectedResult, writerService.write(data));
    }

    @Test
    void write_writesEmptyData_Ok() {
        List<String> data = List.of();
        assertEquals(expectedResult, writerService.write(data));
    }

    @Test
    void write_trowsExceptionIfNull_Ok() {
        List<String> data = null;
        assertThrows(RuntimeException.class, () -> writerService.write(data));
    }
}
