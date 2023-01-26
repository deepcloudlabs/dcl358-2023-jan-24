package com.example.crm.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="customers")
public class CustomerDocument {
	@Id
	private String identity;
	private String fullname;
	private String email;
	private List<Address> addresses;
	private List<Phone> phones;
}
