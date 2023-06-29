package com.infybuzz.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.infybuzz.app.entity.Student;
import com.infybuzz.app.feignclients.FeignClient;
import com.infybuzz.app.repository.StudentRepository;
import com.infybuzz.app.request.CreateStudentRequest;
import com.infybuzz.app.response.StudentResponse;

@Service
public class StudentService {
	Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	WebClient webClient;
	
	@Autowired
	FeignClient addressFeignClient;

	@Autowired
	CommonService commonService;

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());
		
		student.setAddressId(createStudentRequest.getAddressId());
		student = studentRepository.save(student);
		
		StudentResponse studentResponse = new StudentResponse(student);
		
		//studentResponse.setAddressResponse(getAddressById(student.getAddressId()));
		
		studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

		return studentResponse;
	}
	
	public StudentResponse getById (long id) {
		logger.info("Inside student getById.....");
		Student student = studentRepository.findById(id).get();
		StudentResponse studentResponse = new StudentResponse(student);
		
		//studentResponse.setAddressResponse(getAddressById(student.getAddressId()));
		
		studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));
		
		return studentResponse;
	}
////if we are getting response after calling other microservice as internal server error or 404 or exception in that case we have to return proper response that
////	time we use fallback method to return dummy response
//	@CircuitBreaker(name="addressService", fallbackMethod = "fallbackGetAddressById")
//	public AddressResponse getAddressById (long addressId) {
//		AddressResponse addressResponse=addressFeignClient.getById(addressId);
//		return addressResponse;
//	}
//
//	//it should have same method signiture as on which CircuitBreaker annotation present
//	public AddressResponse fallbackGetAddressById (long addressId, Throwable th) {
//
//		return new AddressResponse();}
	}
