import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData<String>().apply { value = "John Doe" }  // Default name
    val name: LiveData<String> = _name

    private val _gender = MutableLiveData<String>().apply { value = "Male" }  // Default gender
    val gender: LiveData<String> = _gender

    private val _interests = MutableLiveData<List<String>>().apply {
        value = listOf("Anime", "Music")  // Default interests
    }
    val interests: LiveData<List<String>> = _interests

    // Setters
    fun setName(newName: String) {
        _name.value = newName
    }

    fun setGender(newGender: String) {
        _gender.value = newGender
    }

    fun setInterests(newInterests: List<String>) {
        _interests.value = newInterests
    }
}
