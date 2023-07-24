package service.impl;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReaderService;
import service.WriterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriterServiceImplTest {
    private static final String TEST_FILE = "src/main/resources/test.csv";
    private WriterService writer;
    private FruitStorageDao fruitStorageDao;
    private ReaderService reader;
    private Path testFilePath;

    @BeforeEach
    void setUp() {
        writer = new WriterServiceImpl();
        fruitStorageDao = new FruitStorageDaoImpl();
        reader = new ReaderServiceImpl();
        try {
            testFilePath = Files.createTempFile("test", ".csv");
        } catch (IOException e) {
            throw new RuntimeException("Can't create file with path " + testFilePath, e);
        }
    }

    @Test
    void writeReport_ok() {
        List<String> expected = List.of("fruit,quantity", "banana,100", "apple,25");

        fruitStorageDao.set("banana", 100);
        fruitStorageDao.set("apple", 25);

        writer.write(expected, TEST_FILE);

        List<String> actual = new ArrayList<>();
        actual.add("fruit,quantity");
        actual.addAll(reader.read(TEST_FILE));

        assertEquals(expected, actual,
                "Actual and expected files content should be equals");
    }

    @AfterEach
    void tearDown() {
        fruitStorageDao.set("banana", 0);
        fruitStorageDao.set("apple", 0);
        try {
            Files.deleteIfExists(Path.of(TEST_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file" + TEST_FILE, e);
        }
        try {
            Files.deleteIfExists(testFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + testFilePath, e);
        }
    }
}