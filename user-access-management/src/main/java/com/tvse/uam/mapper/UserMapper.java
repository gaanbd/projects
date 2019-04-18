package com.tvse.uam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tvse.uam.domain.User;
import com.tvse.uam.dto.UserDTO;

/**
 * @author techmango (https://www.techmango.net/)
 */
@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	List<UserDTO> userProfileListToUserProfileDTOList(List<User> userprofile);

	User userDTOtoUser(UserDTO userdto);

	UserDTO userToUserDTO(User user);

	List<User> userDTOListtoUserList(List<UserDTO> userDtoList);

	List<UserDTO> userListToUserDTOList(List<User> userList);

}
