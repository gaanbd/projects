package com.tvse.uam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tvse.uam.domain.Application;
import com.tvse.uam.domain.Brand;
import com.tvse.uam.domain.User;
import com.tvse.uam.domain.UserRole;
import com.tvse.uam.dto.ApplicationDTO;
import com.tvse.uam.dto.BrandDTO;
import com.tvse.uam.dto.UserDTO;
import com.tvse.uam.dto.UserRoleDTO;

/**
 * @author techmango (https://www.techmango.net/)
 */
@Mapper
public interface UserAccessMapper {

	UserAccessMapper INSTANCE = Mappers.getMapper(UserAccessMapper.class);

	UserRole userRoleDTOtoUserRole(UserRoleDTO userRoleDTO);

	UserRoleDTO userRoleToUserRoleDTO(UserRole userRole);

	List<UserRoleDTO> userRoleListToUserRoleDTOList(List<UserRole> userRoleList);

	Brand brandDTOtoBrand(BrandDTO brandDTO);

	BrandDTO brandtoBrandDTO(Brand brand);

	List<BrandDTO> brandListToBrandDTOList(List<Brand> brandList);

	List<Brand> brandDTOListToBrandList(List<BrandDTO> brandDTOList);

	User userDTOtoUser(UserDTO userdto);

	UserDTO userToUserDTO(User user);

	List<User> userDTOListtoUserList(List<UserDTO> userDtoList);

	List<UserDTO> userListToUserDTOList(List<User> userList);

	Application brandDTOtoBrand(ApplicationDTO brandDTO);

	ApplicationDTO brandtoBrandDTO(Application brand);

	List<ApplicationDTO> applicationListToApplicationDTOList(List<Application> applicationList);

	List<UserDTO> userProfileListToUserProfileDTOList(List<User> userprofile);

}
