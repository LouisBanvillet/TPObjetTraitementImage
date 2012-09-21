import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//afficher l'histogramme du fichier lena.pgm
		afficherHistogramme(histogramme("lena"));
		
		//creer un fichier pgm correspondant à l'histogramme du fichier lena.pgm
		creerHistogramme(histogramme("lena"), "histoLena");
		
		//Effectuer un seuillage sur le fichier lena.pgm pour une valeur de 128
		seuillage(128, "lena", "lenaSeuil");
	}


	//fonction de seuillage
	public static void seuillage(int a, String fichierEntree, String fichierSortie){
		try {
			InputStream ips = new FileInputStream(fichierEntree + ".pgm");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);

			//fichier de sortie
			PrintWriter out  = new PrintWriter(new FileWriter(fichierSortie + ".pgm"));

			//On passe les 4 premières lignes
			for(int i = 0; i<4; i++){
				out.println(br.readLine());
			}

			//on remplit le tableau de l'histogramme
			for(int i = 0; i<512*512; i++){
				int valeurSeuil;
				if(Integer.parseInt(br.readLine()) < a){
					valeurSeuil=0;
				}
				else{valeurSeuil=255;}
				out.println(valeurSeuil);
			}

			br.close();
			out.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		} 
	}

	//Histogramme correspondant à un fichier PGM
	public static int[] histogramme(String fichier){
		int histogramme[] = new int[256];

		try {
			InputStream ips = new FileInputStream(fichier + ".pgm");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;

			//On passe les 4 premières lignes
			for(int i = 0; i<4; i++){
				br.readLine();
			}

			//on remplit le tableau de l'histogramme
			for(int i = 0; i<512*512; i++){
				histogramme[Integer.parseInt(br.readLine())]++;
			}

			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} 

		return histogramme;
	}

	//Afficher l'histogramme dans la console
	public static void afficherHistogramme(int[] histogramme){

		for(int i=0; i<histogramme.length; i++){
			System.out.println("valeur " + i + ": " + histogramme[i]);
		}
		
	}
	
	//Créer un fichier pgm représentant l'histogramme
	public static void creerHistogramme(int[] histogramme, String fichierSortie){
		
		try{
		      PrintWriter out  = new PrintWriter(new FileWriter(fichierSortie + ".pgm"));
		      
		      out.println("P2");
		      out.println("# Histogramme");
		      out.println("256 256");
		      out.println("255");
		      
		      int maxHist = 0;
		      
		      //On récupère la valeur max de l'histogramme pour appliquer une règle de trois aux valeurs de l'histogramme
		      for(int i=0; i<histogramme.length; i++){
		    	  if(histogramme[i]>maxHist){maxHist=histogramme[i];}
		      }
		      
		      for(int i=0; i<histogramme.length; i++){
		    	  out.println((int) histogramme[i]*256/maxHist);
		      }
		      out.close();
		    }
		    catch(Exception e){
		      e.printStackTrace();
		    }
	}

}
