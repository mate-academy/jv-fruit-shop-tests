package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.NoFileToReadException;
import core.basesyntax.service.interfaces.FileWriter;
import core.basesyntax.service.serviceimpl.FileWriterImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {

    private static final String REPORT_FILE_PATH = "src/test/resources/service/report.csv";
    private static final String READONLY_FILE_PATH = "src/test/resources/service/readonly.csv";
    private static final String EXPECTED_CONTENT = "fruit,quantity\n"
            + "banana,157\n"
            + "apple,90\n"
            + "kiwi,20\n";

    private FileWriter fileWriter;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void write_reportValidData_Ok() throws IOException {
        fileWriter.writeDataToFile(EXPECTED_CONTENT, REPORT_FILE_PATH);
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(REPORT_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        String actualContent = contentBuilder.toString();
        assertEquals("The content of the file should match the expected result",
                EXPECTED_CONTENT, actualContent);
    }

    @Test
    public void write_readonlyFile_notOk() {
        FileWriter fileWriter = new FileWriterImpl();
        new File(READONLY_FILE_PATH).setReadOnly();
        assertThrows(NoFileToReadException.class, () -> {
            fileWriter.writeDataToFile(EXPECTED_CONTENT, READONLY_FILE_PATH);
        });
    }
}
