package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Operation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDataParserTest {
    private static FruitDataParser parser;
    private static final String DATA_PATH = "src\\resources\\fileForParserCheck.csv";
    private static final List<FruitTransactionDto> EXPECTED_PARSED_DATA = new ArrayList<>();
    private static final List<FruitTransactionDto> EXPECTED_EMPTY_DATA = Collections.emptyList();
    private static final List<String> EMPTY_DATA = Collections.emptyList();
    private static final FruitTransactionDto FIRST_DTO =
            new FruitTransactionDto(Operation.BALANCE, "banana", 20);
    private static final FruitTransactionDto SECOND_DTO =
            new FruitTransactionDto(Operation.BALANCE, "apple", 100);
    private static final FruitTransactionDto THIRD_DTO =
            new FruitTransactionDto(Operation.SUPPLY, "banana", 100);

    private static List<String> RAW_DATA;

    @BeforeAll
    static void setUp() throws IOException {
        RAW_DATA = Files.readAllLines(Path.of(DATA_PATH));

        EXPECTED_PARSED_DATA.add(FIRST_DTO);
        EXPECTED_PARSED_DATA.add(SECOND_DTO);
        EXPECTED_PARSED_DATA.add(THIRD_DTO);
    }

    @BeforeEach
    void setUpParser() {
        parser = new FruitDataParser();
    }

    @Test
    void parse_ParseValidData_Ok() {
        List<FruitTransactionDto> actual = parser.parse(RAW_DATA);
        assertEquals(EXPECTED_PARSED_DATA, actual);
    }

    @Test
    void parse_ParseEmptyData_Ok() {
        List<FruitTransactionDto> actual = parser.parse(EMPTY_DATA);
        assertEquals(EXPECTED_EMPTY_DATA, actual);
    }

}
