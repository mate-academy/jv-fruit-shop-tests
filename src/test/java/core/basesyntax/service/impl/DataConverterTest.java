package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.service.DataConverter;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private static final DataConverter converter = new DataConverterImpl();
    private static final List<String> tooSmallReport = List.of(
            "header1,header2,header3");
    private static final List<String> tooSmallLinePartsReport = List.of(
            "header1,header2", "p,fruit", "p,fruit");
    private static final List<String> tooManyLinePartsReport = List.of(
            "header1,header2,header3,header4", "p,fruit,20,22",
            "p,fruit,30,32", "p,fruit,45,89");
    private static final List<String> nullFruitNameReport = List.of(
            "header1,header2,header3", "p,null,20");
    private static final List<String> emptyFruitNameReport = List.of(
            "header1,header2,header3", "p, ,20");
    private static final List<String> nullQuantityReport = List.of(
            "header1,header2,header3", "p,fruit,null");
    private static final List<String> emptyQuantityReport = List.of(
            "header1,header2,header3", "p,fruit, ");
    private static final List<String> invalidFormatQuantityReport = List.of(
            "header1,header2,header3", "p,fruit,price");
    private static final List<String> negativeQuantityReport = List.of(
            "header1,header2,header3", "p,fruit,-1");
    private static final List<String> normalReport = List.of(
            "header1,header2,header3", "p,fruit1,0",
            "p,fruit2,45", "r,fruit1,1");

    @Test
    public void convertToTransaction_nullReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(null));
    }

    @Test
    public void convertToTransaction_emptyReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(
                        Collections.singletonList("")));
    }

    @Test
    public void convertToTransaction_tooSmallReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(
                        tooSmallReport));
    }

    @Test
    public void convertToTransaction_tooSmallLinePartsReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(
                        tooSmallLinePartsReport));
    }

    @Test
    public void convertToTransaction_tooManyLinePartsReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(
                        tooManyLinePartsReport));
    }

    @Test
    public void convertToTransaction_nullFruitName_notOk() {
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(
                        nullFruitNameReport));
    }

    @Test
    public void convertToTransaction_emptyFruitName_notOk() {
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(
                        emptyFruitNameReport));
    }

    @Test
    public void convertToTransaction_nullQuantityReport_notOk() {
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(
                        nullQuantityReport));
    }

    @Test
    public void convertToTransaction_emptyQuantityReport_notOk() {
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(
                        emptyQuantityReport));
    }

    @Test
    public void convertToTransaction_invalidFormatQuantityReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(
                        invalidFormatQuantityReport));
    }

    @Test
    public void convertToTransaction_negativeQuantityReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(
                        negativeQuantityReport));
    }

    @Test
    public void convertToTransaction_normalReport_Ok() {
        assertDoesNotThrow(() -> converter.convertToTransaction(normalReport));
    }
}
