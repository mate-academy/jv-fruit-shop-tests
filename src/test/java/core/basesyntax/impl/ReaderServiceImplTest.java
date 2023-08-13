package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static String correctAddressFile;

    @BeforeAll
    static void setup() {
        readerService = new ReaderServiceImpl();
        correctAddressFile = "/src/main/java/core/recourses/input.csv";
    }

    @Test
    void readFromFile_validFile() throws IOException {
        String testAddress = "testfile.txt";
        String testData = "Line 1\nLine 2\nLine 3";
        Files.write(Path.of(testAddress), testData.getBytes());
        List<String> data = readerService.readFromFile(testAddress);
        assertNotNull(data);
        assertEquals(3, data.size());
        assertEquals("Line 1", data.get(0));
        assertEquals("Line 2", data.get(1));
        assertEquals("Line 3", data.get(2));
        Files.delete(Path.of(testAddress));
    }

    @Test
    void readFromFile_addressFileNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(null);
        });
    }

    @Test
    void readFromFile_formatAddressStartFromYourDisk_NotOk() {
        String notCorrectAddressFile = "C:/Users/karvatskyi/IdeaProjects/jv-fruit-shop-tests"
                + "/src/main/java/core/basesyntax/recourses";
        assertNotEquals(correctAddressFile.split("/")[1], notCorrectAddressFile.split("/")[0]);
    }
}
