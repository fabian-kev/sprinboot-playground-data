package com.fabiankevin.springbootjpabatching;

import com.fabiankevin.springbootjpabatching.persistence.AddressEntity;
import com.fabiankevin.springbootjpabatching.persistence.StudentEntity;
import com.fabiankevin.springbootjpabatching.persistence.StudentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringbootJpaBatchingApplicationTests {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BatchStatementInspector batchStatementInspector;

    @Test
    void saveAll() {
        List<StudentEntity> studentEntities = IntStream.range(0, 10).boxed()
                .map(i -> {
                    StudentEntity studentEntity = new StudentEntity();
                    studentEntity.setAge(18);
                    studentEntity.setName(UUID.randomUUID().toString());
                    studentEntity.setStatus("ACTIVE");
                    return studentEntity;
                }).toList();
        studentRepository.saveAll(studentEntities);

        List<StudentEntity> savedStudentEntities = studentRepository.findAll();
        assertEquals(10, savedStudentEntities.size());
    }

    @Test
    @Transactional
    void saveAll_givenAddingAddressToStudents_thenShouldExecuteInBatch() {
        List<StudentEntity> studentEntities = IntStream.range(0, 5).boxed()
                .map(i -> {
                    StudentEntity studentEntity = new StudentEntity();
                    studentEntity.setAge(18);
                    studentEntity.setName(UUID.randomUUID().toString());
                    studentEntity.setStatus("ACTIVE");
                    return studentEntity;
                }).toList();
        studentRepository.saveAll(studentEntities);
        List<StudentEntity> savedStudentEntities = studentRepository.findAllEagerly();

        Set<StudentEntity> studentsWithAddress = savedStudentEntities.stream()
                .map(studentEntity -> {
                    studentEntity.addAddress(AddressEntity.builder()
                            .name(UUID.randomUUID().toString())
                            .build());
                    return studentEntity;
                }).collect(Collectors.toSet());

        studentRepository.saveAllAndFlush(studentsWithAddress);
//
//        List<StudentEntity> all = studentRepository.findAll();
//        assertEquals(100_000, all.size());
    }

}
