package harsh.my.go_grocery.registration

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import harsh.my.go_grocery.R
import harsh.my.go_grocery.databinding.FragmentSignupBinding
import harsh.my.go_grocery.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater)

        Handler().postDelayed(
            { findNavController().navigate(R.id.action_splashFragment_to_signupFragment) }, 2000
        )

        return binding.root
    }


}