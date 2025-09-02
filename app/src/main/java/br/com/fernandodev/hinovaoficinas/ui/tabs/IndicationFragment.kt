// File: app/src/main/java/br/com/fernandodev/hinovaoficinas/ui/tabs/IndicationFragment.kt
package br.com.fernandodev.hinovaoficinas.ui.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.fernandodev.hinovaoficinas.R
import br.com.fernandodev.hinovaoficinas.databinding.FragmentIndicationBinding
import br.com.fernandodev.hinovaoficinas.ui.indication.ActivityIndication

class IndicationFragment : Fragment(R.layout.fragment_indication) {

    private var _binding: FragmentIndicationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConvidar.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityIndication::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
