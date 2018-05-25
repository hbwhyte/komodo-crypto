package komodocrypto.controllers.api;

import komodocrypto.exceptions.custom_exceptions.UserException;
import komodocrypto.model.user.User;
import komodocrypto.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserControllerAPI {

    @Autowired
    UserService userService;

    @GetMapping
    public ArrayList<User> getUsers() {

        return userService.getUsers();
    }

    @GetMapping("/{user_id}")
    public User getUserByID(@PathVariable int user_id){

        return userService.getUserByID(user_id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws UserException {
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws UserException{
        return userService.updateUser(user);
    }

    @PutMapping("/{user_id}")
    public User deleteUser(@PathVariable int user_id) {
            return userService.deleteUser(user_id);
    }
}
