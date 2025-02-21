package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String FILE_FOR_FIRST_TEST = "emptyFile.csv";
    private static final String FILE_FOR_SECOND_TEST = "fileWithHello.csv";

    private List<String> readFileContents(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data from file " + fileName);
        }
    }

    @BeforeAll
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_correctWritingToFileIfTextIsEmpty_ok() {
        String fileName = FILE_FOR_FIRST_TEST;
        String text = "";

        fileWriter.writeToFile(fileName, text);
        List<String> expected = new ArrayList<>();

        List<String> actual = readFileContents(fileName);

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void writeToFile_correctWritingToFileIfTextIsNotEmpty_ok() {
        String fileName = FILE_FOR_SECOND_TEST;
        String text = "Hello";

        fileWriter.writeToFile(fileName, text);
        List<String> actual = readFileContents(fileName);

        List<String> expected = List.of("Hello");

        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_fileFound_notOk() {
        String fileName = "";
        String text = "";

        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeToFile(fileName,text);
        });
    }
}
