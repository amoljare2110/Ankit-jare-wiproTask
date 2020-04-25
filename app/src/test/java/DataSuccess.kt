import com.ankit.jare.model.ListRepository
import org.junit.Assert
import org.junit.Test

class DataSuccess {

    @Test
    fun getData() {
        ListRepository.getInstance().getRepoList { isSuccess, response ->
            Assert.assertTrue("Response Succesfull:", isSuccess)
        }

    }
}