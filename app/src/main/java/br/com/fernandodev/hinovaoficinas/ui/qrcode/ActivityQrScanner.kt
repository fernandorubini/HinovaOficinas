package br.com.fernandodev.hinovaoficinas.ui.qrcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.fernandodev.hinovaoficinas.R
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.google.android.material.appbar.MaterialToolbar

class ActivityQrScanner : AppCompatActivity() {

    private lateinit var barcodeView: DecoratedBarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { finish() }

        barcodeView = findViewById(R.id.barcode_scanner)
        barcodeView.decodeContinuous {
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        barcodeView.pause()
        super.onPause()
    }
}
