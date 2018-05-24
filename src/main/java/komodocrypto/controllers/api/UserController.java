package komodocrypto.controllers.api;

import komodocrypto.exceptions.custom_exceptions.UserException;
import komodocrypto.model.user.User;
import komodocrypto.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ArrayList<User> getUsers() {

        return userService.getUsers();
    }

    // Still in progress.
//    @GetMapping("/")
//    public User getUserByID(int user_id){
//
//        return userService.getUserByID(user_id);
//    }


    @PostMapping
    public User createUser(@RequestBody User user) throws UserException {
        return userService.createUser(user);
    }


    @PutMapping
    public User updateUser(@RequestBody User user) throws UserException{
        return userService.updateUser(user);
    }
}
