package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String INACCESSIBLE_FILE_PATH = "src/test/resources/inaccessibleFile.csv";
    private FileWriter fileWriter;
    private String resultingReport;
    private File inaccessibleFile;

    @BeforeEach
    void setUp() throws IOException {
        fileWriter = new FileWriterImpl();
        resultingReport = "banana,172" + System.lineSeparator()
                        + "raspberry,15" + System.lineSeparator();
        inaccessibleFile = new File(INACCESSIBLE_FILE_PATH);
        inaccessibleFile.createNewFile();
        inaccessibleFile.setWritable(false);
    }

    @AfterEach
    void tearDown() {
        inaccessibleFile.delete();
    }

    @Test
    void write_WriteToFIleIsImpossible_NotOk() {
        assertThrows(RuntimeException.class, () -> fileWriter
                .write(resultingReport, INACCESSIBLE_FILE_PATH));
    }
}
