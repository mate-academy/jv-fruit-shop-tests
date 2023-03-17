package com.mate.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import com.mate.fruitshop.service.ReaderService;
import java.util.ArrayList;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final int LINES_COUNT = 100;
    private static final String TEST_FILE_NAME = "src/test/resources/HundredLines.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/empty.csv";
    private static ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void read_Empty_Ok() {
        assertEquals(new ArrayList<>(), readerService.read(EMPTY_FILE_NAME));
    }

    @Test
    public void read_HundredLines_Ok() {
        ArrayList<String> expected = new ArrayList<>();
        for (int i = 1; i <= LINES_COUNT; i++) {
            expected.add("line" + i);
        }
        assertEquals(expected, readerService.read(TEST_FILE_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void read_NonExistentFile_NotOk() {
        readerService.read("");
    }
}
