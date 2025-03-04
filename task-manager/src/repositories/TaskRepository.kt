import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {
    fun findByDescricao(description: String): List<Task>
    fun findBySituacao(situation: String): List<Task>
}
