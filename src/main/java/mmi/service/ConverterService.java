package mmi.service;

import mmi.domain.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.Function;

@Service
public class ConverterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterService.class);
    private static final String FOOT = "foot";
    private static final String METER = "meter";
    private static final String FAHRENHEIT = "fahrenheit";
    private static final String CELSIUS = "celsius";
    private static final String SQUARE_METER = "sqm";
    private static final String SQUARE_YARD = "sqyd";
    private static final String GRAM = "gram";
    private static final String OUNCE = "ounce";
    private static final String KMS = "kph";
    private static final String MPH = "mph";
    private static final double KPH_TO_MPH = 0.621372;
    private static final double SQM_TO_SQYD = 1.19599;
    private static final double GRAM_TO_OUNCE = 0.035274;
    private static final double METER_TO_FOOT = 3.28084;
    private final HashMap<String, Function<UnitValue, UnitValue>> functionMap;

    public ConverterService() {
        this.functionMap = new HashMap<>(10);
        functionMap.put(METER, unitValue -> new UnitValue(FOOT, unitValue.getValue() * METER_TO_FOOT));
        functionMap.put(FOOT, unitValue -> new UnitValue(METER, unitValue.getValue() / METER_TO_FOOT));
        functionMap.put(FAHRENHEIT, unitValue -> new UnitValue(CELSIUS, (unitValue.getValue() - 32) * 5./9.));
        functionMap.put(CELSIUS, unitValue -> new UnitValue(FAHRENHEIT, unitValue.getValue() * 9 / 5 + 32));
        functionMap.put(GRAM, unitValue -> new UnitValue(OUNCE, unitValue.getValue() * GRAM_TO_OUNCE));
        functionMap.put(OUNCE, unitValue -> new UnitValue(GRAM, unitValue.getValue() / GRAM_TO_OUNCE));
        functionMap.put(SQUARE_METER, unitValue -> new UnitValue(SQUARE_YARD, unitValue.getValue() * SQM_TO_SQYD));
        functionMap.put(SQUARE_YARD, unitValue -> new UnitValue(SQUARE_METER, unitValue.getValue() / SQM_TO_SQYD));
        functionMap.put(KMS, unitValue -> new UnitValue(MPH, unitValue.getValue() * KPH_TO_MPH));
        functionMap.put(MPH, unitValue -> new UnitValue(KMS, unitValue.getValue() / KPH_TO_MPH));
    }

    public UnitValue toMetric(UnitValue unitValue) throws NoSuchUnitException {
        LOGGER.debug("Convert {}", unitValue);
        final String unit = unitValue.getUnit();
        if (!functionMap.containsKey(unit)) {
            throw new NoSuchUnitException(unit);
        }

        final UnitValue convertedValue = functionMap.get(unit).apply(unitValue);
        LOGGER.info("Converted {} to {}", unitValue, convertedValue);

        return convertedValue;
    }
}
