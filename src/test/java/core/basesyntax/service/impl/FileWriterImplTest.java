package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String PATH_TO_OUTPUT_FILE = "src/test/java/resources/testOutput.csv";
    private static FileWriter fileWriter;
    
    @BeforeAll
    public static void init() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeFile_wrongPath_notOk() {
        String wrongPath = "some_file.csv";
        assertThrows(RuntimeException.class,
                () -> fileWriter
                        .writeFile("test/some_file.csv", "some content"));
    }

    @Test
    void writeFile_writeToFile_ok() {
        String content = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100";
        fileWriter.writeFile(PATH_TO_OUTPUT_FILE, content);
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        List<String> actual = readFromFile(PATH_TO_OUTPUT_FILE);
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + filePath, e);
        }
    }
}
