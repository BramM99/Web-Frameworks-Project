package app.rest;

import app.models.ConfirmationToken;
import app.models.User;
import app.repositories.ConfirmationTokenRepository;
import app.repositories.UserRepository;
import app.utility.EmailService;
import app.utility.JWTToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;



@RestController
public class AuthenticateController {

    //jwt config
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.pass-phrase}")
    private String passPhrase;
    @Value("${jwt.duration-of-validity}")
    private int tokenDurationValidity;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping(path = "/authenticate/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<User> login(@RequestBody ObjectNode loginObject) {

        //Statisch ID omdat er nog geen gebruikers worden opgeslagen en er anders dus geen overeenkomst is in de Database
//        long id = (long) (Math.random() * (999 - 1 + 1) + 1);
        long id = 1;

        //getting email and password from the jsonObject
        String eMail = loginObject.get("eMail").asText();
        String passWord = loginObject.get("passWord").asText();

        User user = userRepository.findByMail(eMail);
        if(user == null){
            throw new UnAuthorizedException("user does not exist");
        }
        if(!passWord.equals(user.getHashedPassWord())){
            throw new UnAuthorizedException("password or email is wrong");
        }

        if (userRepository.findByMail(eMail).isActive()) {

            return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer " + createJWTToken(user)).body(user);
        } else {
            throw new UnAuthorizedException("account has not been activated yet");
        }
    }

    @PostMapping(path = "/authenticate/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> registerUser(
            @RequestBody ObjectNode registerObject) {

            //getting email and password from the jsonObject
            String eMail = registerObject.get("eMail").asText();
            String passWord = registerObject.get("passWord").asText();

            if (userRepository.findByMail(eMail) != null) {
                return ResponseEntity.badRequest().header("email exists").build();
            }

            //getting only the information before the @
            String name = eMail.substring(0, eMail.indexOf('@'));

//            passWord = encoder.encode(passWord);
            User createUser = new User(0, name, eMail, passWord, false, false);
            createUser = userRepository.save(createUser);

            ConfirmationToken confirmationToken = new ConfirmationToken(createUser);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(createUser.geteMail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("team05_web-frameworks");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8085/authenticate/confirm-account?token=" + confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);


        return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "User registered").build();

    }

    private final long A_WHOLE_DAY = 24*60*60*1000;

    @GetMapping(path = "/authenticate/confirm-account")
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {

            if(token.getCreatedDate().getTime() > System.currentTimeMillis() - A_WHOLE_DAY) {
                User user = userRepository.findByMail(token.getUser().geteMail());
                user.setActive(true);
                userRepository.save(user);
                return ResponseEntity.accepted().body("You have activated your account!");
            }else {
                return ResponseEntity.badRequest().body("token is not valid");
            }
        } else {
            return ResponseEntity.badRequest().body("token does not exist");
        }
    }

    private String createJWTToken(User user){
        final JWTToken jwtTokenGenerator = new JWTToken(user.getName(), user.getId(), user.isAdmin());
        return jwtTokenGenerator.encode(issuer, passPhrase,tokenDurationValidity);
    }

}
