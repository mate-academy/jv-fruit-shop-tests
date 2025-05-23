package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String REPORT_TO_READ = "src/main/resources/reportToRead.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_numberOfLinesInOutputListAndInputFileAreEqual_Ok() {
        int actual = fileReader.read(REPORT_TO_READ).size();
        assertEquals(13, actual);
    }

    @Test
    void read_impossibleToReadFromFile_NotOk() {
        String reportToRead = "unexistingfile.csv";
        assertThrows(RuntimeException.class, () -> fileReader.read(reportToRead));
    }
}
