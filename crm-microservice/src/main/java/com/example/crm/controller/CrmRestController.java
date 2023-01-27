package com.example.crm.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.document.Address;
import com.example.crm.document.CustomerDocument;
import com.example.crm.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("customers")
public class CrmRestController {
	private final CustomerService customerService;

	public CrmRestController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("{identity}")
	public CustomerDocument getCustomerDocument(@PathVariable String identity) {
		return customerService.findCustomerByIdentity(identity);
	}

	@PostMapping
	public CustomerDocument addCustomerDocument(@RequestBody CustomerDocument customer) throws JsonProcessingException {
		return customerService.addCustomer(customer);
	}

	@PostMapping("{identity}/address")
	public Address addAddress(@PathVariable String identity, @RequestBody Address address)
			throws JsonProcessingException {
		return customerService.addAddress(identity, address);
	}

	@DeleteMapping("{identity}")
	public CustomerDocument removeCustomer(@PathVariable String identity) throws JsonProcessingException {
		return customerService.removeByIdentity(identity);
	}
}
