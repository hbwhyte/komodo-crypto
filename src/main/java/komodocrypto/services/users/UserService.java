package komodocrypto.services.users;

import komodocrypto.exceptions.custom_exceptions.UserException;
import komodocrypto.mappers.user.UserMapper;
import komodocrypto.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    // in progress. Brandt

    @Autowired
    UserMapper userMapper;

    public ArrayList<User> getUsers(){

        return userMapper.getUsers();
    }

    public User getUserByID(int user_id){

        return userMapper.getUserByID(user_id);
    }

    // Update still in progress.
    public User updateUser(User user) throws UserException {

        if (validateUser(user)){

            int i = userMapper.updateUser(user);

            if (i > 0){
                return userMapper.getUserByFirstName_LastName(user.getFirst_name(), user.getLast_name());
            } else {
                throw new UserException("Unable to update user.");
            }
        } else {
            throw new UserException("Please enter the data you are changing.");
        }
    }

    public User createUser(User user) throws UserException {

        if (validateUser(user)){

            int i = userMapper.createUser(user);

            if (i > 0){
                return userMapper.getUserByFirstName_LastName(user.getFirst_name(), user.getLast_name());
            } else {
                throw new UserException("Unable to create user. Unknown error.");
            }
        } else {
            throw new UserException("Please enter all required fields. First Name, Last Name, Email, Password");
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
