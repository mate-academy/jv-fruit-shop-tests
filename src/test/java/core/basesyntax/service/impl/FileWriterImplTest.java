package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.DataWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterImplTest {
    private static DataWriter dataWriter;
    private static final String ACTUAL_CSV_REPORT_FILE_PATH = "src/test/resources/actualReport.csv";
    private static final String EXPECTED_REPORT_FILE_PATH = "src/test/resources/expectedReport.csv";
    private static final String INVALID_REPORT_FILE_PATH = "src/test/@#$/expectedReport.csv";
    private static final String VALID_REPORT_STRING = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private static final String NULL_PATH_EXCEPTION_MESSAGE = "File path can`t be null";
    private static final String NULL_DATA_EXCEPTION_MESSAGE = "Recordable data can`t be null";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void writeData_actualFileDataEqualsValid_ok() {
        dataWriter = new FileWriterImpl(EXPECTED_REPORT_FILE_PATH, VALID_REPORT_STRING.getBytes());
        dataWriter.writeData();
        List<String> actualData;
        List<String> expectedData;
        try {
            Path expectedDataPath = Files.write(
                    Path.of(ACTUAL_CSV_REPORT_FILE_PATH), VALID_REPORT_STRING.getBytes());
            expectedData = Files.readAllLines(expectedDataPath);
            actualData = Files.readAllLines(Path.of(EXPECTED_REPORT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(actualData, expectedData);
    }

    @Test
    public void writeData_invalidFilePathThrowsException_notOk() {
        expectedException.expect(RuntimeException.class);
        dataWriter = new FileWriterImpl(INVALID_REPORT_FILE_PATH, VALID_REPORT_STRING.getBytes());
        dataWriter.writeData();
    }

    @Test
    public void writeData_nullFilePathThrowsException_notOk() {
        expectedException.expect(RuntimeException.class);
        dataWriter = new FileWriterImpl(null, VALID_REPORT_STRING.getBytes());
        dataWriter.writeData();
    }

    @Test
    public void writeData_nullDataToWriteThrowsException_notOk() {
        expectedException.expect(RuntimeException.class);
        dataWriter = new FileWriterImpl(ACTUAL_CSV_REPORT_FILE_PATH, null);
        dataWriter.writeData();
    }

    @Test
    public void writeData_ExceptionMessageWithNullData_ok() {
        expectedException.expectMessage(NULL_DATA_EXCEPTION_MESSAGE);
        dataWriter = new FileWriterImpl(ACTUAL_CSV_REPORT_FILE_PATH, null);
        dataWriter.writeData();
    }

    @Test
    public void writeData_ExceptionMessageWithNullPath_ok() {
        expectedException.expectMessage(NULL_PATH_EXCEPTION_MESSAGE);
        dataWriter = new FileWriterImpl(null, VALID_REPORT_STRING.getBytes());
        dataWriter.writeData();
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

}
