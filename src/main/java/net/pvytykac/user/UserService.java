package net.pvytykac.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public Page<User> getUsers(Pageable pageable) {
        log.debug("Listing users");

        return userRepository.findAll(pageable);
    }

    public Optional<User> getUser(String id) {
        log.debug("Looking up user '{}'", id);

        return userRepository.findById(id);
    }

    public User createUser(String firstName, String lastName) {
        log.info("Creating user '{} {}'", firstName, lastName);

        return userRepository.save(User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build());
    }

    public Optional<User> updateUser(String id, String firstName, String lastName) {
        return userRepository.findByIdForUpdate(id)
                .map(user -> {
                    log.info("Updating user '{}' to '{} {}'", id, firstName, lastName);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);

                    return user;
                });
    }

    public Optional<User> deleteUser(String id) {
        return userRepository.findByIdForUpdate(id)
                .map(user -> {
                    log.info("Deleting user '{}'", id);
                    userRepository.delete(user);

                    return user;
                });
    }

}
