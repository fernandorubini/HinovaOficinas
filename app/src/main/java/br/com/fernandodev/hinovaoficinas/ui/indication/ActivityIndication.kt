package br.com.fernandodev.hinovaoficinas.ui.indication

import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import br.com.fernandodev.hinovaoficinas.R
import br.com.fernandodev.hinovaoficinas.core.network.HinovaWebRepository
import br.com.fernandodev.hinovaoficinas.data.net.models.EntradaIndicacaoDto
import br.com.fernandodev.hinovaoficinas.data.net.models.IndicacaoDto
import br.com.fernandodev.hinovaoficinas.data.session.SessionManager
import br.com.fernandodev.hinovaoficinas.databinding.ActivityIndicationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityIndication : AppCompatActivity() {

    private lateinit var binding: ActivityIndicationBinding
    private val repo: HinovaWebRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.tvTitulo?.text = getString(R.string.title_indication)

        // Máscara telefone BR
        lifecycleScope.launch(Dispatchers.Default) {
            val watcher = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                PhoneNumberFormattingTextWatcher("BR")
            else
                PhoneNumberFormattingTextWatcher()
            withContext(Dispatchers.Main) { binding.etTelefone.addTextChangedListener(watcher) }
        }

        binding.btnEnviar.setOnClickListener { enviar() }
    }

    private fun enviar() {
        val nome = binding.etNome.text?.toString()?.trim().orEmpty()
        val tel = binding.etTelefone.text?.toString()?.filter { it.isDigit() }.orEmpty()
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()

        var hasError = false
        binding.tilNome.error =
            if (nome.isBlank()) { hasError = true; getString(R.string.error_form_name) } else null
        binding.tilTelefone.error =
            if (tel.isBlank()) { hasError = true; getString(R.string.error_form_phone) } else null
        binding.tilEmail.error =
            if (email.isBlank()) { hasError = true; getString(R.string.error_form_email) } else null
        if (hasError) return

        lifecycleScope.launch {
            binding.progress.isVisible = true
            binding.btnEnviar.isEnabled = false

            val user = SessionManager.getUser(this@ActivityIndication)
            if (user == null) {
                Toast.makeText(
                    this@ActivityIndication,
                    R.string.error_login_invalido,
                    Toast.LENGTH_SHORT
                ).show()
                binding.progress.isVisible = false
                binding.btnEnviar.isEnabled = true
                finish()
                return@launch
            }

            val hoje = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

            val entrada = EntradaIndicacaoDto(
                indicacao = IndicacaoDto(
                    codigoAssociacao = 601,
                    dataCriacao = hoje,
                    cpfAssociado = user.Cpf,
                    emailAssociado = user.Email,
                    nomeAssociado = user.Nome,
                    telefoneAssociado = user.Telefone,
                    placaVeiculoAssociado = null,
                    nomeAmigo = nome,
                    telefoneAmigo = tel,
                    emailAmigo = email,
                    observacao = null
                ),
                remetente = (user.Email ?: ""),
                copias = emptyList()
            )

            repo.enviarIndicacao(entrada)
                .onSuccess { msg ->
                    Toast.makeText(
                        this@ActivityIndication,
                        msg.ifBlank { getString(R.string.success_indication) },
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                .onFailure { e ->
                    Toast.makeText(
                        this@ActivityIndication,
                        e.message ?: getString(R.string.error_network),
                        Toast.LENGTH_LONG
                    ).show()
                }


            binding.progress.isVisible = false
            binding.btnEnviar.isEnabled = true
        }
    }
}
