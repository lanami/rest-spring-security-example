package lanami.example.springsecurity.web.controller;

import lanami.example.springsecurity.Utils;
import lanami.example.springsecurity.app.service.IUserService;
import lanami.example.springsecurity.app.domain.User;
import lanami.example.springsecurity.app.security.AuthenticationRequest;
import lanami.example.springsecurity.app.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Unsecured controller available to anonymous users.
 *
 * @author lanami
 * @date 2016-09-08
 */
@RestController
@RequestMapping("token")
public class AuthenticationController {

    @Value("X-Auth-Token")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    IUserService userService;

    /**
     * Authenticate the user and generate JWT token to supply with future requests.
     *
     * POST /token
     * Content-Type: application/json
     * {"username" : "admin", "password" : "admin"}
     * */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest, BindingResult bindingResult) throws AuthenticationException {

        // check for validation errors
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Utils.translate(bindingResult.getFieldErrors()));
        }

        // try authenticating the user
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // if all good, generate a token
        User user = this.userService.findByUsername(authenticationRequest.getUsername());
        String token = this.tokenUtils.generateToken(user);

        return ResponseEntity.ok(token);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = this.userService.findByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token)) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return ResponseEntity.ok(refreshedToken);
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