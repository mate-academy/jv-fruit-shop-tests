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
    void writeToFile_successful() throws IOException {
        List<String> testList = List.of("apple,10", "banana,10");
        String address = "testReport.csv";
        writerService.writeToFile(testList, address);
        List<String> testFile = Files.readAllLines(Path.of(address));
        assertEquals("apple,10", testList.get(0));
        assertEquals("banana,10", testList.get(1));
    }

    @Test
    void writeToFile_emptyInputList_throwsException() {
        List<String> emptyList = new ArrayList<>();
        String address = "testReport.csv";
        assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile(emptyList, address);
        });
    }

    @Test
    void writeToFIle_emptyAddress_throwsException() {
        List<String> testList = new ArrayList<>();
        testList.add("banana,10");
        String address = "";
        assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile(testList, address);
        });
    }

    @Test
    void writeToFile_incorrectAddress_throwsException() {
        String incorrectAddress = "incorrectAddress";
        List<String> testList = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile(testList, incorrectAddress);
        });
    }
}
