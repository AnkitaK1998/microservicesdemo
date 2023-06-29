package com.infybuzz.app.controller;

import com.infybuzz.app.request.CreateAddressRequest;
import com.infybuzz.app.response.AddressResponse;
import com.infybuzz.app.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RefreshScope
public class AddressController {

	@Autowired
	AddressService addressService;
	
	@Value("${address.test}")
	private String test;

	@PostMapping("/create")
	public AddressResponse createAddress (@RequestBody CreateAddressRequest createAddressRequest) {
		return addressService.createAddress(createAddressRequest);
	}
	
	@GetMapping("/getById/{id}")
	public AddressResponse getById(@PathVariable long id) {
		return addressService.getById(id);
	}
	
	@GetMapping("/test")
	public String test () {
		return test;
	}
	
}
