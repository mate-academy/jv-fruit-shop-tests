package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String ACTUAL_FILE_WRITE_PATH = "src/test/resources/actualFinalReport.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static final String EMPTY_REPORT = "";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() {
        File file = new File(ACTUAL_FILE_WRITE_PATH);
        file.deleteOnExit();
    }

    @Test
    void write_correctWriting_ok() {
        fileWriter.write(REPORT, ACTUAL_FILE_WRITE_PATH);
        File actual = new File(ACTUAL_FILE_WRITE_PATH);
        try {
            String actualContent = Files.readString(actual.toPath());
            Assertions.assertEquals(REPORT, actualContent);
        } catch (IOException e) {
            throw new RuntimeException("error file reading file", e);
        }
    }

    @Test
    void write_emptyReportToWrite_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write(EMPTY_REPORT, ACTUAL_FILE_WRITE_PATH),
                "Expected RuntimeException was not thrown in " + FileWriterImpl.class);
    }
}
