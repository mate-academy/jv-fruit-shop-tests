package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.CouldNotReadFromFileException;
import core.basesyntax.service.FileService;
import core.basesyntax.service.impl.FileServiceImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileServiceTest {
    private static final String INPUT_FILE = "src/test/test_resources/inputTest.csv";
    private static final String INVALID_FILE = "broken";
    private static final String OUTPUT_FILE = "src/test/test_resources/outputTest.csv";
    private static final String TEST_DATA = "Some new sentence";
    private static FileService fileService;

    @BeforeEach
    void init() {
        fileService = new FileServiceImpl();
    }

    @Test
    void readFromFile_normalData_Ok() {
        List<String> exceptedResult = readLineFromFile(INPUT_FILE);
        List<String> result = fileService.readFromFile(INPUT_FILE);

        assertEquals(exceptedResult, result,
                "The excepted result and result must be the same.");
    }

    @Test
    void readFromFile_invalidFile_notOk() {
        assertThrows(CouldNotReadFromFileException.class,
                () -> fileService.readFromFile(INVALID_FILE),
                "Can't work with invalid file.");
    }

    @Test
    void writeToFile_normalData_Ok() {
        fileService.writeToFile(OUTPUT_FILE, TEST_DATA);
        List<String> result = readLineFromFile(OUTPUT_FILE);
        for (var element : result) {
            assertEquals(TEST_DATA, element,
                    "The written data must be the same with read.");
        }
    }

    private List<String> readLineFromFile(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            List<String> result = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (IOException e) {
            throw new CouldNotReadFromFileException("Could not read from file: " + fileName);
        }
    }
}
