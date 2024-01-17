package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RapportCreatorTest {
    private static final String CONTENT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90";
    private static final String OUTPUT_FILE_PATH =
            "src/test/resources/testOutput.csv";
    private static final String INCORRECT_OUTPUT_FILE_PATH =
            "src/test/resource/incorrectPath.csv";

    private static RapportCreator rapportCreator;

    @BeforeAll
    static void beforeAll() {
        rapportCreator = new RapportCreator();
    }

    @Test
    void createRapport_isOk() {
        rapportCreator.createRapport(OUTPUT_FILE_PATH, CONTENT);
        String actualResult = readFromFile(OUTPUT_FILE_PATH);
        String expectedResult = CONTENT;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRapportWithIncorrectFilePath_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            rapportCreator.createRapport(INCORRECT_OUTPUT_FILE_PATH, CONTENT);
        });
        String expectedMassage = "Can't write to file"
                + INCORRECT_OUTPUT_FILE_PATH;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
