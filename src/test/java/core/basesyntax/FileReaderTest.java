package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.serviceimpl.FileReaderImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private FileReader fileReader;
    private Path temp;
    private final List<String> expectedList = List.of(
            "type,fruit,quantity", "b,banana,20",
            "s,banana,100", "p,banana,13", "r,apple,10");

    @BeforeEach
    void setUp() throws IOException {
        temp = Files.createTempFile("data", ".csv");
        fileReader = new FileReaderImpl();
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(temp);
    }

    @Test
    void readFromCorrectPath_Ok() throws IOException {
        Files.write(temp, List.of("type,fruit,quantity", "b,banana,20",
                "s,banana,100", "p,banana,13", "r,apple,10"), StandardCharsets.UTF_8);
        List<String> readList = fileReader.read(temp.toString());
        Assertions.assertEquals(expectedList.size(), readList.size());
    }

    @Test
    void readFromEmptyFile_NotOk() throws IOException {
        Files.write(temp, List.of(), StandardCharsets.UTF_8);
        List<String> read = fileReader.read(temp.toString());
        Assertions.assertTrue(read.isEmpty());
    }

    @Test
    void readNotExistFileThrowRuntimeEx_NotOk() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.read("src/test/resources/no_such_file"));
        Assertions.assertTrue(exception.getMessage().contains(
                "Can't get data"));
    }
}
