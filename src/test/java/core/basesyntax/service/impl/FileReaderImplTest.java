package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    @BeforeEach
    void setUp() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test_data.txt"))) {
            writer.write("fruit,quantity\nb,apple,50");
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("test_data.txt"));
        Files.deleteIfExists(Path.of("output.txt"));
    }

    @Test
    void read_validFile_returnsDataWithoutHeader() {
        FileReader fileReader = new FileReaderImpl();
        List<String> result = fileReader.read("test_data.txt");
        assertEquals(1, result.size());
        assertEquals("b,apple,50", result.get(0));
    }
}
