package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriterServ;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private FileWriterServ fileWriter;
    private final String dataToWrite = "\tfruit,quantity"
            + System.lineSeparator()
            + "\tbanana,152"
            + System.lineSeparator()
            + "\tapple,90"
            + System.lineSeparator();

    @BeforeEach
    void setUp() {
        fileWriter = new CsvFileWriter();
    }

    @Test
    void write_CheckCorrectWriteDataToFile_Ok() {
        String pathToWrite = "src/test/resources/write.csv";
        fileWriter.write(dataToWrite, pathToWrite);
        Path path = Path.of(pathToWrite);
        StringBuilder builder = new StringBuilder();
        List<String> dataFromWriteFile;
        try {
            dataFromWriteFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can not read data from test file",e);
        }
        for (String line : dataFromWriteFile) {
            builder.append(line).append(System.lineSeparator());
        }
        String actualWriteData = builder.toString();
        assertEquals(dataToWrite, actualWriteData);
    }

    @Test
    void write_FilePathIsIncorrect_NotOk() {
        String pathToWriteNotOk = "src/test/base/write.csv";
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(dataToWrite, pathToWriteNotOk));
    }

    @Test
    void write_CheckPresentWriteFile_Ok() {
        String pathToWrite = "src/test/resources/write.csv";
        fileWriter.write(dataToWrite, pathToWrite);
        assertTrue(Files.exists(Path.of(pathToWrite)),
                "File does not exist in directory - \"resources\"");
    }
}
