package core.basesyntax.service;

import core.basesyntax.service.base.WriterAndReadServiceTestBase;

public class WriteReadImpTest
        extends WriterAndReadServiceTestBase<CsvFileWriterService, CsvFileReaderService> {
    @Override
    protected CsvFileWriterService createWriterInstance() {
        return new CsvFileWriterService();
    }

    @Override
    protected CsvFileReaderService createReaderInstance() {
        return new CsvFileReaderService();
    }
}
