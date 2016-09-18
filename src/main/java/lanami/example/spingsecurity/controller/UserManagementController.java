package lanami.example.spingsecurity.controller;

import lanami.example.spingsecurity.service.IUserRoleService;
import lanami.example.spingsecurity.service.IUserService;
import lanami.example.spingsecurity.domain.User;
import lanami.example.spingsecurity.controller.http.ResourceNotFoundException;
import lanami.example.spingsecurity.Utils;
import lanami.example.spingsecurity.domain.UserRole;
import lanami.example.spingsecurity.controller.http.ResponseEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-06
 */
@RestController
@SessionAttributes("roles")
@RequestMapping("secure/usermanagement/users")
public class UserManagementController {
    @Autowired
    IUserService userService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    MessageSource messageSource;

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(ResponseEnvelope.success(users));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity saveUser(@RequestBody @Valid User user, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.ok(ResponseEnvelope.failure(Utils.translate(result.getFieldErrors())));
        }

        if(!userService.isUsernameUnique(user.getId(), user.getUsername())){
            FieldError error =new FieldError("user", "username", messageSource.getMessage("Unique.user.username", new String[]{user.getUsername()}, Locale.getDefault()));
            result.addError(error);
            return ResponseEntity.ok(ResponseEnvelope.failure(Utils.translate(result.getFieldErrors())));
        }

        userService.saveUser(user);

        return ResponseEntity.ok(ResponseEnvelope.success(user));
    }

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
            return ResponseEntity.ok(ResponseEnvelope.failure(Utils.translate(result.getFieldErrors())));
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
