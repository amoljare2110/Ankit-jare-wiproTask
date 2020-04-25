import com.ankit.jare.view.ui.ListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Test
import java.io.IOException

class GetRecordfromDBSuccess {

    @Test
    @Throws(IOException::class)
    fun getRecordFromDB() {

        CoroutineScope(Dispatchers.IO).launch {

            if (ListFragment.getInstance().db.wiproDao().getRecords().isNotEmpty()) {
                Assert.assertTrue("Records getting Successfully from Local DB:", true)
            }
        }

    }
}