package com.infybuzz.app.service;

import com.infybuzz.app.feignclients.FeignClient;
import com.infybuzz.app.response.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    Logger logger = LoggerFactory.getLogger(CommonService.class);
    long conut=1;
    @Autowired
    FeignClient addressFeignClient;
  //  if we are getting response after calling other microservice as internal server error or 404 or exception in that case we have to return proper response that
//	time we use fallback method to return dummy response
    @CircuitBreaker(name="addressService", fallbackMethod = "fallbackGetAddressById")
    public AddressResponse getAddressById (long addressId) {
       logger.info("count="+conut);
       conut++;
        AddressResponse addressResponse=addressFeignClient.getById(addressId);
        return addressResponse;
    }

    //it should have same method signiture as on which CircuitBreaker annotation present
    public AddressResponse fallbackGetAddressById (long addressId, Throwable th) {
        logger.error("Error= "+th);
        return new AddressResponse();}

}
