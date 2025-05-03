package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderImplTest {
    private static CsvReader csvReader;
    private static List<String> checkTransactionsStrings;
    private static final String FIRST_STRING = "type;fruit;quantity";
    private static final String BANANA_BALANCE = "b;banana;20";
    private static final String BANANA_SUPPLY = "s;banana;102";
    private static final String BANANA_PURCHASE = "p;banana;40";
    private static final String BANANA_RETURN = "r;banana;30";

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
        checkTransactionsStrings = List.of(
                FIRST_STRING,
                BANANA_BALANCE,
                BANANA_SUPPLY,
                BANANA_PURCHASE,
                BANANA_RETURN
        );
    }

    @Test
    void read_nullFileName_throwsException() {
        assertThrows(NullPointerException.class, () -> csvReader.read(null));
    }

    @Test
    void read_notExistFile_throwsException() {
        assertThrows(RuntimeException.class, () -> csvReader.read("notExistFile.csv"));
    }

    @Test
    void read_illegalFileFormat_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> csvReader.read("Text.txt"));
    }

    @Test
    void read_validFile_Ok() {
        List<String> fruitTransactions = csvReader.read("src/test/resources/reportToRead.csv");
        assertEquals(checkTransactionsStrings, fruitTransactions);
    }
}
