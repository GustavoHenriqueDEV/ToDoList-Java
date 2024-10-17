package br.com.gustavodev.TodoList.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Getter
    @Column(unique = true)
    private String userName;
    @Getter
    @Setter
    private String name;
    @Setter
    @Getter
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
