package komodocrypto.mappers.user;

import komodocrypto.model.user.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface UserMapper {

    String GET_USERS = "SELECT * FROM `komodoDB`.`users`;";

    String UPDATE_USER = "UPDATE `komodoDB`.`users` SET `first_name` = #{first_name}, `last_name` = #{last_name}, `password` = #{password}, " +
            "`email` = #{email}, `active` = #{active} WHERE `user_id` = #{user_id};";

    String CREATE_USER = "INSERT INTO `komodoDB`.`users` (`user_id`, `first_name`, `last_name`, `password`, `email`, `userSettings_id`) " +
            "VALUES (#{user_id}, #{first_name}, #{last_name}, #{password}, #{email}, #{userSettings_id});";

    String GET_USER_BY_ID = "SELECT * FROM `komodoDB`.`users` WHERE `user_id` = #{user_id};";

    String GET_USER_BY_FIRSTNAME_LASTNAME = "SELECT * FROM `komodoDB`.`users` WHERE `first_name` = #{arg0} AND last_name = #{arg1} " +
            "ORDER BY created DESC limit 1;";

    String DELETE_USER = "UPDATE `komodoDB`.`users` SET `active` = 0 WHERE `user_id` = #{user_id};";


    @Select(GET_USERS)
    public ArrayList<User> getUsers();

    @Update(UPDATE_USER)
    public int updateUser(User user);

    @Insert(CREATE_USER)
    public int createUser(User user);

    @Select(GET_USER_BY_ID)
    public User getUserByID(int user_id);

    @Select(GET_USER_BY_FIRSTNAME_LASTNAME)
    public User getUserByFirstName_LastName(String first_name, String last_name);

    @Update(DELETE_USER)
    public int deleteUser(int user_id);

}
