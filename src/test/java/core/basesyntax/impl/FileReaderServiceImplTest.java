package core.basesyntax.impl;

import core.basesyntax.service.FileReaderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FileReaderServiceImplTest {
   private static final String inputFileName = "src/test/java/resources/testFruitStore.csv";
   private static FileReaderServiceImpl fileReaderService;


    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readFromFile_CorrectPath_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        List<String> actual = fileReaderService.readFromFile(inputFileName);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void readFromFile_Null_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> fileReaderService.readFromFile(null));
    }

    @Test
    void readFromFile_UncorrectedPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> fileReaderService.readFromFile("badPath"));
    }


}