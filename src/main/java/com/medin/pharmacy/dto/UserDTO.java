package com.medin.pharmacy.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDTO extends BaseDomainDTO {

	private String userName;

	private String password;

	private String email;

	private String status;

}
