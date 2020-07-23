package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	@GetMapping("/filtering")
	public MappingJacksonValue retrieveSomeBean (@RequestParam("filter") Optional<String> filtersQuery) {
		SomeBean somebean = new SomeBean("Value 1", "Value 2", "Value 3");
		
		return createFilterMapping(somebean, verifyFilterQueryParam(filtersQuery));
	}

	@GetMapping("/filtering-list")
	public MappingJacksonValue retrieveSomeBeanList (@RequestParam("filter") Optional<String> filtersQuery) {
		List<SomeBean> someBeanList = Arrays.asList(new SomeBean("Value 1", "Value 2", "Value 3"), new SomeBean("Value 4", "Value 5", "Value 6"), new SomeBean("Value 7", "Value 8", "Value 9"));
		return createFilterMapping(someBeanList, verifyFilterQueryParam(filtersQuery));
	}
	
	private MappingJacksonValue createFilterMapping(Object value, String ...fields) {
		MappingJacksonValue mapping = new MappingJacksonValue(value);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(fields);
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter );
		
		mapping.setFilters(filters);
		return mapping;
	}
	
	private String[] verifyFilterQueryParam(Optional<String> filtersQuery) {
		if (filtersQuery.isPresent()) {
			return filtersQuery.get().split(",");
		}
		return new String[]{""};
	}

}
