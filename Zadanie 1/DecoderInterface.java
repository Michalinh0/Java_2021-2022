/*
 * Abstrakcyjna klasa definiujaca interfejs pozwalajacy na dekodowanie protokolu
 * szeregowego opisanego w zadaniu 01.
 * 
 * @author oramus
 *
 */
public abstract class DecoderInterface {
	/**
	 * Metoda pozwala na dostarczanie danych do zdekodowania. Pojedyncze wywolanie
	 * metody dostarcza jeden bit.
	 * 
	 * @param bit Argumentem wywolania jest dekodowany bit. Argument moze przybrac
	 *            wartosci wylacznie 0 i 1.
	 */
	public abstract void input(int bit);

	/**
	 * Metoda zwraca odkodowane dane. Metoda nigdy nie zwraca null. Jesli jeszcze
	 * zadna liczba nie zostala odkodowana metoda zwraca "" (pusty ciag znakow,
	 * czyli ciag znakow o dlugosci rownej 0).
	 * 
	 * @return Ciag znakow reprezentujacy sekwencje odkodowanych danych.
	 */
	public abstract String output();

	/**
	 * Metoda przywraca stan poczatkowy. Proces odkodowywania danych zaczyna sie od
	 * poczatku.
	 */
	public abstract void reset();
}
