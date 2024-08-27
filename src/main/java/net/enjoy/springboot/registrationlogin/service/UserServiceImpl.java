package net.enjoy.springboot.registrationlogin.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.enjoy.springboot.registrationlogin.dto.RoleDto;
import net.enjoy.springboot.registrationlogin.dto.UserDto;
import net.enjoy.springboot.registrationlogin.entity.Role;
import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.repository.RoleRepository;
import net.enjoy.springboot.registrationlogin.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Trouver ou créer les rôles
        Set<Role> roles = userDto.getRoles().stream()
                .map(roleDto -> roleRepository.findByName(roleDto.getName()))
                .collect(Collectors.toSet());
        
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        if (user.getName() != null && !user.getName().isEmpty()) {
            String[] nameParts = user.getName().split(" ", 2);
            userDto.setFirstName(nameParts[0]);
            userDto.setLastName(nameParts.length > 1 ? nameParts[1] : "");
        } else {
            userDto.setFirstName("");
            userDto.setLastName("");
        }
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toSet()));
        return userDto;
    }

    @Override
    public List<String> getAdminEmails() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            throw new IllegalStateException("Le rôle ROLE_ADMIN n'existe pas.");
        }

        List<User> admins = userRepository.findByRoles(adminRole);
        return admins.stream()
                    .map(User::getEmail)
                    .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return convertEntityToDto(user);
    }
}
