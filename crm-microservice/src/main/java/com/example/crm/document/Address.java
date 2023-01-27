package com.example.crm.document;

import lombok.Data;

@Data
public class Address {
	private AdressType type;
	private String street;
	private String city;
	private String country;
}
