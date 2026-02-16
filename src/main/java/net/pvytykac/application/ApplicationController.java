package net.pvytykac.application;

import lombok.RequiredArgsConstructor;
import net.pvytykac.authorization.IsApplicationViewer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//todo: finish
@RestController
@RequestMapping(path = "/v1/users/{userId}/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationAssembler applicationAssembler;

    @GetMapping
    @IsApplicationViewer
    public PagedModel<EntityModel<ApplicationDto>> list(@PathVariable String userId,
                                                        Pageable pageable, PagedResourcesAssembler<Application> pagedAssembler) {
        return pagedAssembler.toModel(applicationService.getApplications(userId, pageable), applicationAssembler);
    }

    @GetMapping("/{applicationId}")
    @IsApplicationViewer
    public EntityModel<ApplicationDto> get(@PathVariable String userId, @PathVariable String applicationId) {
        return applicationService.getApplication(userId, applicationId)
                .map(applicationAssembler::toModel)
                .orElseThrow();
    }

}
