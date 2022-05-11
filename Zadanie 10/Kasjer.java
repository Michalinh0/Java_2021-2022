import java.util.List;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.Collections;

public class Kasjer implements KasjerInterface 
{
	ArrayList <Pieniadz> kasa = new ArrayList <Pieniadz>();
	ArrayList <Pieniadz> r = new ArrayList<Pieniadz>();
	ArrayList <Pieniadz> nr = new ArrayList<Pieniadz>();
	int kasar;
	int kasanr;
	RozmieniaczInterface rozmien;
	@Override
	public List<Pieniadz> rozlicz(int cena, List<Pieniadz> pieniadze) 
	{
		ArrayList <Pieniadz> reszta = new ArrayList<Pieniadz>();
		int suma = 0;
		int suma_r = 0;
		int suma_nr = 0;
		for(Pieniadz i : pieniadze)
		{
			suma += i.wartosc();
			if(i.czyMozeBycRozmieniony())
			{
				suma_r += i.wartosc();
				r.add(i);
				kasar += i.wartosc();
			}
			else
			{	
				suma_nr += i.wartosc();
				nr.add(i);
				kasanr += i.wartosc();
			}
			kasa.add(i);
		}
		Collections.sort(r, (p1, p2) -> p1.wartosc() - p2.wartosc());
		Collections.reverse(r);
		System.out.println("R = " + r);
		Collections.sort(nr, (p1, p2) -> p1.wartosc() - p2.wartosc());
		Collections.reverse(nr);
		int reszt = suma - cena;
		if(reszt == 0)
			return reszta;
		else if (suma_r > 0)
		{
			while(reszta.isEmpty())
				reszta = composereszta(reszt);
		}
		else
		{
			while(reszta.isEmpty())
				reszta = composereszta(reszt);
			Pieniadz smallnr = null;
			for(Pieniadz i : pieniadze)
			{
				if(smallnr == null&&!i.czyMozeBycRozmieniony())
					smallnr = i;
				else if(!i.czyMozeBycRozmieniony()&&i.wartosc()<smallnr.wartosc())
					smallnr = i;
			}
			reszta.add(smallnr);
		}
		for(Pieniadz i : reszta)
		{
			kasa.remove(i);
			if(i.czyMozeBycRozmieniony())
			{	
				r.remove(i);
				kasar -= i.wartosc();
			}
			else
			{	
				nr.remove(i);
				kasanr -= i.wartosc();
			}	
		}
		System.out.println(reszta);
		return reszta;
	}

	@Override
	public List<Pieniadz> stanKasy() 
	{		
		return kasa;
	}

	@Override
	public void dostępDoRozmieniacza(RozmieniaczInterface rozmieniacz) 
	{
		rozmien = rozmieniacz;
	}

	@Override
	public void dostępDoPoczątkowegoStanuKasy(Supplier<Pieniadz> dostawca) 
	{
		Pieniadz nominal;
		do
		{
			nominal = dostawca.get();
			if(nominal != null)
			{
				kasa.add(nominal);
				if(nominal.czyMozeBycRozmieniony())
				{
					r.add(nominal);
					kasar += nominal.wartosc();
				}
				else
				{	
					nr.add(nominal);
					kasanr += nominal.wartosc();
				}
			}
			
		}
		while(nominal != null);
	}
	
	public ArrayList<Pieniadz> composereszta(int reszta)
	{
		ArrayList<Pieniadz> x = new ArrayList<Pieniadz>();
		int rem = reszta;
		for(Pieniadz i : r)
		{
			if(i.wartosc()<=rem)
			{
				x.add(i);
				rem -= i.wartosc();
			}
			if(rem==0)
				break;
		}
		if(rem > 0)
		{
			Collections.sort(r, (p1, p2) -> p1.wartosc() - p2.wartosc());
			Collections.reverse(r);
			rozmien(r.get(0));
			x.clear();
		}
		return x;
	}
	
	public void rozmien(Pieniadz hajs)
	{
		System.out.println(r);
		System.out.println("nothing");
		List <Pieniadz> x = rozmien.rozmien(hajs);
		kasa.remove(hajs);
		r.remove(hajs);
		for(Pieniadz i : x)
		{
			kasa.add(i);
			r.add(i);
		}
		System.out.println(r);
	}
}
// 3 kubki z yerba mate pozniej
// I 4 godziny vibeowania do https://www.youtube.com/watch?v=dQw4w9WgXcQ
// Prayge