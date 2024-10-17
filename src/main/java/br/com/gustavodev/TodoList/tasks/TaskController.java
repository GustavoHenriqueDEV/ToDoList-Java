package br.com.gustavodev.TodoList.tasks;


import br.com.gustavodev.TodoList.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private InterfaceTaskRepo taskRepo;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel task, HttpServletRequest request) {
        System.out.println("Chegou" + request.getAttribute("idUser"));
        var idUser = request.getAttribute("idUser");
        task.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        //Valida se a task esta sendo lançada na data correta
      if(currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de inicio e termino devem ser maiores que a data atual.");
        }
        var tasks = this.taskRepo.save(task);

        System.out.println(task);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/")
    public List<TaskModel> listTasks(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepo.findByIdUser((UUID)idUser);
        return tasks;
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel task,  @PathVariable UUID id, HttpServletRequest request){

        var existTask = this.taskRepo.findById(id).orElse(null);

        if (existTask == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task não existe");

        }

        var idUser = request.getAttribute("idUser");

        if(!existTask.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario não tem permissão para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(task, existTask);
        var taskUpdated = this.taskRepo.save(existTask);
        return  ResponseEntity.ok().body(taskUpdated);
    }
}
