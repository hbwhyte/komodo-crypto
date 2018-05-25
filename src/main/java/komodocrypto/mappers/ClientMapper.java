package komodocrypto.mappers;

import komodocrypto.model.security.OAuth2Client;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClientMapper {

    @Insert("INSERT INTO `Komodo`.`oauth_client_details` " +
            "(`client_id`, `client_secret`, `scope`, `authorized_grant_types`) " +
            "VALUES (#{client_id}, #{client_secret}, #{scope}, #{authorized_grant_types}); ")
    public boolean insertClient(OAuth2Client client);


    @Select("SELECT * FROM `Komodo`.`oauth_client_details` WHERE `client_id` = #{client_id} ")
    public OAuth2Client findClient(String client_id);


    @Update("UPDATE `Komodo`.`oauth_client_details`" +
            "SET client_id=#{client_id}, client_secret=#{client_secret}, scope=#{scope}, authorized_grant_types=#{authorized_grant_types}" +
            "WHERE client_id=#{client_id}")
    public boolean updateClient(OAuth2Client client);


    @Delete("DELETE FROM `Komodo`.`oauth_client_details`" +
            "WHERE client_id=#{client_id}")
    public boolean deleteClient(String client_id);

}
