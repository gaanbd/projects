package com.tvse.uam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tvse.uam.domain.Application;
import com.tvse.uam.dto.ApplicationDTO;

/**
 * RoleMapper class to map roledto and role domain
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Mapper
public interface ApplicationMapper {

	ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

	Application applicationDTOtoApplication(ApplicationDTO applicationDTO);

	ApplicationDTO applicationToApplicationDTO(Application application);

	List<Application> applicationDTOListToApplicationList(List<ApplicationDTO> applicationDTOList);

	List<ApplicationDTO> applicationListToApplicationDTOList(List<Application> applicationList);

}
