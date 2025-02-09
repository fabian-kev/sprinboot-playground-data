package com.fabiankevin.springbootjpabatching;

import com.fabiankevin.springbootjpabatching.persistence.StudentEntity;
import com.fabiankevin.springbootjpabatching.persistence.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;


    void retrieveAll() {
        Slice<StudentEntity> sliceStudents = studentRepository.sliceStudents(PageRequest.of(0, 3));

        while (sliceStudents.hasNext()) {
            sliceStudents = studentRepository.sliceStudents(sliceStudents.nextPageable());

            List<StudentEntity> activeStudents = sliceStudents.get()
                    .map(studentEntity -> studentEntity.toBuilder().status("ACTIVE").build())
                    .collect(Collectors.toList());
            studentRepository.saveAllAndFlush(activeStudents);
        }
    }


}
