import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tarefas")
class TaskController(private val taskService: TaskService) {

    @PostMapping
    fun criarTarefa(@RequestBody tarefa: Task): ResponseEntity<Task> {
        val novaTarefa = taskService.salvar(tarefa)
        return ResponseEntity.ok(novaTarefa)
    }

    @GetMapping("/{id}")
    fun buscarTarefaPorId(@PathVariable id: Long): ResponseEntity<Task?> {
        val tarefa = taskService.buscarPorId(id)
        return ResponseEntity.ok(tarefa)
    }

    @GetMapping
    fun buscarTarefas(
        @RequestParam(required = false) id: Long?,
        @RequestParam(required = false) descricao: String?,
        @RequestParam(required = false) situacao: String?
    ): ResponseEntity<List<Task>> {
        val tarefas = when {
            id != null -> listOfNotNull(taskService.buscarPorId(id))
            descricao != null -> taskService.buscarPorDescricao(descricao)
            situacao != null -> taskService.buscarPorSituacao(situacao)
            else -> taskService.buscarPorSituacao("") // Retorna todas
        }
        return ResponseEntity.ok(tarefas)
    }

    @PutMapping("/{id}")
    fun atualizarTarefa(
        @PathVariable id: Long,
        @RequestBody novaTarefa: Task
    ): ResponseEntity<Task?> {
        val tarefaAtualizada = taskService.atualizar(id, novaTarefa)
        return if (tarefaAtualizada != null) {
            ResponseEntity.ok(tarefaAtualizada)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletarTarefa(@PathVariable id: Long): ResponseEntity<Void> {
        taskService.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
