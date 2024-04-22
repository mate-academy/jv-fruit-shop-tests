package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReader fileReader;
    private static List<String> checkTransStrings;
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static final String BANANA_BALANCE = "b,banana,20";
    private static final String BANANA_SUPPLY = "s,banana,100";
    private static final String BANANA_PURCHASE = "p,banana,13";
    private static final String BANANA_RETURN = "r,banana,10";

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
        checkTransStrings = List.of(
          FIRST_LINE,
          BANANA_BALANCE,
          BANANA_SUPPLY,
          BANANA_PURCHASE,
          BANANA_RETURN
        );
    }

    @Test
    void nullFileName_NotOK() {
        assertThrows(NullPointerException.class, () ->
                fileReader.read(null));
    }

    @Test
    void illegalFileFormat_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                fileReader.read("report.txt"));
    }

    @Test
    void notExistingFile_NotOK() {
        assertThrows(RuntimeException.class, () ->
                fileReader.read("noSuchFile.csv"));
    }

    @Test
    void validFile_OK() {
        List<String> fruitTransSting = fileReader.read("src/test/resources/reportToRead.csv");
        assertEquals(checkTransStrings, fruitTransSting);
    }
}
