package ar.edu.utn.frba.dds.OPF5.ui.adapters

import com.uqbar.commons.collections.Transformer
import java.awt.Color
import ar.edu.utn.frba.dds.OPF5.domain.Jugador

object ColorTransformer extends Transformer[Boolean, Color] {

  override def transform(condicion: Boolean) = {
    if (condicion)
      Color.BLUE
    else
      Color.WHITE
  }
}