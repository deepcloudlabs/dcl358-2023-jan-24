package com.example.crm.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.document.CustomerDocument;
import com.example.crm.service.CustomerReactiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CrmRestController {
	private final CustomerReactiveService customerReactiveService;

	public CrmRestController(CustomerReactiveService customerReactiveService) {
		this.customerReactiveService = customerReactiveService;
	}

	@GetMapping("{identity}")
	public Mono<CustomerDocument> findCustomerByIdentity(@PathVariable String identity) {
		return customerReactiveService.findCustomerById(identity);
	}

	@GetMapping(params = { "page", "size" })
	public Flux<CustomerDocument> findAllCustomers(@RequestParam int page, @RequestParam int size) {
		return customerReactiveService.findAllByPage(page, size);
	}

	@PostMapping
	public Mono<CustomerDocument> acquireCustomer(@RequestBody CustomerDocument customer) {
		return customerReactiveService.acquire(customer);
	}

	@PutMapping("{identity}")
	public Mono<CustomerDocument> updateCustomer(@PathVariable String identity,@RequestBody CustomerDocument customer) {
		return customerReactiveService.updateCustomer(identity,customer);
	}
	
	@DeleteMapping("{identity}")
	public Mono<CustomerDocument> releaseCustomerByIdentity(@PathVariable String identity) {
		return customerReactiveService.releaseCustomerById(identity);
	}

}
