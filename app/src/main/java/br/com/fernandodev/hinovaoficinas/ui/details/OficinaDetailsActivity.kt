package br.com.fernandodev.hinovaoficinas.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fernandodev.hinovaoficinas.R
import br.com.fernandodev.hinovaoficinas.databinding.ActivityOficinaDetailsBinding

class OficinaDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NOME = "extra_nome"
        const val EXTRA_ENDERECO = "extra_endereco"
        const val EXTRA_TELEFONE = "extra_telefone"
    }

    private lateinit var binding: ActivityOficinaDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOficinaDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar (agora existe no layout)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_oficina_details)

        // Extras
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val nome = intent.getStringExtra(EXTRA_NOME).orEmpty()
        val endereco = intent.getStringExtra(EXTRA_ENDERECO).orEmpty()
        val telefone = intent.getStringExtra(EXTRA_TELEFONE).orEmpty()

        // UI
        binding.tvNome.text = nome
        binding.tvEndereco.text =
            if (endereco.isBlank()) getString(R.string.not_informed_address) else endereco
        binding.tvTelefone.text =
            if (telefone.isBlank()) getString(R.string.not_informed_tel) else telefone

        binding.btnLigar.setOnClickListener {
            if (telefone.isNotBlank()) {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telefone")))
            } else {
                Toast.makeText(this, R.string.not_informed_tel, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
}
