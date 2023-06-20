package core.basesyntax.service.impl;

import core.basesyntax.service.TransactionException;
import core.basesyntax.service.WriterService;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String EXCEPTION_MESSAGE
            = TransactionException.class.toString();
    private static final String CORRECT_FILE_PATH = "src/main/resources/output.csv";
    private static final String INCORRECT_FILE_PATH
            = "C:/Users/.../my-project/src/main/resources/output.csv";
    private static final String REPORT_CONTENT = "apple,120";
    private static WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writer_filePathIsNull_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> writerService.write(REPORT_CONTENT, null),
                String.format("%s should throw for file path which is null",
                        EXCEPTION_MESSAGE));
    }

    @Test
    void writer_contentIsNull_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> writerService.write(null, CORRECT_FILE_PATH),
                String.format("%s should throw for report content which is null",
                        EXCEPTION_MESSAGE));
    }

    @Test
    void writer_validPath_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> writerService.write(REPORT_CONTENT, INCORRECT_FILE_PATH),
                String.format("%s should throw for incorrect file path",
                        EXCEPTION_MESSAGE));
    }

    @Test
    void writer_fileCreatedCorrect_Ok() {
        writerService.write(REPORT_CONTENT, CORRECT_FILE_PATH);
        File file = new File(CORRECT_FILE_PATH);
        Assertions.assertTrue(file.exists());
    }
}
