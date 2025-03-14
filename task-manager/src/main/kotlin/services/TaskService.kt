import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepository: TaskRepository) {

    fun salvar(tarefa: Task): Task = taskRepository.save(tarefa)

    fun buscarPorId(id: Long): Task? = taskRepository.findById(id).orElse(null)

    fun buscarPorDescricao(descricao: String): List<Task> = taskRepository.findByDescricao(descricao)

    fun buscarPorSituacao(situacao: String): List<Task> = taskRepository.findBySituacao(situacao)

    fun atualizar(id: Long, novaTarefa: Task): Task? {
        val tarefaExistente = taskRepository.findById(id).orElse(null)
        return if (tarefaExistente != null) {
            val tarefaAtualizada = tarefaExistente.copy(
                description = novaTarefa.description,
                situation = novaTarefa.situation
            )
            taskRepository.save(tarefaAtualizada)
        } else {
            null
        }
    }

    fun deletar(id: Long) {
        taskRepository.deleteById(id)
    }
}
