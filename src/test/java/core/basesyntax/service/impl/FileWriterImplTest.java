package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    public void write_fileNameNull_notOk() {
        String expected = "Invalid file name";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("12345",null)).getMessage());
    }

    @Test
    public void write_fileNameEmpty_notOk() {
        String expected = "Invalid file name";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("12345","")).getMessage());
    }

    @Test
    public void write_dataNull_notOk() {
        String expected = "There is no data for writing";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(null,"12345")).getMessage());
    }

    @Test
    public void read_invalidPath_notOk() {
        String invalidPath = "src/test1/resources/finalReportTest.csv";
        String expected = "Can't write data to the file";
        assertEquals(expected, assertThrows(RuntimeException.class,
                () -> fileWriter.write("12345", invalidPath)).getMessage());
    }

    @Test
    public void read_writingFile_Ok() {
        String filePath = "src/test/resources/finalReportTest.csv";
        List<String> expected;
        List<String> actual = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(i).append(System.lineSeparator());
        }
        String data = builder.toString();
        expected = List.of(data.split(System.lineSeparator()));
        try {
            fileWriter.write(data, filePath);
            actual = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            fail();
        }
        assertEquals(expected, actual);
    }
}
