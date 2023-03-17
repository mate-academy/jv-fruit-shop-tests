package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String TEST_FILE_PATH = "./src/main/resources/testDatabase.csv";
    private static List<String> content;
    private static ReaderService reader;

    @BeforeAll
    static void beforeAll() {
        reader = new ReaderServiceImpl();
        content = new ArrayList<>();
        try {
            Files.createFile(Path.of(TEST_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("can't create test case database");
        }
    }

    @Test
    void readOneLineValidDataOk() {
        content.add("Hello mates!");
        write();
        assertEquals(content, reader.read(TEST_FILE_PATH));
    }

    @Test
    void readThreeLineValidDataOk() {
        content.add("Hello mates!");
        content.add("how it is going?");
        content.add("All great");
        write();
        assertEquals(content, reader.read(TEST_FILE_PATH));
    }

    @Test
    void readDigitsAndSymbolsOk() {
        content.add("1234321.,()[]");
        write();
        assertEquals(content, reader.read(TEST_FILE_PATH));
    }

    @Test
    void readFromEmptyFileOk() {
        assertEquals(content, reader.read(TEST_FILE_PATH));
    }

    @Test
    void readFromNullNotOk() {
        assertThrows(RuntimeException.class, () -> {
            reader.read(null);
        });
    }

    @Test
    void readFromNonexistingFile() {
        assertThrows(RuntimeException.class, () -> {
            reader.read("./src/main/nonExisting.csv");
        });
    }

    @AfterEach
    void tearDown() {
        content.clear();
        try {
            Files.newBufferedWriter(Path.of(TEST_FILE_PATH), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("can't delete content of this file " + TEST_FILE_PATH);
        }
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.delete(Path.of(TEST_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("can't delete this file " + TEST_FILE_PATH);
        }
    }

    private void write() {
        String data = content.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        try {
            Files.write(Path.of(TEST_FILE_PATH), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("can't write this content " + content
                    + " to this file " + TEST_FILE_PATH);
        }
    }
}
