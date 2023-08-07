package core.basesyntax.service.impl;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.CsvFileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterImplTest {
    private static final String VALID_INPUT = "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10";
    private static final String FILE_PATH = "src/test/resources/validData.csv";
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static final String SECOND_LINE = "b,apple,100";
    private static final String INVALID_FILE_PATH = "src/test/resours/validData.csv";
    private CsvFileWriter csvFileWriter;

    @BeforeEach
    void setUp() {
        csvFileWriter = new CsvFileWriterImpl();
    }

    @Test
    void write_validInputData_ok() {
        csvFileWriter.write(FILE_PATH, VALID_INPUT);
        assertTrue(new File(FILE_PATH).exists());
        verifyFileContents();
    }

    @Test
    void write_invalidFileName_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> csvFileWriter.write(INVALID_FILE_PATH, VALID_INPUT));
        assertEquals("Can't write data in file " + INVALID_FILE_PATH, exception.getMessage());
    }

    private void verifyFileContents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String firstLine = reader.readLine();
            assertEquals(FIRST_LINE, firstLine);
            String secondLine = reader.readLine();
            assertEquals(SECOND_LINE, secondLine);
        } catch (IOException e) {
            fail("Exception should not be thrown when reading the file.");
        }
    }
}
