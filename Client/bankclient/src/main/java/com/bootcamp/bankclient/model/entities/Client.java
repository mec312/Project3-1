package com.bootcamp.bankclient.model.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@Document(value = "client")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Client {
	@Id
	private String id;
	private String name;

	private String clientIdType;

	@Indexed(unique = true)
	private String clientIdNumber;

	@Email
	private String email;
	private String phone;
	private String address;

	private ClientType clientType;
}
