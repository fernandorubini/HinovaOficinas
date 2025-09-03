package br.com.fernandodev.hinovaoficinas.ui.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.fernandodev.hinovaoficinas.R
import br.com.fernandodev.hinovaoficinas.domain.model.Oficina

class OficinasAdapter(
    private val onClick: (Oficina) -> Unit
) : ListAdapter<Oficina, OficinasAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Oficina>() {
            override fun areItemsTheSame(oldItem: Oficina, newItem: Oficina) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Oficina, newItem: Oficina) =
                oldItem == newItem
        }
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNome: TextView = itemView.findViewById(R.id.tvNome)
        private val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
        private val tvTelefone: TextView = itemView.findViewById(R.id.tvTelefone)

        fun bind(item: Oficina) {
            tvNome.textOrGone(item.nome)
            tvDescricao.textOrGone(item.descricaoCurta.ifBlank { item.descricao })
            tvTelefone.textOrGone(item.telefone)

            itemView.setOnClickListener { onClick(item) }
        }

        private fun TextView.textOrGone(value: String?) {
            val t = value?.trim().orEmpty()
            if (t.isEmpty()) visibility = View.GONE
            else {
                text = t
                visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_oficina, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
