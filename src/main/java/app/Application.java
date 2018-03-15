package app;

import app.models.Professor;
import app.models.Project;
import app.models.ProjectCoordinator;
import app.models.Student;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import app.storage.FileSystemStorageService;
import app.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

    @Bean
    public CommandLineRunner demo(ProjectRepository projectRepository, StudentRepository studentRepository) {
        if(projectRepository.count() > 0){
            return (args) -> {
                // do nothing since there is already data
            };
        }

        return (args) -> {
            Student student1 = new Student("Justin", "Krol", "justinkrol@cmail.carleton.ca", 100990999, "Software Engineering");
            Student student2 = new Student("Derek", "Stride", "derekstride@cmail.carleton.ca", 100440444, "Software Engineering");
            ProjectCoordinator coordinator = new ProjectCoordinator("Sir", "Coordinate", "coordinator@sce.carleton.ca");
            Professor prof1 = new Professor("Babak", "Esfandiari", "babak@sce.carleton.ca", coordinator);
            Professor prof2 = new Professor("Samuel", "Ajila", "ajila@sce.carleton.ca", coordinator);
            Project project = new Project(prof1, "GraphQL Query Planner", new ArrayList<String>(), 4);

            project.addStudent(student1);
            project.addStudent(student2);

            projectRepository.save(project);

            // fetch all students
            log.info("students found with findAll():");
            log.info("-------------------------------");
            for (Student student : studentRepository.findAll()) {
                log.info(student.toString());
            }
            log.info("");

            // fetch an individual student by ID
            Project proj = projectRepository.findFirstByOrderById();
            log.info("project found with findFirstByOrderById():");
            log.info("--------------------------------");
            log.info("");
        };
    }
}
