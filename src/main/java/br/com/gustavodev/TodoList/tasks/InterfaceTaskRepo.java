package br.com.gustavodev.TodoList.tasks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InterfaceTaskRepo extends JpaRepository<TaskModel, UUID> {

    List<TaskModel> findByIdUser(UUID idUser);
}
