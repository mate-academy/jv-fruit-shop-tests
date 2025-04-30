package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.CustomException;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static File inputFile;
    private static File invalidInputFile;
    private static String inputData;
    private static FileReader fileReader;
    private static final String PATH = "src/test/resources/inputDatabase.csv";
    private static final String INVALID_PATH = "invalid/path/inputDatabase.csv";

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        inputFile = new File(PATH);
        inputData = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50";
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(new File(inputFile.toURI())))) {
            bufferedWriter.write(inputData);
        } catch (IOException e) {
            throw new RuntimeException("can't write inputData to file: " + inputFile);
        }
    }

    @Test
    void readReport_ValidReport_IsOk() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.readReport(inputFile);
        assertEquals(expected, actual);
    }

    @Test
    void readReport_EmptyFile_NotOk() {
        invalidInputFile = new File(INVALID_PATH);
        assertThrows(CustomException.class, () -> fileReader.readReport(invalidInputFile));
    }
}
