package com.fabiankevin.springbootjpabatching;

import com.fabiankevin.springbootjpabatching.persistence.StudentEntity;
import com.fabiankevin.springbootjpabatching.persistence.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableScheduling
public class SpringbootJpaBatchingApplication {

    @Autowired
    private StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaBatchingApplication.class, args);
    }

    @PostConstruct
//	@Transactional
    public void load() {
        List<StudentEntity> entities = IntStream.range(1, 50).boxed()
                .map(i -> new StudentEntity(null, UUID.randomUUID().toString(), i, "PENDING", Instant.now()))
                .collect(Collectors.toList());
        System.out.println("entities " + entities.size());
        studentRepository.saveAllAndFlush(entities);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentService studentService) {
        return args -> {
            studentService.retrieveAll();
        };
    }

    @Scheduled(fixedDelay = 5000)
    public void check() {
        int active = studentRepository.findAll().stream()
                .filter(studentEntity -> "ACTIVE".equalsIgnoreCase(studentEntity.getStatus()))
                .collect(Collectors.toList()).size();
        int pending = studentRepository.findAll().stream()
                .filter(studentEntity -> "PENDING".equalsIgnoreCase(studentEntity.getStatus()))
                .collect(Collectors.toList()).size();
        System.out.println("Active size: " + active + " pending=" + pending);
    }
}
