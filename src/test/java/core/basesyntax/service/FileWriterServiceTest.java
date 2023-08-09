package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import org.junit.jupiter.api.Test;

class FileWriterServiceTest {
    private static final FileWriterService FILE_WRITER = new FileWriterServiceImpl();

    @Test
    void write_pathIsNull_notOk() {
        String report = "test";
        String path = null;
        assertThrows(RuntimeException.class, () -> FILE_WRITER.write(report, path));
    }
}
