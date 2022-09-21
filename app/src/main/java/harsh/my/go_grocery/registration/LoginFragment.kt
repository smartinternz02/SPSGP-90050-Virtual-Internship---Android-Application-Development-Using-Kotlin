package harsh.my.go_grocery.registration

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import harsh.my.go_grocery.MainActivity
import harsh.my.go_grocery.R
import harsh.my.go_grocery.databinding.FragmentLoginBinding


const val TAG = "LoginFragment"
const val RC_ONE_TAP = 123

class login : Fragment() {
    private var _binding: harsh.my.go_grocery.databinding.FragmentLoginBinding? = null
    private val binding get() = _binding!!


    // Firebase Auth
    private lateinit var auth: FirebaseAuth


//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater)

        binding.loginbtn.setOnClickListener {
            requireActivity().startActivity(Intent(context, MainActivity::class.java))
            requireActivity().finish()
        }
        auth = FirebaseAuth.getInstance()


        //For Forget Password:
        binding.btnForgpas.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgotpass, null)
            val user = view.findViewById<EditText>(R.id.et_username)

            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgotPassword(user)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ -> })
            builder.show()


        }
        return binding.root
    }

    //Fun for forgot pass
    private fun forgotPassword(user: EditText) {
        if (user.text.toString().isEmpty()) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user.text.toString()).matches()) {
            return
        }
        auth.sendPasswordResetEmail(user.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "Email sent(do check your spam folder!)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.loginbtn.setOnClickListener {
            val inputEmail = binding.username.text.toString().trim()
            val inputPassword = binding.password.text.toString().trim()

            if (validateInput(inputEmail, inputPassword)) signIn(inputEmail, inputPassword)
        }


    }

    private fun signIn(username: String, password: String) {


        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

            }
    }

    private fun validateInput(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            binding.username.error = "Email is empty."
            return false
        }
        if (password.isEmpty()) {
            binding.password.error = "Password is empty."
            return false
        }
        return true
    }

    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            requireActivity().startActivity(Intent(context, MainActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}