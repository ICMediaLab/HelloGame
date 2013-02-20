package utils.EFX;

import org.lwjgl.openal.EFX10;

public class EFXEffectDistortion extends EFXEffect {

	public EFXEffectDistortion() {
		super(EFX10.AL_EFFECT_DISTORTION);
	}
	
	public void setEdge(float passedValue){
		if (passedValue < EFX10.AL_DISTORTION_MIN_EDGE)
			addEffectf(EFX10.AL_DISTORTION_EDGE, EFX10.AL_DISTORTION_MIN_EDGE);
		else if (passedValue > EFX10.AL_DISTORTION_MAX_EDGE)
			addEffectf(EFX10.AL_DISTORTION_EDGE, EFX10.AL_DISTORTION_MAX_EDGE);
		else
			addEffectf(EFX10.AL_DISTORTION_EDGE, passedValue);
	}
	
	public void setGain(float passedValue){
		if (passedValue < EFX10.AL_DISTORTION_MIN_GAIN)
			addEffectf(EFX10.AL_DISTORTION_GAIN, EFX10.AL_DISTORTION_MIN_GAIN);
		else if (passedValue > EFX10.AL_DISTORTION_MAX_GAIN)
			addEffectf(EFX10.AL_DISTORTION_GAIN, EFX10.AL_DISTORTION_MAX_GAIN);
		else
			addEffectf(EFX10.AL_DISTORTION_GAIN, passedValue);
	}
	
	public void setLowpassCutoff(float passedValue){
		if (passedValue < EFX10.AL_DISTORTION_MIN_LOWPASS_CUTOFF)
			addEffectf(EFX10.AL_DISTORTION_LOWPASS_CUTOFF, EFX10.AL_DISTORTION_MIN_LOWPASS_CUTOFF);
		else if (passedValue > EFX10.AL_DISTORTION_MAX_LOWPASS_CUTOFF)
			addEffectf(EFX10.AL_DISTORTION_LOWPASS_CUTOFF, EFX10.AL_DISTORTION_MAX_LOWPASS_CUTOFF);
		else
			addEffectf(EFX10.AL_DISTORTION_LOWPASS_CUTOFF, passedValue);
	}
	
	public void setEqCenter(float passedValue){
		if (passedValue < EFX10.AL_DISTORTION_MIN_EQCENTER)
			addEffectf(EFX10.AL_DISTORTION_EQCENTER, EFX10.AL_DISTORTION_MIN_EQCENTER);
		else if (passedValue > EFX10.AL_DISTORTION_MAX_EQCENTER)
			addEffectf(EFX10.AL_DISTORTION_EQCENTER, EFX10.AL_DISTORTION_MAX_EQCENTER);
		else
			addEffectf(EFX10.AL_DISTORTION_EQCENTER, passedValue);
	}
	
	public void setEqBandwitdth(float passedValue){
		if (passedValue < EFX10.AL_DISTORTION_MIN_EQBANDWIDTH)
			addEffectf(EFX10.AL_DISTORTION_EQBANDWIDTH, EFX10.AL_DISTORTION_MIN_EQBANDWIDTH);
		else if (passedValue > EFX10.AL_DISTORTION_MAX_EQBANDWIDTH)
			addEffectf(EFX10.AL_DISTORTION_EQBANDWIDTH, EFX10.AL_DISTORTION_MAX_EQBANDWIDTH);
		else
			addEffectf(EFX10.AL_DISTORTION_EQBANDWIDTH, passedValue);
	}

	public float getEdge(){
		return getEffectf(EFX10.AL_DISTORTION_EDGE);
	}
	
	public float getGain(){
		return getEffectf(EFX10.AL_DISTORTION_GAIN);
	}
	
	public float getLowpassCutoff(){
		return getEffectf(EFX10.AL_DISTORTION_LOWPASS_CUTOFF);
	}
	
	public float getEqCenter(){
		return getEffectf(EFX10.AL_DISTORTION_EQCENTER);
	}
	
	public float getEqBandwidth(){
		return getEffectf(EFX10.AL_DISTORTION_EQBANDWIDTH);
	}
	
}
