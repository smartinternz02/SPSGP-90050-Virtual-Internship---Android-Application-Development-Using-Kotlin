package harsh.my.go_grocery.registration

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import harsh.my.go_grocery.MainActivity
import harsh.my.go_grocery.R
import harsh.my.go_grocery.databinding.ActivityRegistrationBinding
import harsh.my.go_grocery.databinding.FragmentSignupBinding



class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    // Firebase Auth Variables
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSignupBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.supbtn.setOnClickListener {
            val userName = binding.username.text.toString().trim()
            val inputEmail = binding.email.text.toString().trim()
            val passWord = binding.pass.text.toString().trim()



            if (validateInput(userName,inputEmail,passWord)) createUser(userName,inputEmail,passWord)

        }
        //To Sign-in Page
        binding.already.setOnClickListener { findNavController().navigate(R.id.action_signupFragment_to_loginFragment) }
    }


    private fun createUser(userName: String , inputEmail:String , passWord:String) {

        auth .createUserWithEmailAndPassword(inputEmail, passWord)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        context,
                        "You've been registered successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        context,
                        "Account Creation Failed, Try Again!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }

    private fun validateInput(userName: String , inputEmail: String , passWord: String): Boolean {
        if (userName.isEmpty()) {
            binding.username.error = "Username is empty."
            return false
        }
        if (inputEmail.isEmpty()) {
            binding.email.error = "Email is empty."
            return false
        }
        if(passWord.length<6){
            binding.pass.error = "Password min length 6"
        }
        if (passWord.isEmpty()) {
            binding.pass.error = "Password is empty."
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}