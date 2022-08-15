package ru.turaev.user.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.user.enums.Role;
import ru.turaev.user.exception.UserNotFoundException;
import ru.turaev.user.model.User;
import ru.turaev.user.service.RegistrationService;
import ru.turaev.user.service.UserService;

@Component
@RequiredArgsConstructor
public class ContextListener implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private final RegistrationService registrationService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        User admin;
        try {
            admin = userService.findUserByUsername("admin");
        } catch (UserNotFoundException e) {
            registerAdmin();
            return;
        }

        if (!admin.isNonLocked()) {
            admin.setNonLocked(true);
        }
    }

    private void registerAdmin() {
        User newAdmin = User.builder()
                .username("admin")
                .password("admin")
                .role(Role.ADMIN)
                .isActive(true)
                .isNonLocked(true)
                .build();
        registrationService.registerUserOfAnyType(newAdmin);
    }
}