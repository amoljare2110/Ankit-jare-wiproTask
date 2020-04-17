import com.ankit.jare.model.ListRepository
import org.junit.Assert
import org.junit.Test

class DataFailure {

    @Test
    fun getData() {
        ListRepository.getInstance().getRepoList { isSuccess, response ->
            Assert.assertFalse("Error in Response:", isSuccess)
        }
    }
}