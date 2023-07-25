package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFile_CheckIncorrectAddress_NotOk() {
        String inputFileName = "src/main/input_file.csv";
        assertThrows(RuntimeException.class, () -> fileReader.readFile(inputFileName));
    }

    @Test
    void readFile_CheckFileNameIsNull_NotOk() {
        String inputFileName = null;
        assertThrows(RuntimeException.class, () -> fileReader.readFile(inputFileName));
    }

    @Test
    void readFile_CheckReadingTestFile_Ok() {
        String inputFileName = "src/test/resources/test_input_file.csv";
        FileReaderResult fileReaderResult = fileReader.readFile(inputFileName);
        String[] lines = fileReaderResult.getLines();
        String secondStringExpected = "b,banana,20";
        String ninthStringExpected = "s,banana,50";
        assertEquals(secondStringExpected, lines[1]);
        assertEquals(ninthStringExpected, lines[8]);
    }
}
