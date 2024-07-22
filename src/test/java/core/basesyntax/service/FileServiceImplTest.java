package core.basesyntax.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceImplTest {

    @Test
    @DisplayName("Read from file test")
    void readFile_ok() {
        FileService fileService = new FileServiceImpl();
        List<String> actualReadLines = fileService.read("fruits.csv");
        List<String> expectedReadLines = new ArrayList<>();
        expectedReadLines.add("type,fruit,quantity");
        expectedReadLines.add("b,banana,20");
        expectedReadLines.add("b,apple,100");
        expectedReadLines.add("s,banana,100");
        expectedReadLines.add("p,banana,13");
        expectedReadLines.add("r,apple,10");
        expectedReadLines.add("p,apple,20");
        expectedReadLines.add("p,banana,5");
        expectedReadLines.add("s,banana,50");
        assertEquals(actualReadLines, expectedReadLines);
    }

    @Test
    @DisplayName("Read null file test")
    void readNullFile_notOk() {
        FileService fileService = new FileServiceImpl();
        assertThrows(NullPointerException.class, () -> fileService.read(null));
    }

    @Test
    @DisplayName("Read non-existent file test")
    void readNonExistentFile_notOk() {
        FileService fileService = new FileServiceImpl();
        assertThrows(RuntimeException.class, () -> fileService.read("invalidFile.csv"));
    }

    @Test
    @DisplayName("Write to file test")
    void writeToFile_ok() {
        FileService fileService = new FileServiceImpl();
        String expectedContent = "apple,10";
        fileService.writeToFile(expectedContent, "resultTest.csv");
        List<String> readContent = fileService.read("resultTest.csv");
        String actualContent = String.join("", readContent);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Write to null file test")
    void writeToNullFile_notOk() {
        FileService fileService = new FileServiceImpl();
        assertThrows(NullPointerException.class, () -> fileService.writeToFile("fruit,10", null));
    }

}