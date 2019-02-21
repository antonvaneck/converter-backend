package mmi.service;

import mmi.domain.UnitValue;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConverterServiceTest {
    private ConverterService service;

    @Before
    public void before() {
        service = new ConverterService();
    }

    @Test
    public void convert() throws NoSuchUnitException {
        final UnitValue meter = service.toMetric(new UnitValue("foot", 1));
        assertThat(meter.getUnit(), is("meter"));
        assertThat(meter.getValue(), is(0.3048));
    }

    @Test(expected = NoSuchUnitException.class)
    public void invalidUnit() throws NoSuchUnitException {
        service.toMetric(new UnitValue("no such unit", 123));
    }
}