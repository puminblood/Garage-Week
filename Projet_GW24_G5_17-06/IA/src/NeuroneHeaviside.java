public class NeuroneHeaviside extends Neurone
{
	// Fonction d'activation d'un neurone (peut facilement être modifiée par héritage)
	protected float activation(final float valeur) {return valeur >= 0 ? 1.f : 0.f;}
	
	// Constructeur
	public NeuroneHeaviside(final int nbEntrees) {super(nbEntrees);}

	public void setSynapses(float[] syn){
		for(int i = 0; i < syn.length - 1; i++)
			this.synapses[i] = syn[i];
	}
	public void setBiais(float valeur){this.biais = valeur;}
}
