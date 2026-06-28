package com.student.services.impl;



import com.student.dto.AddressRequestDto;
import com.student.dto.ProfileUpdateRequest;
import com.student.dto.StudentRequestDto;
import com.student.dto.StudentResponseDto;
import com.student.entity.Address;
import com.student.entity.Course;
import com.student.entity.Student;
import com.student.exception.ResourceAlredyExistsException;
import com.student.exception.ResourseNotFoundException;
import com.student.repository.CourseRepository;
import com.student.repository.StudentRepository;
import com.student.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }


    @Override
    public Student addStudent(StudentRequestDto studentRequestDto) {

        if(studentRepository.existsByStudentCode(studentRequestDto.getStudentCode())){
            throw new ResourceAlredyExistsException("Student already exists with student code: "+studentRequestDto.getStudentCode());
        }
        List<Address> addresses = new ArrayList<>();

        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setDateOfBirth(studentRequestDto.getDateOfBrith());
        student.setGender(studentRequestDto.getGender());
        student.setStudentCode(studentRequestDto.getStudentCode());
        student.setAddresses(addresses);

        List<AddressRequestDto> addresses1 = studentRequestDto.getAddresses();

        List<Address> list = new ArrayList<>();
        for(AddressRequestDto address : addresses1){
            Address adr = new Address();
            adr.setAddressType(address.getAddressType());
            adr.setStreet(address.getStreet());
            adr.setCity(address.getCity());
            adr.setState(address.getState());
            adr.setCountry(address.getCountry());
            adr.setPincode(address.getPincode());
            adr.setStudent(student);
            list.add(adr);
        }
        student.setAddresses(list);


        return studentRepository.save(student);
    }

    @Override
    public List<Student> searchStudentByName(String studentName) {
        List<Student> list = studentRepository.findByNameIgnoreCase(studentName);
        return list;

    }

    @Override
    public void mapCourseToStudent(Long studentId, Long courseId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourseNotFoundException("Student not found with id: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourseNotFoundException("Course not found with id: " + courseId));

        student.getCourses().add(course);
        studentRepository.save(student);

    }

    @Override
    public List<StudentResponseDto> getStudentByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourseNotFoundException("Course not found with id: " + courseId));


        List<Student> students = studentRepository.findByCourseId(courseId);

        List<StudentResponseDto> list = new ArrayList<>();
        for(Student stu : students){
            list.add(studentResponseMapper(stu));
        }
        return list;
    }

    @Override
    public Student updateStudentProfile(Long id, ProfileUpdateRequest profileUpdateRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Student not found with id : " + id));

        if(profileUpdateRequest.getEmail() != null){
            if(studentRepository.existsByEmail(profileUpdateRequest.getEmail())){
                throw new ResourceAlredyExistsException("Student already exists with email : "+ profileUpdateRequest.getEmail());
            }
            student.setEmail(profileUpdateRequest.getEmail());
        }
        student.setMobileNumber(profileUpdateRequest.getMobileNumber());
        student.setParentsName(profileUpdateRequest.getParentsName());

         if(profileUpdateRequest.getAddresses() != null){
             student.getAddresses().clear();
             List<Address> newAddress = profileUpdateRequest.getAddresses().stream().map(a -> Address.builder()
                             .addressType(a.getAddressType())
                             .street(a.getStreet())
                             .city(a.getCity())
                             .state(a.getState())
                             .country(a.getCountry())
                             .pincode(a.getPincode())
                             .student(student)
                             .build())
                     .collect(Collectors.toList());
             student.getAddresses().addAll(newAddress);
         }
         return studentRepository.save(student);

    }

    private StudentResponseDto studentResponseMapper(Student stu) {
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setStudentId(stu.getStudentId());
        responseDto.setName(stu.getName());
        responseDto.setDateOfBrith(stu.getDateOfBirth());
        responseDto.setGender(stu.getGender());
        responseDto.setStudentCode(stu.getStudentCode());

        responseDto.setAddresses(stu.getAddresses());

        responseDto.setCourses(stu.getCourses());
        return responseDto;
    }


}
