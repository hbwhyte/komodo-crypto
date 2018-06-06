package komodocrypto.services.users;

import komodocrypto.exceptions.custom_exceptions.UserException;
import komodocrypto.mappers.UserMapper;
import komodocrypto.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    // in progress. Brandt

    @Autowired
    UserMapper userMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public ArrayList<User> getUsers(){

        return userMapper.getUsers();
    }

    public User getUserByID(int user_id){

        return userMapper.getUserByID(user_id);
    }

    // Update still in progress.
    public User updateUser(User user) throws UserException {

        if (validateUser(user)){

            // encrypt password
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            int i = userMapper.updateUser(user);

            if (i > 0){
                return userMapper.getUserByFirstName_LastName(user.getFirst_name(), user.getLast_name());
            } else {
                throw new UserException("Unable to update user.", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new UserException("Please enter the data you are changing.", HttpStatus.BAD_REQUEST);
        }
    }

    public User createUser(User user) throws UserException {

        if (validateUser(user)){

            // encrypt password
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            int i = userMapper.createUser(user);

            if (i > 0){
                return userMapper.getUserByFirstName_LastName(user.getFirst_name(), user.getLast_name());
            } else {
                throw new UserException("Unable to create user. Unknown error.", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new UserException("Please enter all required fields. First Name, Last Name, Email, Password", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean validateUser(User user){

        if (!(user.getFirst_name().length() > 0)) return false;
        if (!(user.getLast_name().length() > 0)) return false;
        if (!(user.getEmail().length() > 0)) return false;
        if (!(user.getPassword().length() > 0)) return false;

        return true;
    }

}
