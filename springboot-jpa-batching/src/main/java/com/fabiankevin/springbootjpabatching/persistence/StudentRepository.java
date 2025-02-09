package com.fabiankevin.springbootjpabatching.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    @Query("SELECT s FROM students s")
    Slice<StudentEntity> sliceStudents(Pageable pageable);

    @Query("SELECT s FROM students s LEFT JOIN FETCH s.addresses a")
    List<StudentEntity> findAllEagerly();
}
