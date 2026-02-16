package net.pvytykac.application;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ApplicationAssembler extends RepresentationModelAssemblerSupport<Application, EntityModel<ApplicationDto>> {

    public ApplicationAssembler() {
        super(ApplicationController.class, (Class<EntityModel<ApplicationDto>>) (Class<?>) EntityModel.class);
    }

    public ApplicationDto toDto(Application application) {
        return new ApplicationDto(application.getId(), application.getName());
    }

    @Override
    public EntityModel<ApplicationDto> toModel(Application entity) {
        var selfLink = linkTo(methodOn(ApplicationController.class, entity.getOwner().getId())
                .get(entity.getOwner().getId(), entity.getId())).withSelfRel();

        return EntityModel.of(toDto(entity))
                .add(selfLink);
    }
}
