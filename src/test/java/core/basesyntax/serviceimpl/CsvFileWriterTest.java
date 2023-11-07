package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.DataWriter;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String DATA1 = "Data 1;";
    private static final String DATA2 = "Data 2;";
    private static final String DATA3 = "Data 3;";
    private static final String FILE_TO_WRITE_VALID_LOCATION = "src/test/java/core/"
            + "basesyntax/filesForTesting/fileToWrite.csv";
    private static final String FILE_TO_WRITE_INVALID_LOCATION = "some invalid location";
    private static final String[] ARRAY_WITH_DATA = new String[] {DATA1, DATA2, DATA3};
    private static DataWriter csvFileWriter;
    private static File fileToDelete;

    @AfterAll
    static void afterAll() {
        fileToDelete = new File(FILE_TO_WRITE_VALID_LOCATION);
        fileToDelete.delete();
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        csvFileWriter = new CsvFileWriter();
        File file = new File(FILE_TO_WRITE_VALID_LOCATION);
        file.createNewFile();
    }

    @Test
    void write_Into_ValidFile_Ok() {
        assertTrue(() -> csvFileWriter.writeToFile(ARRAY_WITH_DATA, FILE_TO_WRITE_VALID_LOCATION));
    }

    @Test
    void write_Into_InvalidFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> csvFileWriter.writeToFile(ARRAY_WITH_DATA, FILE_TO_WRITE_INVALID_LOCATION));
    }
}
