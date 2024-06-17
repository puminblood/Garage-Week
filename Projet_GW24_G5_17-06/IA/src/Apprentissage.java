import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Apprentissage {
    public static void main(String[] args){
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * Initialisation de la chaîne avec les sons ainsi que le traitement *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        final int TailleFFT = 4096; 

        Son sonApprentissage = new Son("./BDD/SourcesSonores/normal/SonNormal1.wav");
        Son bruitApprentissage = new Son("./BDD/SourcesSonores/bruit/SonBruite1.wav");



        final int NombreBlocs = sonApprentissage.donnees().length/TailleFFT; 
        Complexe[][] FFTs = conversionSonFFT(sonApprentissage, TailleFFT);

        Complexe[][] bruits = conversionSonFFT(bruitApprentissage, TailleFFT);

        float[][] entrees = new float[NombreBlocs][TailleFFT];
        float[] resultats = new float[NombreBlocs];
        for (int i = 0; i < NombreBlocs*4/5; ++i){
            entrees[i] = normalisation(modules(FFTs[i]));
            resultats[i] = 1;
        }
        for(int i = NombreBlocs*4/5 ; i< NombreBlocs; i++)
        {
            entrees[i] = normalisation(modules(bruits[i]));
            resultats[i] = 0;
        }


        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * Apprentissage de neurone avec fonction d'activation quelconque  *
        * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        final iNeurone n = new NeuroneHeaviside(entrees[0].length);

        System.out.println("Apprentissage…");
        System.out.println("Nombre de tours : "+n.apprentissage(entrees, resultats));
        final Neurone vueNeurone = (Neurone)n;
        System.out.print("Synapses : ");
        for (final float f : vueNeurone.synapses())
            System.out.print(f+" ");
        System.out.print("\nBiais : ");
        System.out.println(vueNeurone.biais());
        ecrireTableauFichier((Neurone) n,"./BDD/Modele_IA/test.txt");
        
    }

    public static float[] modules(Complexe[] resultat) {
        float[] modules = new float[resultat.length];
        for (int i = 0; i < resultat.length; ++i)
            modules[i] = (float) resultat[i].mod();
        return modules;
    }

    public static float maxModule(float[] modules){
        float maxModule = 0;
        for (Float module : modules) {
            if (module > maxModule) {
                maxModule = module;
            }
        }
        return maxModule;
    }

    public static float[] normalisation(float[] modules){
        float maxModule = maxModule(modules);
        float[] modulesNormalises = new float[modules.length];
        for (int i = 0; i < modules.length; ++i)
            modulesNormalises[i] = modules[i] / maxModule;
        return modulesNormalises;
    }

    public static Complexe[][] conversionSonFFT(Son son, int TailleFFT) {
        int NombreBlocs = son.donnees().length/TailleFFT;

        Complexe[][] signaux = new Complexe[NombreBlocs][TailleFFT];
        for(int i = 0; i < NombreBlocs; ++i)
            for (int j = 0; j < TailleFFT; ++j)
                signaux[i][j] = new ComplexeCartesien(son.bloc_deTaille(i, TailleFFT)[j], 0);

        Complexe[][] FFTs = new Complexe[NombreBlocs][TailleFFT];
        for (int i = 0; i < NombreBlocs; ++i)
            FFTs[i] = FFTCplx.appliqueSur(signaux[i]);

        return FFTs; 
    }
    public static void ecrireTableauFichier(Neurone neurone, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
           
            for (float value : neurone.synapses()) {
                writer.write(Float.toString(value));
                writer.newLine(); 
            }
            writer.write(Float.toString(neurone.biais()));
            writer.newLine();

            System.out.println("Float et tableau de float écrits dans le fichier " + filename);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }
    public static float[] readFloatAndArrayFromFile(String filename) {
        List<Float> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    list.add(Float.parseFloat(line));
                } catch (NumberFormatException e) {
                    System.err.println("Erreur de formatage de nombre : " + line);
                }
            }
            System.out.println("Lecture du fichier " + filename + " réussie.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            return null;
        }

        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

}