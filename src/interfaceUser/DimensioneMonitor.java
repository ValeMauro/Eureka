package interfaceUser;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DimensioneMonitor {
	
	
	public static void main(String[] args) {
		
		
		// Dichiaro Toolkit e lo Istanzio
		Toolkit MyTool = Toolkit.getDefaultToolkit();
		
		// Dichiaro tipo Dimension e prelevo la Risoluzione
		Dimension MySkermo = MyTool.getScreenSize();
		
		// Dichiaro e Prelevo l altezza in Int
		int Altezza = (int)MySkermo.getHeight();
		
		// Dichiaro e prelevo la larghezza in Int
		int Larghezza = (int)MySkermo.getWidth();
		
		// Stampo a Video la Risoluzione
		System.out.println("La Tua Risoluzione e:  " + Altezza +" x " + Larghezza);
		
	}

}
