package com.fruitshop.servicesimpl;

import com.fruitshop.services.FileCsvReader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileCsvReaderImplTest {

    private final String filePath = "src/test/resources/testFiletoRead1.csv";
    private List<String> expected;
    private FileCsvReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileCsvReaderImpl();
        expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "b,kivi,100", "s,banana,100",
                "p,kivi,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,kivi,5", "s,kivi,20", "s,banana,50");
    }

    @Test
    void readFromFile_ok() {
        List<String> actual = reader.readFromFile(filePath);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readFromFile_ThrowOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> reader.readFromFile("src/test/resources/wrongName.csv"));
    }
}
