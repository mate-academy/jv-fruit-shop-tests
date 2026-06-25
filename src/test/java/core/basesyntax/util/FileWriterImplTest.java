package core.basesyntax.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {

    private static final String TEST_FILE = "testReport.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void write_validPathAndReport_Ok() throws IOException {
        String report = "fruit,quantity\nbanana,100";

        fileWriter.write(TEST_FILE, report);

        String actual = Files.readString(Path.of(TEST_FILE));
        assertEquals(report, actual);
    }

}
