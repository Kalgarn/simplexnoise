package game.mapping;

public class SimplexNoise
{
	SimplexNoiseOctave[] octaves;
    double[] frequencys;
    double[] amplitudes;
    double persistence;
    
	public SimplexNoise(final int octaves, final double persistence, final int seed)
	{
		this.persistence = persistence;
		
		this.octaves = new SimplexNoiseOctave[octaves];
		frequencys = new double[octaves];
		amplitudes = new double[octaves];

        final java.util.Random rnd = new java.util.Random(seed);

        for(int i=0;i<octaves;i++)
        {
            this.octaves[i] = new SimplexNoiseOctave(rnd.nextInt());
            frequencys[i] = Math.pow(2,i);
            amplitudes[i] = Math.pow(persistence, this.octaves.length-i);
        }
	}
	
	public double getNoise(final int x, final int y)
	{
		double result=0;
	
		for(int i=0; i<octaves.length; i++)
		{
			result = result + octaves[i].noise(x/frequencys[i], y/frequencys[i]) * amplitudes[i];
		}
		
		return result;
	}

    public double getNoise(final int x, final int y, final int z)
    {
		double result=0;
		
		for(int i=0; i<octaves.length; i++)
		{
			double frequency = Math.pow(2,i);
			double amplitude = Math.pow(persistence,octaves.length-i);
			
			result = result + octaves[i].noise(x/frequency, y/frequency,z/frequency) * amplitude;
		}
		
		return result;
    }
}
