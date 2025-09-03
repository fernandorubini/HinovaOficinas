package br.com.fernandodev.hinovaoficinas.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.fernandodev.hinovaoficinas.R
import br.com.fernandodev.hinovaoficinas.data.session.SessionManager
import br.com.fernandodev.hinovaoficinas.databinding.ActivityProfileBinding
import br.com.fernandodev.hinovaoficinas.ui.principal.ActivityPrincipal
import kotlinx.coroutines.launch

class ActivityProfile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.tvTitulo?.text = getString(R.string.app_name)

        lifecycleScope.launch {
            val user = SessionManager.getUser(this@ActivityProfile)
            if (user == null) {
                Toast.makeText(this@ActivityProfile, R.string.error_login_invalido, Toast.LENGTH_SHORT).show()
                finish()
                return@launch
            }

            // Preenche os campos
            binding.tvId.text         = (user.Id ?: 0L).toString()
            binding.tvNome.text       = user.Nome.orEmpty()
            binding.tvCodigo.text     = user.Codigo_mobile.orEmpty()
            binding.tvCpf.text        = user.Cpf.orEmpty()
            binding.tvEmail.text      = user.Email.orEmpty()
            binding.tvSituacao.text   = user.Situacao.orEmpty()
            binding.tvTelefone.text   = user.Telefone.orEmpty()
        }

        binding.btnContinuar.setOnClickListener {
            startActivity(Intent(this, ActivityPrincipal::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }
}
