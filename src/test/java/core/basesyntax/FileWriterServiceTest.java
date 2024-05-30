package core.basesyntax;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterServiceTest {
    private static FileWriterService writeToFile;
    private static Path outputPath;
    private static Path testPath;

    @TempDir
    private static Path tempDir;

    @AfterEach
    void clearStorage() {
        Storage.Storage.clear();
    }

    @BeforeAll
    static void setUp() {
        writeToFile = new FileWriterService();
        outputPath = tempDir.resolve("report.csv");
        testPath = Paths.get("src/test/resources/test.csv");
    }

    @Test
    void write_validInput_ok() throws IOException {
        String separator = System.lineSeparator();
        String data = "fruit,quantity" + separator
                + "banana,152" + separator
                + "apple,90" + separator;

        writeToFile.write(data, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        assertTrue(Files.isReadable(outputPath));

        byte[] outputFileBytes = Files.readAllBytes(outputPath);
        byte[] testFileBytes = Files.readAllBytes(testPath);

        assertArrayEquals(outputFileBytes, testFileBytes, "File contents should match");

    }
}
