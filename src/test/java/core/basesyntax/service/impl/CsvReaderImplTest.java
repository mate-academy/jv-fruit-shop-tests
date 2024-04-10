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

    @BeforeEach
    void beforeEach() {
        csvReader = new CsvReaderImpl();
        checkTransactionsStrings = List.of(
                "type;fruit;quantity",
                "b;banana;20",
                "s;banana;102",
                "p;banana;40",
                "r;banana;30"
        );
    }

    @Test
    void read_nullFileName_trowException() {
        assertThrows(NullPointerException.class, () -> csvReader.read(null));
    }

    @Test
    void read_notExistFile_trowException() {
        assertThrows(RuntimeException.class, () -> csvReader.read("notExistFile.csv"));
    }

    @Test
    void read_illegalFileFormat_throwException() {
        assertThrows(IllegalArgumentException.class, () -> csvReader.read("Text.txt"));
    }

    @Test
    void read_validFile_Ok() {
        List<String> fruitTransactions = csvReader.read("src/test/resources/reportToRead.csv");
        assertEquals(checkTransactionsStrings, fruitTransactions);
    }
}
