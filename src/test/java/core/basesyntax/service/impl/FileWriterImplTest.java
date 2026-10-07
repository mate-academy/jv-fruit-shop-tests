package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static String text;
    private static List<String> expected;
    private static String fileNotExists;
    private static String fileEmpty;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
        text = "type,amount"
            + System.lineSeparator()
            + "apple,34"
            + System.lineSeparator()
            + "banana,234";
        expected = List.of("type,amount", "apple,34", "banana,234");
        fileNotExists = "src/test/resources/notExisting.csv";
        fileEmpty = "src/test/resources/writeEmpty.csv";
    }

    @Test
    void write_EmptyExistingFile_Ok() throws IOException {
        fileWriter.write(text, fileEmpty);
        List<String> actual = Files.readAllLines(Path.of(fileEmpty));
        assertEquals(expected, actual);
    }

    @Test
    void write_NotEmptyExistingFile_Ok() throws IOException {
        String fileName = "src/test/resources/writeNotEmpty.csv";
        fileWriter.write(text, fileName);
        List<String> actual = Files.readAllLines(Path.of(fileName));
        assertEquals(expected, actual);
    }

    @Test
    void write_NotExistingFile_Ok() throws IOException {
        fileWriter.write(text, fileNotExists);
        List<String> actual = Files.readAllLines(Path.of(fileNotExists));
        assertEquals(expected, actual);
    }

    @Test
    void write_InvalidPath_NotOk() {
        String directory = "src/test/resources";

        assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(text, directory),
                "Can't write to file: " + directory
        );
    }

    @AfterAll
     static void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(fileNotExists));
        Files.writeString(Path.of(fileEmpty), "");
    }
}
