package br.com.fernandodev.hinovaoficinas.ui.details

import android.os.Bundle
import android.view.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import br.com.fernandodev.hinovaoficinas.core.network.HinovaWebRepository
import org.koin.androidx.compose.get

class OficinasTabFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            MaterialTheme {
                OficinasScreen(web = get<HinovaWebRepository>())
            }
        }
    }
}
