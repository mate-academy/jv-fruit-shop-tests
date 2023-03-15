package com.mate.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import com.mate.fruitshop.service.ReaderService;
import java.util.ArrayList;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void read_Empty_Ok() {
        assertEquals(new ArrayList<>(), readerService.read("src/test/resources/empty.csv"));
    }

    @Test
    public void read_HundredLines_Ok() {
        ArrayList<String> expected = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            expected.add("line" + i);
        }
        assertEquals(expected, readerService.read("src/test/resources/HundredLines.csv"));
    }

    @Test(expected = RuntimeException.class)
    public void read_NonExistentFile_NotOk() {
        readerService.read("");
    }
}
