// app/src/main/java/br/com/fernandodev/hinovaoficinas/ui/login/ActivityLogin.kt
package br.com.fernandodev.hinovaoficinas.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.fernandodev.hinovaoficinas.R
import br.com.fernandodev.hinovaoficinas.data.session.SessionManager
import br.com.fernandodev.hinovaoficinas.ui.profile.ActivityProfile
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class ActivityLogin : AppCompatActivity() {

    companion object {
        private const val CPF_VALIDO = "78885983073"
        private const val SENHA_VALIDA = "37038958887"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val tilCpf   = findViewById<TextInputLayout>(R.id.tilCpf)
        val tilSenha = findViewById<TextInputLayout>(R.id.tilSenha)
        val etCpf    = findViewById<TextInputEditText>(R.id.etCpf)
        val etSenha  = findViewById<TextInputEditText>(R.id.etSenha)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)

        etSenha.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tentarLogin(tilCpf, tilSenha, etCpf, etSenha)
                true
            } else false
        }

        btnLogin.setOnClickListener { tentarLogin(tilCpf, tilSenha, etCpf, etSenha) }
    }

    private fun tentarLogin(
        tilCpf: TextInputLayout,
        tilSenha: TextInputLayout,
        etCpf: TextInputEditText,
        etSenha: TextInputEditText
    ) {
        val cpf   = etCpf.text?.toString().orEmpty().filter { it.isDigit() }
        val senha = etSenha.text?.toString().orEmpty()

        var ok = true
        if (cpf.isBlank()) { tilCpf.error = getString(R.string.error_informe_cpf); ok = false } else tilCpf.error = null
        if (senha.isBlank()) { tilSenha.error = getString(R.string.error_informe_senha); ok = false } else tilSenha.error = null
        if (!ok) return

        if (cpf == CPF_VALIDO && senha == SENHA_VALIDA) {
            val usuario = SessionManager.LoginUser(
                Id = 3555L,
                Nome = "Marcelo Teste",
                Codigo_mobile = "555",
                Cpf = "788.859.830-73",
                Email = "marcelo.teste@hinovamobile.com.br",
                Situacao = "ATIVO",
                Telefone = "31-9999-5551"
            )

            lifecycleScope.launch {
                SessionManager.setUser(this@ActivityLogin, usuario)
                startActivity(Intent(this@ActivityLogin, ActivityProfile::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        } else {
            Toast.makeText(this, R.string.error_login_invalido, Toast.LENGTH_SHORT).show()
        }
    }
}
