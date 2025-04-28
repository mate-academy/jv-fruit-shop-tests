package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readMangoFromFile_Ok() {
        List<String> expectedList = new LinkedList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,mango,90");
        expectedList.add("r,mango,5");
        expectedList.add("p,mango,10");
        expectedList.add("s,mango,70");

        List<String> actual = fileReader.read("src/test/resources/Test1.csv");
        assertEquals(expectedList, actual);
    }

    @Test
    void readFromNonExistingFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read("qwerty11.csv"));
    }
}
