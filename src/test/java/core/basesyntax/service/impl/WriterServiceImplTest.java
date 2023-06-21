package core.basesyntax.service.impl;

import core.basesyntax.service.TransactionException;
import core.basesyntax.service.WriterService;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String EXCEPTION
            = TransactionException.class.toString();
    private static final String CORRECT_FILE_PATH = "src/main/resources/output.csv";
    private static final String INCORRECT_FILE_PATH
            = "C:/Users/.../my-project/src/main/resources/output.csv";
    private static final String REPORT_CONTENT = "apple,120";
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writer_filePathIsNull_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> writerService.write(REPORT_CONTENT, null));
    }

    @Test
    void writer_contentIsNull_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> writerService.write(null, CORRECT_FILE_PATH));
    }

    @Test
    void writer_invalidPath_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> writerService.write(REPORT_CONTENT, INCORRECT_FILE_PATH));
    }

    @Test
    void writer_validFile_ok() {
        writerService.write(REPORT_CONTENT, CORRECT_FILE_PATH);
        File file = new File(CORRECT_FILE_PATH);
        Assertions.assertTrue(file.exists());
    }
}
