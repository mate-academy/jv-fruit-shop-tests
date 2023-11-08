package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.WriterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;

    @BeforeAll
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void readFile_nullPath_notOk() {
        assertThrows(Exception.class,
                () -> writerService.writeReport(null, "some text"));
    }
}
