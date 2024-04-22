package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataConverterImplTest {

    private DataConverter dataConverter;
    private List<FruitTransaction> checkTransList;
    private List<String> checkTransStrings;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
        checkTransList = List.of(

        )
    }

    @Test
    void emptyFile_NotOK(){

    }

    @Test
    void wrongFileFormat_NotOK() {

    }

    @Test
    void validFile_OK() {

    }


}