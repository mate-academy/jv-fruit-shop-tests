package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.DataWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataWriterImplTest {
    private static final String EXPECTED_DATA = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90";
    private DataWriter dataWriter;

    @BeforeEach
    void setUp() {
        dataWriter = new DataWriterImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        String validFile = "src/test/resources/valid_report.csv";
        Files.deleteIfExists(Path.of(validFile));
    }

    @Test
    void writeData_validDataAndFile_ok() {
        String validFile = "src/test/resources/report.csv";
        dataWriter.writeData(EXPECTED_DATA, validFile);
        String actualData = null;
        try {
            actualData = Files.readString(Path.of(validFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(EXPECTED_DATA, actualData);
    }

    @Test
    void writeData_invalidFile_notOk() {
        String invalidFile = "src/test/resources/invalid_folder/report.csv";
        assertThrows(RuntimeException.class, () -> dataWriter
                .writeData(EXPECTED_DATA, invalidFile));
    }

    @Test
    void writeData_nullDataAndFile_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> dataWriter.writeData(null, null));
    }

    @Test
    void writeData_nullData_validFile_exceptionThrown() {
        String validFile = "src/test/resources/valid_report.csv";
        assertThrows(NullPointerException.class, () -> dataWriter.writeData(null, validFile));
    }
}
