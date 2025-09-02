// File: app/src/main/java/br/com/fernandodev/hinovaoficinas/ui/tabs/CarRepairFragment.kt
package br.com.fernandodev.hinovaoficinas.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fernandodev.hinovaoficinas.R
import br.com.fernandodev.hinovaoficinas.core.network.HinovaWebRepository
import br.com.fernandodev.hinovaoficinas.databinding.FragmentOficinasBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CarRepairFragment : Fragment(R.layout.fragment_oficinas) {

    private var _binding: FragmentOficinasBinding? = null
    private val binding get() = _binding!!

    private val web by inject<HinovaWebRepository>()
    private lateinit var adapter: OficinasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOficinasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = OficinasAdapter { /* clique em oficina (se quiser abrir detalhes, faça aqui) */ }
        binding.rvOficinas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOficinas.adapter = adapter

        // carrega as oficinas
        fetchOficinas()

        // Botão do rodapé (pode abrir sua tela Compose/fragment de OS)
        binding.btnOpenOs.setOnClickListener {
            Toast.makeText(requireContext(), "Abrir gerenciador de OS", Toast.LENGTH_SHORT).show()
            // TODO: navegar para sua tela de OS (Compose/Fragment/Activity)
            // ex: findNavController().navigate(R.id.action_to_osHost)
        }
    }

    private fun fetchOficinas() {
        binding.progress.isVisible = true
        binding.emptyState.isVisible = false

        viewLifecycleOwner.lifecycleScope.launch {
            web.getOficinas(codigoAssociacao = 601, cpfAssociado = "")
                .onSuccess { list ->
                    adapter.submitList(list)
                    binding.emptyState.isVisible = list.isEmpty()
                }
                .onFailure { e ->
                    binding.emptyState.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        e.message ?: getString(R.string.error_network),
                        Toast.LENGTH_LONG
                    ).show()
                }

            binding.progress.isVisible = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
