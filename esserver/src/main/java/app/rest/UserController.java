package app.rest;

import app.models.User;
import app.repositories.UserRepository;
import app.utility.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.pass-phrase}")
    private String passPhrase;

    @GetMapping("/users")
    public User getUser(@RequestHeader(name = "Authorization") String jwtToken, @RequestParam long id){
        jwtToken = jwtToken.replace("Bearer ", "");
        JWTToken decodedJwt = JWTToken.decode(jwtToken, passPhrase);
        if(decodedJwt == null){
            throw new UnAuthorizedException("jwt token is invalid");
        }
        if(decodedJwt.isAdmin()) {
            return userRepository.findById(id);
        }
        if(id != decodedJwt.getUserId()){
            throw  new UnAuthorizedException("request id and user id are not the same");
        }
        return userRepository.findById(id);
    }

    @PostMapping("/users")
    public User updateUser(@RequestHeader(name = "Authorization") String jwtToken,@RequestParam("id") long id,@RequestBody User updatedUser){
        jwtToken = jwtToken.replace("Bearer ", "");
        JWTToken decodedJwt = JWTToken.decode(jwtToken, passPhrase);
        if(decodedJwt == null){
            throw new UnAuthorizedException("jwt token is invalid");
        }
        if(!Objects.nonNull(updatedUser)){
            throw new UnAuthorizedException("user not fully filled in");
        }
        if(decodedJwt.isAdmin()) {
            return userRepository.save(updatedUser);
        }
        if(id != decodedJwt.getUserId()){
            throw new UnAuthorizedException("request id and user are not the same");
        }
        User oldUser = userRepository.findById(id);
        oldUser.seteMail(updatedUser.geteMail());
        oldUser.setHashedPassWord(updatedUser.getHashedPassWord());
        oldUser.setName(updatedUser.getName());
        userRepository.save(oldUser);

        return userRepository.save(oldUser);

    }
}
