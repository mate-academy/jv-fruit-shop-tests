package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String CSV_INPUT
            = "src/main/java/core/basesyntax/resources/reportToRead.csv";
    private static final String WRONG_FILE_PATH
            = "src/test/java/core/basesyntax/resources/badHeader.cs";
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_FileName_Format_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(WRONG_FILE_PATH),
                "Wrong file name format");
    }

    @Test
    void read_InputIsNull_NotOk() {
        assertNotNull(fileReader.read(CSV_INPUT));
    }

    @Test
    void read_Input_IsEmptyList_NotOk() {
        List<String> emptyList = new ArrayList<>();
        assertNotEquals(emptyList, fileReader.read(CSV_INPUT));
    }

    @Test
    void read_HeaderIsSeparated_NotOk() {
        List<String> notExpectedOutput = new ArrayList<>();
        notExpectedOutput.add("type;fruit;quantity");
        notExpectedOutput.add("b;banana;20");
        notExpectedOutput.add("b;apple;100");
        notExpectedOutput.add("s;banana;100");
        notExpectedOutput.add("p;banana;13");
        notExpectedOutput.add("r;apple;10");
        notExpectedOutput.add("p;apple;20");
        notExpectedOutput.add("p;banana;5");
        notExpectedOutput.add("s;banana;50");

        assertNotEquals(notExpectedOutput, fileReader.read(CSV_INPUT));
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
