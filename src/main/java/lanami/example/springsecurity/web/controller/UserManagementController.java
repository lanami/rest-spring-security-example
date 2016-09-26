package lanami.example.springsecurity.web.controller;

import lanami.example.springsecurity.app.service.IUserRoleService;
import lanami.example.springsecurity.app.service.IUserService;
import lanami.example.springsecurity.app.domain.User;
import lanami.example.springsecurity.web.controller.http.ResourceNotFoundException;
import lanami.example.springsecurity.Utils;
import lanami.example.springsecurity.app.domain.UserRole;
import lanami.example.springsecurity.web.controller.http.ResponseEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

/**
 * @author lanami
 * @date 2016-09-06
 */
@RestController
@RequestMapping("users")
public class UserManagementController {
    @Autowired
    IUserService userService;

    @Autowired
    IUserRoleService userRoleService;

    /**
     * Create a new user.
     *
     * POST /users
     * {first name, last name, login, password}
     * */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody @Valid User user, BindingResult bindingResult) {

        // check for field validation errors
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Utils.translate(bindingResult.getFieldErrors()));
        }

        // confirm that the username is available
        if(!userService.isUsernameUnique(user.getId(), user.getUsername())){
            //simulate bean validation error
            FieldError error = new FieldError("user", "username", "must be unique");
            bindingResult.addError(error);
            return ResponseEntity.badRequest().body(Utils.translate(bindingResult.getFieldErrors()));
        }

        // save user
        userService.saveUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(user);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(ResponseEnvelope.success(users));
    }

//    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//    @RequestMapping(method = RequestMethod.PUT)
//    public ResponseEntity saveUser(@RequestBody @Valid User user, BindingResult result) {
//
//        if (result.hasErrors()) {
//            return ResponseEntity.ok(Utils.translate(result.getFieldErrors()));
//        }
//
//        if(!userService.isUsernameUnique(user.getId(), user.getUsername())){
//            FieldError error =new FieldError("user", "username", "must be unique");
//            result.addError(error);
//            return ResponseEntity.ok(Utils.translate(result.getFieldErrors()));
//        }
//
//        userService.saveUser(user);
//
//        return ResponseEntity.ok(ResponseEnvelope.success(user));
//    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = { "/{username}" }, method = RequestMethod.GET)
    public ResponseEntity editUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) throw new ResourceNotFoundException();
        return ResponseEntity.ok(ResponseEnvelope.success(user));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = { "/{username}" }, method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody @Valid User user, @PathVariable String username, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.ok(Utils.translate(result.getFieldErrors()));
        }

        userService.updateUser(user);

        return ResponseEntity.ok(ResponseEnvelope.success(user));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = { "/{username}" }, method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.ok(ResponseEnvelope.success(username));
    }

    @ModelAttribute("roles")
    public List<UserRole> initializeProfiles() {
        return userRoleService.findAll();
    }
}
