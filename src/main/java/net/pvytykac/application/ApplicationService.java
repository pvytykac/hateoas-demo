package net.pvytykac.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pvytykac.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public Page<Application> getApplications(String userId, Pageable pageable) {
        log.debug("Listing applications owned by user '{}'", userId);

        userRepository.findById(userId).orElseThrow();
        return applicationRepository.findAllByOwnerId(userId, pageable);
    }

    public Optional<Application> getApplication(String userId, String applicationId) {
        log.debug("Looking up application '{}' owned by user '{}'", applicationId, userId);

        return applicationRepository.findById(applicationId)
                .filter(application -> userId.equals(application.getOwner().getId()));
    }

}
