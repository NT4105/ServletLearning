package dto;

import exceptions.ValidationError;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class RegisterDTO implements DtoBase {
    private String username;
    private String password;
    private String role;

    public RegisterDTO() {
    }

    public RegisterDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public void validate() throws ValidationException {
        List<ValidationError> errors = new ArrayList<>();

        if (username == null || username.trim().isEmpty()) {
            errors.add(new ValidationError("username", "Username cannot be empty"));
        }

        if (password == null || password.trim().isEmpty()) {
            errors.add(new ValidationError("password", "Password cannot be empty"));
        }

        if (role == null || role.trim().isEmpty()) {
            errors.add(new ValidationError("role", "Role must be selected"));
        } else {
            try {
                int roleValue = Integer.parseInt(role);
                if (roleValue != 0 && roleValue != 1) {
                    errors.add(new ValidationError("role", "Invalid role value"));
                }
            } catch (NumberFormatException e) {
                errors.add(new ValidationError("role", "Role must be a valid number"));
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}