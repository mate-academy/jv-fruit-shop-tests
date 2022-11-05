package core.basesyntax.service;

public interface CsvReportGenerator<T,L> {
    T generateCsvReport(L data);
}
