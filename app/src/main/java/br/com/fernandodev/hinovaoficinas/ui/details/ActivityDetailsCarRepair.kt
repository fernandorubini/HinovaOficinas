// File: app/src/main/java/br/com/fernandodev/hinovaoficinas/ui/details/ActivityDetailsCarRepair.kt
package br.com.fernandodev.hinovaoficinas.ui.details

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.fernandodev.hinovaoficinas.databinding.ActivityDetailsCarRepairBinding
import java.net.URLEncoder
import android.util.Base64

class ActivityDetailsCarRepair : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsCarRepairBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsCarRepairBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val oficina = intent.getParcelableExtra<OficinaUi>(EXTRA_OFICINA) ?: run {
            finish(); return
        }

        supportActionBar?.title = oficina.nome

        // Conteúdo
        binding.ivFoto.loadBase64(oficina.fotoBase64)

        binding.tvNome.text = oficina.nome
        binding.tvDescricao.text = oficina.descricao.orDash()
        binding.tvEndereco.bindAction(
            value = oficina.endereco,
            onClick = { abrirMapa(oficina.endereco!!) }
        )
        binding.tvTelefone1.bindAction(
            value = oficina.telefone1,
            onClick = { abrirDiscador(oficina.telefone1!!) }
        )
        binding.tvTelefone2.bindAction(
            value = oficina.telefone2,
            onClick = { abrirDiscador(oficina.telefone2!!) }
        )
        binding.tvEmail.bindAction(
            value = oficina.email,
            onClick = { enviarEmail(oficina.email!!) }
        )
    }

    /* ---------- Ações ---------- */

    private fun abrirDiscador(numero: String) {
        // Não requer permissão (ACTION_DIAL apenas abre o discador)
        val uri = Uri.parse("tel:$numero")
        startActivity(Intent(Intent.ACTION_DIAL, uri))
    }

    private fun enviarEmail(destinatario: String) {
        val uri = Uri.parse("mailto:$destinatario")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        // extras opcionais
        // intent.putExtra(Intent.EXTRA_SUBJECT, "Assunto")
        // intent.putExtra(Intent.EXTRA_TEXT, "Mensagem")
        startActivity(Intent.createChooser(intent, "Enviar e-mail"))
    }

    private fun abrirMapa(endereco: String) {
        // Usa geo: query por endereço (abre Google Maps ou app similar)
        val query = URLEncoder.encode(endereco, "UTF-8")
        val uri = Uri.parse("geo:0,0?q=$query")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    /* ---------- Helpers UI ---------- */

    private fun String?.orDash() = if (this.isNullOrBlank()) "-" else this

    private fun TextView.bindAction(value: String?, onClick: () -> Unit) {
        if (value.isNullOrBlank()) {
            text = "-"
            isClickable = false
            isFocusable = false
        } else {
            text = value
            isClickable = true
            isFocusable = true
            setOnClickListener { onClick() }
        }
    }

    private fun ImageView.loadBase64(base64: String?) {
        if (base64.isNullOrBlank()) {
            this.setImageDrawable(null)
            this.adjustViewBounds = false
            this.layoutParams.height = 0 // some “collapse” if you prefer
            return
        }
        runCatching {
            val data = Base64.decode(base64, Base64.DEFAULT)
            val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
            this.setImageBitmap(bmp)
        }.onFailure {
            // se der ruim, só limpa a imagem
            this.setImageDrawable(null)
        }
    }

    companion object {
        private const val EXTRA_OFICINA = "extra_oficina"

        fun newIntent(context: Context, oficina: OficinaUi): Intent =
            Intent(context, ActivityDetailsCarRepair::class.java)
                .putExtra(EXTRA_OFICINA, oficina)
    }
}
