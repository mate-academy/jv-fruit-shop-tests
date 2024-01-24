package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.file.FileReader;
import core.basesyntax.service.file.FileReaderImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private static final String CONTENT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90";
    private static final String OUTPUT_FILE_PATH =
            "src/test/resources/testOutput.csv";
    private static final String INCORRECT_OUTPUT_FILE_PATH =
            "src/test/resource/incorrectPath.csv";

    private static ReportCreator reportCreator;
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        reportCreator = new ReportCreator();
    }

    @Test
    void createRapport_isOk() {
        reportCreator.createReport(OUTPUT_FILE_PATH, CONTENT);
        String actualResult = fileReader.readFromFile(OUTPUT_FILE_PATH);
        String expectedResult = CONTENT;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRapportWithIncorrectFilePath_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reportCreator.createReport(INCORRECT_OUTPUT_FILE_PATH, CONTENT);
        });
        String expectedMassage = "Can't write to file"
                + INCORRECT_OUTPUT_FILE_PATH;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }
}
