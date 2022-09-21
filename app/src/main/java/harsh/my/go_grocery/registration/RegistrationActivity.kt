package harsh.my.go_grocery.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import harsh.my.go_grocery.R
import harsh.my.go_grocery.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

    }
}