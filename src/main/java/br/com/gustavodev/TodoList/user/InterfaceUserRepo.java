package br.com.gustavodev.TodoList.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterfaceUserRepo extends JpaRepository<UserModel, UUID> {

    UserModel findByUserName(String userName);
}
