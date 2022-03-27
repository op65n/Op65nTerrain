package op65n.tech.terrain.common.noise.component;

public final class Octave {

    private final double amplitude;
    private final double frequency;

    public Octave(final double amplitude, final double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    public double getFrequency() {
        return this.frequency;
    }

    public double getAmplitude() {
        return this.amplitude;
    }
}