package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitInStorage;
import core.basesyntax.service.impl.CsvWriterServiceImpl;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String RESOURCES_PASS = "src/main/resources/";
    private WriterService writerService;

    @Test
    void write_emptyReport_Ok() {
        writerService = new CsvWriterServiceImpl();
        List<FruitInStorage> fruits = new ArrayList<>();
        writerService.write(RESOURCES_PASS + "emptyTest.csv", "");
        assertEquals("", readFile(RESOURCES_PASS + "emptyTest.csv"));
    }

    @Test
    void write_validReport_Ok() {
        writerService = new CsvWriterServiceImpl();
        List<FruitInStorage> fruits = new ArrayList<>();
        fruits.add(new FruitInStorage("banana", 123));
        fruits.add(new FruitInStorage("apple", 12));
        writerService.write(RESOURCES_PASS + "validInputTest.csv", "some text");
        assertEquals("some text", readFile(RESOURCES_PASS + "validInputTest.csv"));
    }

    @Test
    void write_invalidPath_Ok() {
        writerService = new CsvWriterServiceImpl();
        List<FruitInStorage> fruits = new ArrayList<>();
        fruits.add(new FruitInStorage("banana", 123));
        fruits.add(new FruitInStorage("apple", 12));
        assertThrows(RuntimeException.class, () -> writerService.write("invalid/path.csv", "text"));
    }

    private static String readFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("From TEST\n Can't read file: " + path);
        }
    }
}
