// File: app/src/main/java/br/com/fernandodev/hinovaoficinas/ui/principal/OSHostFragment.kt
package br.com.fernandodev.hinovaoficinas.ui.principal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import br.com.fernandodev.hinovaoficinas.ui.navigation.AppNavHost

/**
 * Fragmento “container” para hospedar o NavHost em Compose
 * usado na primeira aba (Ordens de Serviço).
 */
class OSHostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Libera a composição quando o lifecycle do Fragment é destruído
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                // Se você tiver um tema Compose, envolva aqui (ex.: AppTheme { AppNavHost() })
                AppNavHost()
            }
        }
    }
}
