package utils.EFX;

import org.lwjgl.openal.EFX10;

public class EFXEffectCompressor extends EFXEffect {

	public EFXEffectCompressor() {
		super(EFX10.AL_EFFECT_COMPRESSOR);
	}
	
	/**
	 * The OpenAL Effect Extension Compressor can only be switched on and off – it cannot be
adjusted.
	 * @param passedValue int [0, 1], def: 1
	 */
	
	public void setOnOff(int passedValue){
		if (passedValue < EFX10.AL_COMPRESSOR_MIN_ONOFF)
			addEffectf(EFX10.AL_COMPRESSOR_ONOFF, EFX10.AL_COMPRESSOR_MIN_ONOFF);
		else if (passedValue > EFX10.AL_COMPRESSOR_MAX_ONOFF)
			addEffectf(EFX10.AL_COMPRESSOR_ONOFF, EFX10.AL_COMPRESSOR_MAX_ONOFF);
		else
			addEffectf(EFX10.AL_COMPRESSOR_ONOFF, passedValue);
	}
	
	public int getOnOff(){
		return getEffecti(EFX10.AL_COMPRESSOR_ONOFF);
	}

}
