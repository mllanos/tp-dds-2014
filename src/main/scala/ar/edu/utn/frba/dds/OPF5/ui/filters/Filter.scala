package ar.edu.utn.frba.dds.OPF5.ui.filters

trait FilterByValue[T, R] {
  def apply(obj: T, condition: R): Boolean
  def getString: String
}

trait Filter[T] {
  def apply(obj: T): Boolean
  def getString: String
}