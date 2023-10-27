package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service // to make spring bean
public class StudentService
{
    private final StudentRepository studentRepository;

    @Autowired // it will initiate the studentRepository
    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }
    public List<Student> getStudents()
    {
        return studentRepository.findAll(); // it's like select * from student
    }

    public void addNewStudent(Student student)
    {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent())
            throw new IllegalStateException("Email has been taken");
        studentRepository.save(student);
    }


    public void deleteStudent(Long studentId)
    {
        if (studentRepository.existsById(studentId))
            studentRepository.deleteById(studentId);
        else
            throw new IllegalStateException("Student with id " + studentId + " is not exist");
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email)
    {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id "
                        + studentId + " is not exist"));
        if (name != null && name.length() > 0
                && !Objects.equals(student.getName(), name))
            student.setName(name);

        if (email != null && email.length() > 0
                && !Objects.equals(student.getEmail(), email))
        {
            if (studentRepository.findStudentByEmail(email).isPresent())
                throw new IllegalStateException("Email has been taken");
            student.setEmail(email);
        }
    }
}
