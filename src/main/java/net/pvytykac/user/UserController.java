package net.pvytykac.user;

import lombok.RequiredArgsConstructor;
import net.pvytykac.authorization.IsUserManager;
import net.pvytykac.authorization.IsUserViewer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/users")
@ExposesResourceFor(UserDto.class)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    @GetMapping
    @IsUserViewer
    public PagedModel<EntityModel<UserDto>> list(Pageable pageable, PagedResourcesAssembler<User> pagedAssembler) {
        return pagedAssembler.toModel(userService.getUsers(pageable), userAssembler);
    }

    @PostMapping
    @IsUserManager
    public EntityModel<UserDto> create(@RequestBody @Validated UserDto payload) {
        return userAssembler.toModel(userService.createUser(payload.getFirstName(), payload.getLastName()));
    }

    @GetMapping("/{userId}")
    @IsUserViewer
    public EntityModel<UserDto> get(@PathVariable String userId) {
        return userService.getUser(userId)
                .map(userAssembler::toModel)
                .orElseThrow();
    }

    @PutMapping("/{userId}")
    @IsUserManager
    public EntityModel<UserDto> update(@PathVariable String userId, @RequestBody @Validated UserDto payload) {
        return userService.updateUser(userId, payload.getFirstName(), payload.getLastName())
                .map(userAssembler::toModel)
                .orElseThrow();
    }

    @DeleteMapping("/{userId}")
    @IsUserManager
    public EntityModel<UserDto> delete(@PathVariable String userId) {
        return userService.deleteUser(userId)
                .map(userAssembler::toModel)
                .orElseThrow();
    }

}
