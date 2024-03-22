package core.basesyntax.service.impl;

import core.basesyntax.record.Record;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.RecordMapper;
import core.basesyntax.strategy.RecordMapperStrategy;
import core.basesyntax.strategy.impl.RecordMapperStrategyImpl;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final RecordMapperStrategy recordMapperStrategy = new RecordMapperStrategyImpl();
    private static final int HEADER_INDEX = 0;
    private static final int TYPE_INDEX = 0;
    private static final int PRODUCT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int START_OF_BODY_INDEX = 1;
    private static final int MIN_LENGTH = 3;
    private static final String COMMA = ",";
    private String[] header;

    @Override
    public List<Record> convert(List<String> data) {
        String firstLine = data.get(HEADER_INDEX);
        validateHeader(firstLine);
        String productType = header[PRODUCT_INDEX];
        RecordMapper mapper = recordMapperStrategy.get(productType);
        if (mapper == null) {
            throw new IllegalArgumentException("Product type is not supported. Type="
                    + productType);
        }
        return mapper.getRecordsFromLines(data.subList(START_OF_BODY_INDEX, data.size()));
    }

    private void validateHeader(String line) {
        header = line.split(COMMA);
        if (header.length != 3) {
            throw new RuntimeException("Number of columns is invalid. Header=" + line);
        }
        if (!(header[TYPE_INDEX].equals("type") && header[QUANTITY_INDEX].equals("quantity"))) {
            throw new IllegalArgumentException("Invalid header. Header=" + line);
        }
        if (header[PRODUCT_INDEX].length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Product name shorter than 3 letters. Header="
                    + line);
        }
    }
}
