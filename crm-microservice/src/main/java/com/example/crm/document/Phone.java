package com.example.crm.document;

import lombok.Data;

@Data
public class Phone {
	private PhoneType type;
	private String area;
	private String phoneNumber;
	private String extension;
	
}
