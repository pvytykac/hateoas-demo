package net.pvytykac.user;

import net.pvytykac.application.ApplicationController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, EntityModel<UserDto>> {

    @SuppressWarnings("unchecked")
    public UserAssembler() {
        super(UserController.class, (Class<EntityModel<UserDto>>) (Class<?>) (EntityModel.class));
    }

    public UserDto toDto(User entity) {
        return new UserDto(entity.getId(), entity.getFirstName(), entity.getLastName());
    }

    @Override
    public EntityModel<UserDto> toModel(User entity) {
        var selfLink = linkTo(methodOn(UserController.class).get(entity.getId()))
                .withSelfRel()
                //todo: include optionally based on users roles/permissions
                .andAffordances(List.of(
                        afford(methodOn(UserController.class, entity.getId()).update(entity.getId(), null)),
                        afford(methodOn(UserController.class, entity.getId()).delete(entity.getId()))
                ));

        var applicationsLink = linkTo(methodOn(ApplicationController.class, entity.getId())
                .list(entity.getId(), Pageable.unpaged(), null))
                .withRel("applications");

        return EntityModel.of(toDto(entity))
                .add(selfLink, applicationsLink);
    }
}
