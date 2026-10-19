package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String OUTPUT_FILE = "src/test/resources/test_output.csv";

    @BeforeAll
    static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(OUTPUT_FILE));
    }

    @Test
    void writeToFile_validData_ok() throws IOException {
        String report = "fruit,quantity\nbanana,20\napple,50";
        fileWriter.writeToFile(OUTPUT_FILE, report);
        File file = new File(OUTPUT_FILE);
        assertTrue(file.exists(), "Output file should be created");

        String content = Files.readString(Path.of(OUTPUT_FILE));
        assertEquals(report, content, "File content should match the report");
    }
}
