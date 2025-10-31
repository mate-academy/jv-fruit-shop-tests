package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Constants;
import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderTest {
    private static final String FILE_NAME = "reportToRead.csv";
    private static final String WRONG_FILE_NAME = "noSuchReport.csv";
    private static final String WRONG_PATH = "/resources/reportToRead.csv";
    private FileReader fileReader;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void initializeService() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_existingPath_ok() throws IOException {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("p,banana,5");
        expected.add("s,banana,50");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit");
        stringBuilder.append(Constants.COMA_SEPARATOR);
        stringBuilder.append("quantity");
        for (var line : expected) {
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(line);
        }
        String dataInResourceFile = stringBuilder.toString();
        Path resouceFilePath = tempDir.resolve(FILE_NAME);
        Files.writeString(resouceFilePath, dataInResourceFile);
        String absolutePathString = resouceFilePath.toString();
        List<String> actual = fileReader.read(absolutePathString);
        assertEquals(expected, actual, "can't read existing file from proper path");

    }

    @Test
    void read_nonExistingFile_notOk() {
        List<String> expected = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> fileReader.read(WRONG_FILE_NAME),
                "not throwing error for non existing file");
    }

    @Test
    void read_wrongPath_notOk() {
        List<String> expected = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> fileReader.read(WRONG_PATH),
                "not throwing error for non existing file");
    }
}
