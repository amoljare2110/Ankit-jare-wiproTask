import com.ankit.jare.view.ui.ListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Test
import java.io.IOException

class GetRecordFromDBFaliure {

    @Test
    @Throws(IOException::class)
    fun getRecordFromDB() {

        CoroutineScope(Dispatchers.IO).launch {

            if (ListFragment.getInstance().db.wiproDao().getRecords().size < 0) {
                Assert.assertFalse("Records not getting from Local DB:", false)
            }
        }

    }
}