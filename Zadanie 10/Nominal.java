public enum Nominal {
	ZĹ1(1), ZĹ2(2), ZĹ5(5), ZĹ10(10), ZĹ20(20), ZĹ50(50), ZĹ100(100), ZĹ200(200), ZĹ500(500);
	
	private int wartoĹÄ;
	
	private Nominal( int wartoĹÄ ) {
		this.wartoĹÄ = wartoĹÄ;
	}
	
	public int wartoĹÄ() {
		return wartoĹÄ;
	}
}