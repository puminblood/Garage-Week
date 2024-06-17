import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class TestSon {
    public static void main(String[] args) {

        final int TailleFFT = 4096; //ou 256, paramètre visant à optimiser la sortie

        Son sonTest = new Son(args[0]);


        final int NombreBlocs = sonTest.donnees().length/TailleFFT;

        int cpt =0;
        int cpt_bon=0;
        float pourcentage;


        /* * * * * * * * * * *
         *  Test du neurone   *
         * * * * * * * * * * * */

        // Création d'un signal test
        Complexe[][] signalTest = conversionSonFFT(sonTest, TailleFFT);

        //Test avec des entrées


        float [] tab = lireTableauFichier("../../BDD/Modele_IA/Neurone.txt");
        final iNeurone n2 = new NeuroneHeaviside(tab.length-1);
        ((NeuroneHeaviside) n2).setSynapses(tab);
        ((NeuroneHeaviside) n2).setBiais(tab[tab.length-1]);


        final Neurone neuroneTest = (Neurone)n2;

        final float[][] entreesTest = new float[NombreBlocs][TailleFFT];
        for(int i=0;i<signalTest.length;i++) {
            entreesTest[i]  = normalisation(modules(signalTest[i]));
            neuroneTest.metAJour(entreesTest[i]);
            if (neuroneTest.sortie()>0.99)
            {
                cpt_bon=cpt_bon+1;
            }
            cpt=cpt+1;
        }

        pourcentage= (float) cpt_bon /cpt*100;
        System.out.println(pourcentage + "%");
        WriteResultat(pourcentage, "../../BDD/donnes/arduino.txt");
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Définition du format souhaité (YY-MM-DD HH-mm)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        // Formatage de la date et de l'heure actuelles
        String formattedDateTime = currentDateTime.format(formatter);
        AppendResultat(formattedDateTime,pourcentage, "../../BDD/donnes/Log.txt");


    }

    public static void WriteResultat(float pourcentage, String filename)
    {
        int resultat;
        // Utilisation de BufferedWriter pour écrire dans le fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            // Écrire le float
            // Saut de ligne après le float
            // Écrire le tableau de float
            // Calcul du résultat en fonction du pourcentage
            if( pourcentage >50) resultat=0;
            else if (pourcentage > 30 && pourcentage< 50) resultat=1;
             else resultat=2;

            // Écriture du résultat dans le fichier
            writer.write(String.valueOf(resultat));
            System.out.println("INITIALISATION SUCCESED\n" + filename);
        }
        catch (IOException e)
        {
            System.err.println("INITIALISATION FAILED : " + e.getMessage());
        }
    }
    public static void AppendResultat(String date, float Resultat, String filename)
    {
        // Utilisation de BufferedWriter pour écrire dans le fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) // Ajout du paramètre true pour le mode append
        {

            writer.write(String.format("%.2f", Resultat));            
            writer.newLine(); // Saut de ligne après chaque valeur
            writer.write(String.valueOf(date));
            writer.newLine(); // Saut de ligne après chaque valeur


            System.out.println(filename+" UPDATED SUCCESSFULLY");
        }
        catch (IOException e)
        {
            System.err.println("UPDATE FAILED : " + e.getMessage());
        }
    }

    public static float[] modules(Complexe[] resultat) {
        float[] modules = new float[resultat.length];
        for (int i = 0; i < resultat.length; ++i)
            modules[i] = (float) resultat[i].mod();
        return modules;
    }

    //Méthode permettant de trouver un maximum, ici pour des modules
    public static float maxModule(float[] modules){
        float maxModule = 0;
        for (Float module : modules) {
            if (module > maxModule) {
                maxModule = module;
            }
        }
        return maxModule;
    }

    //Méthode pour normaliser les modules
    public static float[] normalisation(float[] modules){
        // Normalisation des modules
        float maxModule = maxModule(modules);
        float[] modulesNormalises = new float[modules.length];
        for (int i = 0; i < modules.length; ++i)
            modulesNormalises[i] = modules[i] / maxModule;
        return modulesNormalises;
    }

    public static float[] lireTableauFichier(String filename) {
        List<Float> list = new ArrayList<>();

        //Creation d'un buffer pour lire le fichier
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    list.add(Float.parseFloat(line));
                } catch (NumberFormatException e) {
                    System.err.println("FORMAT ERROR : " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("READ FILE ERROR : " + e.getMessage());
            return null;
        }

        // Conversion de la liste en tableau
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    //Méthode qui convertit un son en un tableau de complexe (coefficients de la FFT)
    public static Complexe[][] conversionSonFFT(Son son, int TailleFFT) {
        int NombreBlocs = son.donnees().length/TailleFFT;

        //Création d'un tableau de signaux complexes à partir des données du son
        Complexe[][] signaux = new Complexe[NombreBlocs][TailleFFT];
        for(int i = 0; i < NombreBlocs; ++i)
            for (int j = 0; j < TailleFFT; ++j)
                signaux[i][j] = new ComplexeCartesien(son.bloc_deTaille(i, TailleFFT)[j], 0);

        //Application de la FFT sur les différents blocs
        Complexe[][] FFTs = new Complexe[NombreBlocs][TailleFFT];
        for (int i = 0; i < NombreBlocs; ++i)
            FFTs[i] = FFTCplx.appliqueSur(signaux[i]);

        return FFTs; //On renvoie le tableau de complexes par bloc de FFT
    }

}
