package service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    @Test
    void readFromWrongFilePath_notOk() {
        FileReaderService fileReaderService = new FileReaderService();
        String wrongFilePath = "src/test/java/resources/File.log";

        Assertions.assertThrows(RuntimeException.class, () -> fileReaderService
                .readFromFile(wrongFilePath));
    }

    @Test
    void readFromFile_ok() {
        FileReaderService fileReaderService = new FileReaderService();
        String filePath = "src/test/java/resources/File.csv";
        List<String> expectedList = List.of("b,banana,20", "b,apple,100");

        List<String> actualList = fileReaderService
                .readFromFile(filePath);

        Assertions.assertEquals(expectedList.get(0), actualList.get(0));
        Assertions.assertEquals(expectedList.get(1), actualList.get(1));
    }
}
