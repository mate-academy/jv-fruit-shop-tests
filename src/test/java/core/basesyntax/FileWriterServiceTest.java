package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FileWriterService;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterServiceTest {
    private static final String OUTPUT_FILE = "src/main/resources/report.csv";
    private static final String TEST_FILE = "src/main/resources/test.csv";

    private static FileWriterService writeToFile;

    @AfterEach
    public void clearStorage() {
        Storage.Storage.clear();
    }

    @BeforeAll
    static void setUp() {
        writeToFile = new FileWriterService();
    }

    @Test
    public void write_validInput_ok() {
        String separator = System.lineSeparator();
        String data = "fruit,quantity" + separator
                + "banana,152" + separator
                + "apple,90" + separator;
        writeToFile.write(data, OUTPUT_FILE);
        int expectedResult = -1;

        try (BufferedInputStream file1 = new BufferedInputStream(new FileInputStream(OUTPUT_FILE));
                        BufferedInputStream file2 =
                            new BufferedInputStream(new FileInputStream(TEST_FILE))) {

            int result = 0;
            int chunk = 0;
            long position = 1;
            while ((chunk = file1.read()) != -1) {
                if (chunk != file2.read()) {
                    result = (int) position;
                }
                position++;
            }
            if (file2.read() == -1) {
                result = -1;
            } else {
                result = (int) position;
            }
            assertEquals(expectedResult, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
