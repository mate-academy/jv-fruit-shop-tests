package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;

    @BeforeAll
    static void setup() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_successful() {
        List<String> testList = List.of("apple,10", "banana,10");
        String address = "testReport.csv";
        try {
            List<String> testFile = Files.readAllLines(Path.of(address));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writerService.writeToFile(testList, address);
        assertEquals("apple,10", testList.get(0));
        assertEquals("banana,10", testList.get(1));
    }

    @Test
    void writeToFile_emptyInputList_NotOk() {
        List<String> emptyList = new ArrayList<>();
        String address = "testReport.csv";
        assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile(emptyList, address);
        });
    }

    @Test
    void writeToFIle_emptyAddress_NotOk() {
        List<String> testList = new ArrayList<>();
        testList.add("banana,10");
        String address = "";
        assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile(testList, address);
        });
    }

    @Test
    void writeToFile_throwException_Ok() {
        String incorrectAddress = "incorrectAddress";
        List<String> testList = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile(testList, incorrectAddress);
        });
    }
}
