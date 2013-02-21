package sounds;

import org.lwjgl.openal.EFX10;
import org.newdawn.slick.util.EFXEffect;
public class EFXEffectChorus extends EFXEffect {

	public EFXEffectChorus() {
		super(EFX10.AL_EFFECT_CHORUS);
	}

	/**
	 * This property sets the waveform shape of the LFO that controls the delay time of the delayed
signals.
	 * @param passedValue float [0(sin), 1(triangle)] def: 1
	 */
	
	public void setWaveform(int passedValue){
		if (passedValue < EFX10.AL_CHORUS_MIN_WAVEFORM)
			addEffectf(EFX10.AL_CHORUS_WAVEFORM, EFX10.AL_CHORUS_MIN_WAVEFORM);
		else if (passedValue > EFX10.AL_CHORUS_MAX_WAVEFORM)
			addEffectf(EFX10.AL_CHORUS_WAVEFORM, EFX10.AL_CHORUS_MAX_WAVEFORM);
		else
			addEffectf(EFX10.AL_CHORUS_WAVEFORM, passedValue);
	}
	
	/**
	 * This property controls the phase difference between the left and right LFO’s. At zero degrees the
two LFOs are synchronized. Use this parameter to create the illusion of an expanded stereo field
of the output signal.
	 * @param passedValue float [-180, 180] def: 90
	 */
	
	public void setPhase(int passedValue){
		if (passedValue < EFX10.AL_CHORUS_MIN_PHASE)
			addEffectf(EFX10.AL_CHORUS_PHASE, EFX10.AL_CHORUS_MIN_PHASE);
		else if (passedValue > EFX10.AL_CHORUS_MAX_PHASE)
			addEffectf(EFX10.AL_CHORUS_PHASE, EFX10.AL_CHORUS_MAX_PHASE);
		else
			addEffectf(EFX10.AL_CHORUS_PHASE, passedValue);
	}
	
	/**
	 * This property sets the modulation rate of the LFO that controls the delay time of the delayed
signals.
	 * @param passedValue float [0.0, 10.0] def: 1.1
	 */
	
	public void setRate(float passedValue){
		if (passedValue < EFX10.AL_CHORUS_MIN_RATE)
			addEffectf(EFX10.AL_CHORUS_RATE, EFX10.AL_CHORUS_MIN_RATE);
		else if (passedValue > EFX10.AL_CHORUS_MAX_RATE)
			addEffectf(EFX10.AL_CHORUS_RATE, EFX10.AL_CHORUS_MAX_RATE);
		else
			addEffectf(EFX10.AL_CHORUS_RATE, passedValue);
	}
	
	/**
	 * This property controls the amount by which the delay time is modulated by the LFO.
	 * @param passedValue float [0.0, 1.0] def: 0.1
	 */
	
	public void setDepth(float passedValue){
		if (passedValue < EFX10.AL_CHORUS_MIN_DEPTH)
			addEffectf(EFX10.AL_CHORUS_DEPTH, EFX10.AL_CHORUS_MIN_DEPTH);
		else if (passedValue > EFX10.AL_CHORUS_MAX_DEPTH)
			addEffectf(EFX10.AL_CHORUS_DEPTH, EFX10.AL_CHORUS_MAX_DEPTH);
		else
			addEffectf(EFX10.AL_CHORUS_DEPTH, passedValue);
	}
	
	/**
	 * This property controls the amount of processed signal that is fed back to the input of the chorus
effect. Negative values will reverse the phase of the feedback signal. At full magnitude the
identical sample will repeat endlessly. At lower magnitudes the sample will repeat and fade out
over time. Use this parameter to create a “cascading” chorus effect.
	 * @param passedValue float [-1.0, 1.0] def: 0.25
	 */
	
	public void setFeedback(float passedValue){
		if (passedValue < EFX10.AL_CHORUS_MIN_FEEDBACK)
			addEffectf(EFX10.AL_CHORUS_FEEDBACK, EFX10.AL_CHORUS_MIN_FEEDBACK);
		else if (passedValue > EFX10.AL_CHORUS_MAX_FEEDBACK)
			addEffectf(EFX10.AL_CHORUS_FEEDBACK, EFX10.AL_CHORUS_MAX_FEEDBACK);
		else
			addEffectf(EFX10.AL_CHORUS_FEEDBACK, passedValue);
	}
	
	/**
	 * This property controls the average amount of time the sample is delayed before it is played back,
and with feedback, the amount of time between iterations of the sample. Larger values lower the
pitch. Smaller values make the chorus sound like a flanger, but with different frequency
characteristics.
	 * @param passedValue float [0.0, 0.016] def: 0.016
	 */
	
	public void setDelay(float passedValue){
		if (passedValue < EFX10.AL_CHORUS_MIN_DELAY)
			addEffectf(EFX10.AL_CHORUS_DELAY, EFX10.AL_CHORUS_MIN_DELAY);
		else if (passedValue > EFX10.AL_CHORUS_MAX_DELAY)
			addEffectf(EFX10.AL_CHORUS_DELAY, EFX10.AL_CHORUS_MAX_DELAY);
		else
			addEffectf(EFX10.AL_CHORUS_DELAY, passedValue);
	}
	
	public int getWaveform(){
		return getEffecti(EFX10.AL_CHORUS_WAVEFORM);
	}
	
	public int getPhase(){
		return getEffecti(EFX10.AL_CHORUS_PHASE);
	}
	
	public float getRate(){
		return getEffectf(EFX10.AL_CHORUS_RATE);
	}
	
	public float getDepth(){
		return getEffectf(EFX10.AL_CHORUS_DEPTH);
	}
	
	public float getFeedback(){
		return getEffectf(EFX10.AL_CHORUS_FEEDBACK);
	}
	
	public float getDelay(){
		return getEffectf(EFX10.AL_CHORUS_DELAY);
	}
}
