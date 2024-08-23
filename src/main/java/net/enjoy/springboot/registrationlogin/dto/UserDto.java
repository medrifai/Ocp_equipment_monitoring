package net.enjoy.springboot.registrationlogin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Long id;

    @NotEmpty(message = "Le prénom ne doit pas être vide")
    private String firstName;

    @NotEmpty(message = "Le nom de famille ne doit pas être vide")
    private String lastName;

    @NotEmpty(message = "L'email ne doit pas être vide")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotEmpty(message = "Le mot de passe ne doit pas être vide")
    private String password;
}
