package service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReaderService;

public class ReaderServiceImplTest {
    private static final String TEST_FILE_PATH = "./src/test/resources/testDatabase.csv";
    private static List<String> content = new ArrayList<>();
    private static ReaderService reader = new ReaderServiceImpl();

    @BeforeClass
    public static void beforeClass() {
        try {
            Files.createFile(Path.of(TEST_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("can't create test case database");
        }
    }

    @Test
    public void readOneLineValidDataOk() {
        content.add("Hello mates!");
        write();
        assertEquals(content, reader.read(TEST_FILE_PATH));
    }

    @Test
    public void readThreeLineValidDataOk() {
        content.add("Hello mates!");
        content.add("how it is going?");
        content.add("All great");
        write();
        assertEquals(content, reader.read(TEST_FILE_PATH));
    }

    @Test
    public void readDigitsAndSymbolsOk() {
        content.add("1234321.,()[]");
        write();
        assertEquals(content, reader.read(TEST_FILE_PATH));
    }

    @Test
    public void readFromEmptyFileOk() {
        assertEquals(content, reader.read(TEST_FILE_PATH));
    }

    @Test (expected = RuntimeException.class)
    public void readFromNullNotOk() {
        reader.read(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromNonexistingFile() {
        reader.read("./src/test/nonExisting.csv");
    }

    @After
    public void tearDown() {
        content.clear();
        try {
            Files.newBufferedWriter(Path.of(TEST_FILE_PATH), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("can't delete content of this file " + TEST_FILE_PATH);
        }
    }

    @AfterClass
    public static void afterClass() {
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
