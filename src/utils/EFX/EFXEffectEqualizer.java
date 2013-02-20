package utils.EFX;

import org.lwjgl.openal.EFX10;

public class EFXEffectEqualizer extends EFXEffect {

	public EFXEffectEqualizer() {
		super(EFX10.AL_EFFECT_EQUALIZER);
	}
	
	/**
	 * This property controls amount of cut or boost on the low frequency range.
	 * @param passedValue float [0.126, 7.943] def: 1.0
	 */
	
	public void setLowGain(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_LOW_GAIN)
			addEffectf(EFX10.AL_EQUALIZER_LOW_GAIN, EFX10.AL_EQUALIZER_MIN_LOW_GAIN);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_LOW_GAIN)
			addEffectf(EFX10.AL_EQUALIZER_LOW_GAIN, EFX10.AL_EQUALIZER_MAX_LOW_GAIN);
		else
			addEffectf(EFX10.AL_EQUALIZER_LOW_GAIN, passedValue);
	}
	
	/**
	 * This property controls the low frequency below which signal will be cut off.
	 * @param passedValue float [50.0, 800.0] def: 200.0
	 */
	
	public void setLowCutoff(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_LOW_CUTOFF)
			addEffectf(EFX10.AL_EQUALIZER_LOW_CUTOFF, EFX10.AL_EQUALIZER_MIN_LOW_CUTOFF);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_LOW_CUTOFF)
			addEffectf(EFX10.AL_EQUALIZER_LOW_CUTOFF, EFX10.AL_EQUALIZER_MAX_LOW_CUTOFF);
		else
			addEffectf(EFX10.AL_EQUALIZER_LOW_CUTOFF, passedValue);
	}
	
	/**
	 * This property allows you to cut / boost signal on the “mid1” range.
	 * @param passedValue float [0.126, 7.943] def: 1.0
	 */
	
	public void setMid1Gain(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_MID1_GAIN)
			addEffectf(EFX10.AL_EQUALIZER_MID1_GAIN, EFX10.AL_EQUALIZER_MIN_MID1_GAIN);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_MID1_GAIN)
			addEffectf(EFX10.AL_EQUALIZER_MID1_GAIN, EFX10.AL_EQUALIZER_MAX_MID1_GAIN);
		else
			addEffectf(EFX10.AL_EQUALIZER_MID1_GAIN, passedValue);
	}
	
	/**
	 * This property sets the center frequency for the “mid1” range.
	 * @param passedValue float [200.0, 3000.0] def: 500.0
	 */
	
	public void setMid1Center(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_MID1_CENTER)
			addEffectf(EFX10.AL_EQUALIZER_MID1_CENTER, EFX10.AL_EQUALIZER_MIN_MID1_CENTER);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_MID1_CENTER)
			addEffectf(EFX10.AL_EQUALIZER_MID1_CENTER, EFX10.AL_EQUALIZER_MAX_MID1_CENTER);
		else
			addEffectf(EFX10.AL_EQUALIZER_MID1_CENTER, passedValue);
	}
	
	/**
	 * This property controls the width of the “mid1” range.
	 * @param passedValue float [0.01, 1.0] def: 1.0
	 */
	
	public void setMid1Width(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_MID1_WIDTH)
			addEffectf(EFX10.AL_EQUALIZER_MID1_WIDTH, EFX10.AL_EQUALIZER_MIN_MID1_WIDTH);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_MID1_WIDTH)
			addEffectf(EFX10.AL_EQUALIZER_MID1_WIDTH, EFX10.AL_EQUALIZER_MAX_MID1_WIDTH);
		else
			addEffectf(EFX10.AL_EQUALIZER_MID1_WIDTH, passedValue);
	}
	
	/**
	 * This property allows you to cut / boost signal on the “mid2” range.
	 * @param passedValue float [0.126, 7.943] def: 1.0
	 */
	
	public void setMid2Gain(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_MID2_GAIN)
			addEffectf(EFX10.AL_EQUALIZER_MID2_GAIN, EFX10.AL_EQUALIZER_MIN_MID2_GAIN);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_MID2_GAIN)
			addEffectf(EFX10.AL_EQUALIZER_MID2_GAIN, EFX10.AL_EQUALIZER_MAX_MID2_GAIN);
		else
			addEffectf(EFX10.AL_EQUALIZER_MID2_GAIN, passedValue);
	}
	
	/**
	 * This property sets the center frequency for the “mid2” range.
	 * @param passedValue float [1000.0, 8000.0] def: 3000.0
	 */
	
	public void setMid2Center(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_MID2_CENTER)
			addEffectf(EFX10.AL_EQUALIZER_MID2_CENTER, EFX10.AL_EQUALIZER_MIN_MID2_CENTER);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_MID2_CENTER)
			addEffectf(EFX10.AL_EQUALIZER_MID2_CENTER, EFX10.AL_EQUALIZER_MAX_MID2_CENTER);
		else
			addEffectf(EFX10.AL_EQUALIZER_MID2_CENTER, passedValue);
	}
	
	/**
	 * This property controls the width of the “mid2” range.
	 * @param passedValue float [0.01, 1.0] def: 1.0
	 */
	
	public void setMid2Width(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_MID2_WIDTH)
			addEffectf(EFX10.AL_EQUALIZER_MID2_WIDTH, EFX10.AL_EQUALIZER_MIN_MID2_WIDTH);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_MID2_WIDTH)
			addEffectf(EFX10.AL_EQUALIZER_MID2_WIDTH, EFX10.AL_EQUALIZER_MAX_MID2_WIDTH);
		else
			addEffectf(EFX10.AL_EQUALIZER_MID2_WIDTH, passedValue);
	}
	
	/**
	 * This property allows you to cut / boost the signal at high frequencies.
	 * @param passedValue float [0.126, 7.943] def: 1.0
	 */
	
	public void setHighGain(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_HIGH_GAIN)
			addEffectf(EFX10.AL_EQUALIZER_HIGH_GAIN, EFX10.AL_EQUALIZER_MIN_HIGH_GAIN);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_HIGH_GAIN)
			addEffectf(EFX10.AL_EQUALIZER_HIGH_GAIN, EFX10.AL_EQUALIZER_MAX_HIGH_GAIN);
		else
			addEffectf(EFX10.AL_EQUALIZER_HIGH_GAIN, passedValue);
	}
	
	/**
	 * This property controls the high frequency above which signal will be cut off.
	 * @param passedValue float [4000.0, 16000.0] def: 6000.0
	 */
	
	public void setHighCutoff(float passedValue){
		if (passedValue < EFX10.AL_EQUALIZER_MIN_HIGH_CUTOFF)
			addEffectf(EFX10.AL_EQUALIZER_HIGH_CUTOFF, EFX10.AL_EQUALIZER_MIN_HIGH_CUTOFF);
		else if (passedValue > EFX10.AL_EQUALIZER_MAX_HIGH_CUTOFF)
			addEffectf(EFX10.AL_EQUALIZER_HIGH_CUTOFF, EFX10.AL_EQUALIZER_MAX_HIGH_CUTOFF);
		else
			addEffectf(EFX10.AL_EQUALIZER_HIGH_CUTOFF, passedValue);
	}
	
	public float getLowGain(){
		return getEffectf(EFX10.AL_EQUALIZER_LOW_GAIN);
	}
	
	public float getLowCutoff(){
		return getEffectf(EFX10.AL_EQUALIZER_LOW_CUTOFF);
	}
	
	public float getMid1Gain(){
		return getEffectf(EFX10.AL_EQUALIZER_MID1_GAIN);
	}
	
	public float getMid1Center(){
		return getEffectf(EFX10.AL_EQUALIZER_MID1_CENTER);
	}
	
	public float getMid1Width(){
		return getEffectf(EFX10.AL_EQUALIZER_MID1_WIDTH);
	}
	
	public float getMid2Gain(){
		return getEffectf(EFX10.AL_EQUALIZER_MID2_GAIN);
	}
	
	public float getMid2Center(){
		return getEffectf(EFX10.AL_EQUALIZER_MID2_CENTER);
	}
	
	public float getMid2Width(){
		return getEffectf(EFX10.AL_EQUALIZER_MID2_WIDTH);
	}
	
	public float getHighGain(){
		return getEffectf(EFX10.AL_EQUALIZER_HIGH_GAIN);
	}
	
	public float getHighCutoff(){
		return getEffectf(EFX10.AL_EQUALIZER_HIGH_CUTOFF);
	}

}
