package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String CSV_INPUT
            = "src/test/resources/reportToRead.csv";
    private static final String WRONG_FILE_PATH
            = "src/test/resources/invalidHeader.cs";
    private static final String EMPTY_FILE_PATH
            = "src/test/resources/empty.cs";
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_FileNameFormat_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(WRONG_FILE_PATH),
                "Invalid file name format");
    }

    @Test
    void read_InputIsNull_Ok() {
        assertNotNull(fileReader.read(CSV_INPUT));
    }

    @Test
    void read_InputIsEmptyList_NotOk() {
        File empyFile = new File(EMPTY_FILE_PATH);
        try {
            empyFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }
        assertNotEquals(fileReader.read(EMPTY_FILE_PATH), fileReader.read(CSV_INPUT));
    }

    @Test
    void read_Ok() {
        List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("b;banana;20");
        expectedOutput.add("b;apple;100");
        expectedOutput.add("s;banana;100");
        expectedOutput.add("p;banana;13");
        expectedOutput.add("r;apple;10");
        expectedOutput.add("p;apple;20");
        expectedOutput.add("p;banana;5");
        expectedOutput.add("s;banana;50");

        assertEquals(expectedOutput, fileReader.read(CSV_INPUT));
    }
}
