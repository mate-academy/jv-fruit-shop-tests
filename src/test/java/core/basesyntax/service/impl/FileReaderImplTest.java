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
    void read_nullFileName_notOK() {
        assertThrows(NullPointerException.class, () ->
                fileReader.read(null));
    }

    @Test
    void read_illegalFileFormat_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                fileReader.read("report.txt"));
    }

    @Test
    void read_notExistingFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileReader.read("noSuchFile.csv"));
    }

    @Test
    void read_validFile_Ok() {
        List<String> fruitTransSting = fileReader.read("src/test/resources/reportToRead.csv");
        assertEquals(checkTransStrings, fruitTransSting);
    }
}
