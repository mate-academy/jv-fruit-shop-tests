package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private FileWriterImpl fileWriter;
    private String validReportInput = "fruit,quantity\n"
            + "apple,120\n"
            + "banana,100";
    private String validReportPath =
            "src\\test\\java\\core\\basesyntax\\resources\\reportTest.csv";
    private String invalidReportPath =
            "test\\java\\core\\\basesyntax\\resources<7>";

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validInput_ok() {
        List<String> expected = List.of("fruit,quantity","apple,120","banana,100");

        fileWriter.write(validReportInput, validReportPath);

        assertTrue(Files.exists(Path.of(validReportPath)));

        try {
            List<String> actual = Files.readAllLines(Path.of(validReportPath));
            assertEquals(expected,actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void write_invalidPathInput_throwsRuntimeExceptions() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(validReportInput, invalidReportPath));
    }

    @Test
    void write_nullPathInput_throwsRuntimeExceptions() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(validReportInput, null));
    }
}
