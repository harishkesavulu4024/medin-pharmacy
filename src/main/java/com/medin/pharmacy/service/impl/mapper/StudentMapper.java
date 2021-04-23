package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.StudentDTO;
import com.medin.pharmacy.entities.Student;

@Mapper(componentModel = "spring", uses = {})
public interface StudentMapper {
	
	Student studentDTOToStudent(StudentDTO studentDTO);
	
	StudentDTO studentToStudentDTO(Student student);
	

}
