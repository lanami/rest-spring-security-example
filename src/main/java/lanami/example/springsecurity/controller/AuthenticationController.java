package lanami.example.springsecurity.controller;

import lanami.example.springsecurity.Utils;
import lanami.example.springsecurity.service.IUserRoleService;
import lanami.example.springsecurity.service.IUserService;
import lanami.example.springsecurity.domain.User;
import lanami.example.springsecurity.controller.http.ResponseEnvelope;
import lanami.example.springsecurity.security.AuthenticationRequest;
import lanami.example.springsecurity.security.AuthenticationResponse;
import lanami.example.springsecurity.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Unsecured controller available to anonymous users.
 *
 * @author lanami
 * @date 2016-09-08
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Value("X-Auth-Token")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    IUserService userService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        User user = this.userService.findByUsername(authenticationRequest.getUsername());
        String token = this.tokenUtils.generateToken(user);

        // Return the token
        return ResponseEntity.ok(ResponseEnvelope.success(new AuthenticationResponse(user, token)));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ResponseEnvelope> saveUser(@RequestBody @Valid User user, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            return ResponseEntity.ok(ResponseEnvelope.failure(Utils.translate(result.getFieldErrors())));
        }

        if(!userService.isUsernameUnique(user.getId(), user.getUsername())){
            FieldError error = new FieldError("user", "username", messageSource.getMessage("Unique.user.username", new String[]{user.getUsername()}, Locale.getDefault()));
            result.addError(error);
            return ResponseEntity.ok(ResponseEnvelope.failure(Utils.translate(result.getFieldErrors())));
        }

        userService.saveUser(user);

        return ResponseEntity.ok(ResponseEnvelope.success(user));
    }

    @RequestMapping(value = "604800", method = RequestMethod.GET)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = this.userService.findByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token)) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return ResponseEntity.ok(ResponseEnvelope.success(new AuthenticationResponse(user, refreshedToken)));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//        return "redirect:/login";
//    }

}