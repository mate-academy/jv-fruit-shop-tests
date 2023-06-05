package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String PATH_TO_TEST_OUTPUT_FILE = "src\\test\\resources\\testOutput.csv";
    private FileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeToFile_wrongPathToFile_notOk() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> fileWriter.writeToFile("test/folder/file.csv", "text")
        );
    }

    @Test
    void writeToFile_ok() {
        String content = "fruit,quantity\n"
                         + "apple,50\n"
                         + "banana,13";
        fileWriter.writeToFile(PATH_TO_TEST_OUTPUT_FILE, content);
        List<String> expected = List.of(
                "fruit,quantity",
                "apple,50",
                "banana,13"
        );
        List<String> actual = readFromFile(PATH_TO_TEST_OUTPUT_FILE);
        Assertions.assertIterableEquals(expected, actual);
    }

    private List<String> readFromFile(String pathToFile) {
        try {
            return Files.readAllLines(Path.of(pathToFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + pathToFile, e);
        }
    }
}
