package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReadFromCsvFileService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadFromCsvFileServiceImplTest {
    private static final String FIRST_FILENAME = "fruits1.csv";
    private static final String SECOND_FILENAME = "fruits2.csv";
    private static final String THIRD_FILENAME = "fruits3.csv";
    private static ReadFromCsvFileService csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new ReadFromCsvFileServiceImpl();
    }

    @Test
    void csvReader_twoFruits_Ok() {
        List<String> expected = List.of("b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13",
                "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = csvReader.readFile(FIRST_FILENAME);

        assertEquals(expected, actual);
    }

    @Test
    void csvReader_threeFruits_Ok() {
        List<String> expected = List.of("b,apple,20", "b,banana,50",
                "b,pineapple,10", "s,pineapple,45",
                "s,banana,20", "r,apple,17",
                "p,pineapple,4", "p,banana,10",
                "s,pineapple,60", "s,apple,80");
        List<String> actual = csvReader.readFile(SECOND_FILENAME);

        assertEquals(expected, actual);
    }

    @Test
    void csvReader_fourFruits_Ok() {
        List<String> expected = List.of("b,apple,80", "b,grape,120",
                "b,banana,30", "b,melon,5",
                "s,grape,80", "p,apple,60",
                "p,banana,15", "r,apple,30",
                "r,melon,5", "p,grape,50",
                "s,apple,50", "p,banana,5");
        List<String> actual = csvReader.readFile(THIRD_FILENAME);

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsCount.clear();
    }
}
