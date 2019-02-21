package mmi.controller;

import mmi.domain.UnitValue;
import mmi.service.ConverterService;
import mmi.service.NoSuchUnitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ConverterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterController.class);
    private final ConverterService converterService;

    public ConverterController(ConverterService converterService) {
        this.converterService = converterService;
    }

    @PostMapping
    @RequestMapping("convert")
    @CrossOrigin(origins = "http://localhost:4200")
    public UnitValue convert(@RequestBody UnitValue unitValue) throws NoSuchUnitException {
        LOGGER.debug("Request conversion for {}", unitValue);
        final UnitValue convertedValue = converterService.toMetric(unitValue);
        LOGGER.info("{} converted to {}", unitValue, convertedValue);
        return convertedValue;
    }
}
