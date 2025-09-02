package br.com.fernandodev.hinovaoficinas.ui.principal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import br.com.fernandodev.hinovaoficinas.ui.oficinas.OficinasScreen

class OficinasHostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setContent { OficinasScreen() }
        }
}
