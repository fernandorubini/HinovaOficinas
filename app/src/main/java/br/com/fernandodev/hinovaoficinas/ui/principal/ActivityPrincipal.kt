// File: app/src/main/java/br/com/fernandodev/hinovaoficinas/ui/principal/ActivityPrincipal.kt
package br.com.fernandodev.hinovaoficinas.ui.principal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import br.com.fernandodev.hinovaoficinas.R
import br.com.fernandodev.hinovaoficinas.data.session.SessionManager
import br.com.fernandodev.hinovaoficinas.ui.login.ActivityLogin
import br.com.fernandodev.hinovaoficinas.ui.tabs.CarRepairFragment
import br.com.fernandodev.hinovaoficinas.ui.tabs.IndicationFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class ActivityPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        findViewById<MaterialToolbar>(R.id.toolbar)?.let {
            setSupportActionBar(it)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        // Garante sessão antes de montar as abas
        lifecycleScope.launch {
            val user = try { SessionManager.getUser(this@ActivityPrincipal) }
            catch (t: Throwable) {
                Toast.makeText(this@ActivityPrincipal, R.string.error_session_read, Toast.LENGTH_SHORT).show()
                null
            }

            if (user == null) {
                startActivity(Intent(this@ActivityPrincipal, ActivityLogin::class.java))
                finish()
            } else {
                setupTabs()
            }
        }
    }

    private fun setupTabs() {
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2
            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> CarRepairFragment()      // 📍 Lista de Oficinas (somente ativas)
                else -> IndicationFragment()  // 📍 Tela com texto + botão “Convidar amigo”
            }
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = if (pos == 0) getString(R.string.tab_oficinas)
            else getString(R.string.tab_indicacao)
        }.attach()
    }
}
