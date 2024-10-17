package br.com.gustavodev.TodoList.tasks;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 50)
    private String title;

    private String description;

    private String priority;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50){
            throw new Exception("O campo deve conter menos de 50 caracteres");
        }
        this.title = title;
    }



}
