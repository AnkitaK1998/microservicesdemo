package com.infybuzz.app.feignclients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infybuzz.app.response.AddressResponse;

@org.springframework.cloud.openfeign.FeignClient(value = "api-gateway")
public interface FeignClient {

	@GetMapping("/address-service/api/address/getById/{id}")
	public AddressResponse getById(@PathVariable long id);
	
}
