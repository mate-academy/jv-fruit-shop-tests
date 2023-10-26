package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String SEPARATOR = ",";
    private static final String HEADER = "fruit,quantity";
    private static FileWriter fileWriter;
    private static FileReader fileReader;
    private static final String FILE_NAME = "report.csv";
    private static final String REPORT = HEADER + System.lineSeparator()
            + "apple" + SEPARATOR + 160 + System.lineSeparator()
            + "banana" + SEPARATOR + 115 + System.lineSeparator()
            + "pear" + SEPARATOR + 140;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    void writeToFile_Ok() {
        File file = (new File(FILE_NAME));
        fileWriter.writeToFile(file, REPORT);
        List<String> expected = new ArrayList<>(List.of(HEADER,
                "apple" + SEPARATOR + 160,
                "banana" + SEPARATOR + 115,
                "pear" + SEPARATOR + 140));
        List<String> actual = fileReader.readFromFile(file);
        assertEquals(expected, actual);
    }

    @Test
    void writeToReadOnlyFile_Not_Ok() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.writeToFile(null, REPORT));
    }
}
