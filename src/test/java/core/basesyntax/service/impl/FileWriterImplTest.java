package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Writer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private static final String RESULTING_REPORT = "fruit,quantity\nbanana,107\napple,100";
    private static final String EMPTY_REPORT = "";
    private static String resultingReport;
    private static final String FILE_TO_WRITE = "testFile.csv";
    private static Writer fileWriter;

    @TempDir
    private static Path tempDir;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void testWriteToFile_validDataInput_ok() {
        resultingReport = RESULTING_REPORT;
        File file = tempDir.resolve(FILE_TO_WRITE).toFile();
        fileWriter.writeToFile(resultingReport, file.getPath());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.lines()
                    .collect(Collectors.joining("\n"));
            assertEquals(resultingReport, line);
        } catch (IOException e) {
            throw new RuntimeException("The file by path "
                    + file.getPath() + " cannot be read or does not exist.", e);
        }
    }

    @Test
    public void testWriteToFile_emptyStringInput_ok() {
        resultingReport = EMPTY_REPORT;
        File file = tempDir.resolve(FILE_TO_WRITE).toFile();
        fileWriter.writeToFile(resultingReport, file.getPath());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.lines()
                    .collect(Collectors.joining("\n"));
            assertEquals(resultingReport, line);
        } catch (IOException e) {
            throw new RuntimeException("The file by path "
                    + file.getPath() + " cannot be read or does not exist.", e);
        }
    }

    @Test
    public void testWriteToFile_nullInput_notOk() {
        File file = tempDir.resolve(FILE_TO_WRITE).toFile();
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(null, file.getPath()));
    }
}
