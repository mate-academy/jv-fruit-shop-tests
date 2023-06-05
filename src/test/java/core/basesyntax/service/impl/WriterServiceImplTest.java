package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class WriterServiceImplTest {
    private final String OUTPUT_DATA_FILE_TEST = "src/test/resources/testOutput.csv";
    private static WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_wrongPathToFile_notOk() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> writerService.writeToFile("test/src/test/test.csv", "content")
        );
    }

    @Test
    public void writeToFile_validData_ok() {
        String content = "fruit,quantity\n"
                + "apple,15\n"
                + "orange,20\n";
        writerService.writeToFile(OUTPUT_DATA_FILE_TEST, content);
        List<String> expected = List.of(
                "fruit,quantity",
                "apple,15",
                "orange,20"
        );
        List<String> actual = readFromFile(OUTPUT_DATA_FILE_TEST);
        Assertions.assertIterableEquals(expected, actual);
    }

    private List<String> readFromFile(String pathToFile) {
        try {
            return Files.readAllLines(Path.of(pathToFile));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from test file = " + pathToFile, e);
        }
    }
}
