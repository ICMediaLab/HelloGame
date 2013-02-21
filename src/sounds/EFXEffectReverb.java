/* 
 * Copyright (C) 2011 Ethan "flibitijibibo" Lee
 * 
 * This file is part of flibitEFX.
 * 
 * flibitEFX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * flibitEFX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with flibitEFX.  If not, see <http://www.gnu.org/licenses/>.
 */

package sounds;

import org.lwjgl.openal.EFX10;
import org.newdawn.slick.util.EFXEffect;

/**
 * @author Ethan "flibitijibibo" Lee
 */

public class EFXEffectReverb extends EFXEffect {
	
	/** Constructor creates a generic reverb effect. */
	public EFXEffectReverb() {
		super(EFX10.AL_EFFECT_REVERB);
	}
	
	
	/**
	 * Reverb Modal Density controls the coloration of the late reverb. Lowering the value adds more
coloration to the late reverb.
	 * @param passedValue float [0.0, 1.0], def: 1.0
	 */
	public void setDensity(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_DENSITY){
			addEffectf(EFX10.AL_REVERB_DENSITY, EFX10.AL_REVERB_MIN_DENSITY);
		} else if (passedValue > EFX10.AL_REVERB_MAX_DENSITY){
			addEffectf(EFX10.AL_REVERB_DENSITY, EFX10.AL_REVERB_MAX_DENSITY);
		} else {
			addEffectf(EFX10.AL_REVERB_DENSITY, passedValue);
		} 
	}
	
	/**
	 * The Reverb Diffusion property controls the echo density in the reverberation decay. It’s set by
default to 1.0, which provides the highest density. Reducing diffusion gives the reverberation a
more “grainy” character that is especially noticeable with percussive sound sources. If you set a
diffusion value of 0.0, the later reverberation sounds like a succession of distinct echoes.
	 * @param passedValue float [0.0, 1.0], def: 1.0
	 */
	
	public void setDiffusion(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_DIFFUSION){
			addEffectf(EFX10.AL_REVERB_DIFFUSION, EFX10.AL_REVERB_MIN_DIFFUSION);
		} else if (passedValue > EFX10.AL_REVERB_MAX_DIFFUSION){
			addEffectf(EFX10.AL_REVERB_DIFFUSION, EFX10.AL_REVERB_MAX_DIFFUSION);
		} else {
			addEffectf(EFX10.AL_REVERB_DIFFUSION, passedValue);
		} 
	}
	
	/**
	 * The Reverb Gain property is the master volume control for the reflected sound (both early
reflections and reverberation) that the reverb effect adds to all sound sources. It sets the
maximum amount of reflections and reverberation added to the final sound mix. The value of the
Reverb Gain property ranges from 1.0 (0db) (the maximum amount) to 0.0 (-100db) (no reflected
sound at all).
	 * @param passedValue float [0.0, 1.0], def: 0.32
	 */
	
	public void setGain(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_GAIN){
			addEffectf(EFX10.AL_REVERB_GAIN, EFX10.AL_REVERB_MIN_GAIN);
		} else if (passedValue > EFX10.AL_REVERB_MAX_GAIN){
			addEffectf(EFX10.AL_REVERB_GAIN, EFX10.AL_REVERB_MAX_GAIN);
		} else {
			addEffectf(EFX10.AL_REVERB_GAIN, passedValue);
		} 
	}
	
	/**
	 * The Reverb Gain HF property further tweaks reflected sound by attenuating it at high frequencies.
It controls a low-pass filter that applies globally to the reflected sound of all sound sources
feeding the particular instance of the reverb effect. The value of the Reverb Gain HF property
ranges from 1.0 (0db) (no filter) to 0.0 (-100db) (virtually no reflected sound).
	 * @param passedValue float [0.0, 1.0], def: 0.89
	 */
	
	public void setGainHF(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_GAINHF){
			addEffectf(EFX10.AL_REVERB_GAINHF, EFX10.AL_REVERB_MIN_GAINHF);
		} else if (passedValue > EFX10.AL_REVERB_MAX_GAINHF){
			addEffectf(EFX10.AL_REVERB_GAINHF, EFX10.AL_REVERB_MAX_GAINHF);
		} else {
			addEffectf(EFX10.AL_REVERB_GAINHF, passedValue);
		} 
	}
	
	/**
	 * The Decay Time property sets the reverberation decay time. It ranges from 0.1 (typically a small
room with very dead surfaces) to 20.0 (typically a large room with very live surfaces).
	 * @param passedValue float [0.1, 20.0], def: 1.49
	 */
	
	public void setDecayTime(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_DECAY_TIME){
			addEffectf(EFX10.AL_REVERB_DECAY_TIME, EFX10.AL_REVERB_MIN_DECAY_TIME);
		} else if (passedValue > EFX10.AL_REVERB_MAX_DECAY_TIME){
			addEffectf(EFX10.AL_REVERB_DECAY_TIME, EFX10.AL_REVERB_MAX_DECAY_TIME);
		} else {
			addEffectf(EFX10.AL_REVERB_DECAY_TIME, passedValue);
		} 
	}
	
	/**
	 * The Decay HF Ratio property sets the spectral quality of the Decay Time parameter. It is the
ratio of high-frequency decay time relative to the time set by Decay Time. The Decay HF Ratio
value 1.0 is neutral: the decay time is equal for all frequencies. As Decay HF Ratio increases
above 1.0, the high-frequency decay time increases so it’s longer than the decay time at low
frequencies. You hear a more brilliant reverberation with a longer decay at high frequencies. As
the Decay HF Ratio value decreases below 1.0, the high-frequency decay time decreases so it’s
shorter than the decay time of the low frequencies. You hear a more natural reverberation.
	 * @param passedValue float [0.1, 2.0], def: 0.83
	 */
	
	public void setDecayHFRation(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_DECAY_HFRATIO){
			addEffectf(EFX10.AL_REVERB_DECAY_HFRATIO, EFX10.AL_REVERB_MIN_DECAY_HFRATIO);
		} else if (passedValue > EFX10.AL_REVERB_MAX_DECAY_HFRATIO){
			addEffectf(EFX10.AL_REVERB_DECAY_HFRATIO, EFX10.AL_REVERB_MAX_DECAY_HFRATIO);
		} else {
			addEffectf(EFX10.AL_REVERB_DECAY_HFRATIO, passedValue);
		} 
	}
	
	/**
	 * The Reflections Gain property controls the overall amount of initial reflections relative to the Gain
property. (The Gain property sets the overall amount of reflected sound: both initial reflections
and later reverberation.) The value of Reflections Gain ranges from a maximum of 3.16 (+10 dB)
to a minimum of 0.0 (-100 dB) (no initial reflections at all), and is corrected by the value of the
Gain property. The Reflections Gain property does not affect the subsequent reverberation
decay.
You can increase the amount of initial reflections to simulate a more narrow space or closer walls,
especially effective if you associate the initial reflections increase with a reduction in reflections
delays by lowering the value of the Reflection Delay property. To simulate open or semi-open
environments, you can maintain the amount of early reflections while reducing the value of the
Late Reverb Gain property, which controls later reflections.
	 * @param passedValue float [0.0, 3.16], def: 0.05
	 */
	
	public void setReflectionsGain(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_REFLECTIONS_GAIN){
			addEffectf(EFX10.AL_REVERB_REFLECTIONS_GAIN, EFX10.AL_REVERB_MIN_REFLECTIONS_GAIN);
		} else if (passedValue > EFX10.AL_REVERB_MAX_REFLECTIONS_GAIN){
			addEffectf(EFX10.AL_REVERB_REFLECTIONS_GAIN, EFX10.AL_REVERB_MAX_REFLECTIONS_GAIN);
		} else {
			addEffectf(EFX10.AL_REVERB_REFLECTIONS_GAIN, passedValue);
		} 
	}
	
	/**
	 * The Reflections Delay property is the amount of delay between the arrival time of the direct path
from the source to the first reflection from the source. It ranges from 0 to 300 milliseconds. You
can reduce or increase Reflections Delay to simulate closer or more distant reflective surfaces—
and therefore control the perceived size of the room.
	 * @param passedValue float [0.0, 0.3], def: 0.007
	 */
	
	public void setReflectionsDelay(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_REFLECTIONS_DELAY){
			addEffectf(EFX10.AL_REVERB_REFLECTIONS_DELAY, EFX10.AL_REVERB_MIN_REFLECTIONS_DELAY);
		} else if (passedValue > EFX10.AL_REVERB_MAX_REFLECTIONS_DELAY){
			addEffectf(EFX10.AL_REVERB_REFLECTIONS_DELAY, EFX10.AL_REVERB_MAX_REFLECTIONS_DELAY);
		} else {
			addEffectf(EFX10.AL_REVERB_REFLECTIONS_DELAY, passedValue);
		} 
	}
	
	/**
	 * The Late Reverb Gain property controls the overall amount of later reverberation relative to the
Gain property. (The Gain property sets the overall amount of both initial reflections and later
reverberation.) The value of Late Reverb Gain ranges from a maximum of 10.0 (+20 dB) to a
minimum of 0.0 (-100 dB) (no late reverberation at all).
Note that Late Reverb Gain and Decay Time are independent properties: If you adjust Decay
Time without changing Late Reverb Gain, the total intensity (the averaged square of the
amplitude) of the late reverberation remains constant.
	 * @param passedValue float [0.0, 10.0], def: 1.26
	 */
	
	public void setLateReverbGain(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_LATE_REVERB_GAIN){
			addEffectf(EFX10.AL_REVERB_LATE_REVERB_GAIN, EFX10.AL_REVERB_MIN_LATE_REVERB_GAIN);
		} else if (passedValue > EFX10.AL_REVERB_MAX_LATE_REVERB_GAIN){
			addEffectf(EFX10.AL_REVERB_LATE_REVERB_GAIN, EFX10.AL_REVERB_MAX_LATE_REVERB_GAIN);
		} else {
			addEffectf(EFX10.AL_REVERB_LATE_REVERB_GAIN, passedValue);
		} 
	}
	
/**
 * The Late Reverb Delay property defines the begin time of the late reverberation relative to the
time of the initial reflection (the first of the early reflections). It ranges from 0 to 100 milliseconds.
Reducing or increasing Late Reverb Delay is useful for simulating a smaller or larger room.
 * @param passedValue float [0.0, 0.1], def: 0.011
 */
	
	public void setLateReverbDelay(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_LATE_REVERB_DELAY){
			addEffectf(EFX10.AL_REVERB_LATE_REVERB_DELAY, EFX10.AL_REVERB_MIN_LATE_REVERB_DELAY);
		} else if (passedValue > EFX10.AL_REVERB_MAX_LATE_REVERB_DELAY){
			addEffectf(EFX10.AL_REVERB_LATE_REVERB_DELAY, EFX10.AL_REVERB_MAX_LATE_REVERB_DELAY);
		} else {
			addEffectf(EFX10.AL_REVERB_LATE_REVERB_DELAY, passedValue);
		} 
	}
	
	/**
	 * The Room Rolloff Factor property is one of two methods available to attenuate the reflected
sound (containing both reflections and reverberation) according to source-listener distance. It’s
defined the same way as OpenAL’s Rolloff Factor, but operates on reverb sound instead of
direct-path sound. Setting the Room Rolloff Factor value to 1.0 specifies that the reflected sound
will decay by 6 dB every time the distance doubles. Any value other than 1.0 is equivalent to a
scaling factor applied to the quantity specified by ((Source listener distance) - (Reference
Distance)). Reference Distance is an OpenAL source parameter that specifies the inner border
for distance rolloff effects: if the source comes closer to the listener than the reference distance,
the direct-path sound isn’t increased as the source comes closer to the listener, and neither is the
reflected sound.
The default value of Room Rolloff Factor is 0.0 because, by default, the Effects Extension reverb
effect naturally manages the reflected sound level automatically for each sound source to
simulate the natural rolloff of reflected sound vs. distance in typical rooms. (Note that this isn’t
the case if the source property flag AL_AUXILIARY_SEND_FILTER_GAIN_AUTO is set to
AL_FALSE) You can use Room Rolloff Factor as an option to automatic control so you can
exaggerate or replace the default automatically-controlled rolloff.
	 * @param passedValue float [0.0, 10.0], def: 0.0
	 */
	
	public void setRoomRolloffFactor(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_ROOM_ROLLOFF_FACTOR){
			addEffectf(EFX10.AL_REVERB_ROOM_ROLLOFF_FACTOR, EFX10.AL_REVERB_MIN_ROOM_ROLLOFF_FACTOR);
		} else if (passedValue > EFX10.AL_REVERB_MAX_ROOM_ROLLOFF_FACTOR){
			addEffectf(EFX10.AL_REVERB_ROOM_ROLLOFF_FACTOR, EFX10.AL_REVERB_MAX_ROOM_ROLLOFF_FACTOR);
		} else {
			addEffectf(EFX10.AL_REVERB_ROOM_ROLLOFF_FACTOR, passedValue);
		} 
	}
	
	/**
	 * The Air Absorption Gain HF property controls the distance-dependent attenuation at high
frequencies caused by the propagation medium. It applies to reflected sound only. You can use
Air Absorption Gain HF to simulate sound transmission through foggy air, dry air, smoky
atmosphere, and so on. The default value is 0.994 (-0.05 dB) per meter, which roughly
corresponds to typical condition of atmospheric humidity, temperature, and so on. Lowering the
value simulates a more absorbent medium (more humidity in the air, for example); raising the
value simulates a less absorbent medium (dry desert air, for example).
	 * @param passedValue float [0.892, 1.0], def: 0.994
	 */
	
	public void setAirAbsorptionGainHF(float passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_AIR_ABSORPTION_GAINHF){
			addEffectf(EFX10.AL_REVERB_AIR_ABSORPTION_GAINHF, EFX10.AL_REVERB_MIN_AIR_ABSORPTION_GAINHF);
		} else if (passedValue > EFX10.AL_REVERB_MAX_AIR_ABSORPTION_GAINHF){
			addEffectf(EFX10.AL_REVERB_AIR_ABSORPTION_GAINHF, EFX10.AL_REVERB_MAX_AIR_ABSORPTION_GAINHF);
		} else {
			addEffectf(EFX10.AL_REVERB_AIR_ABSORPTION_GAINHF, passedValue);
		} 
	}
	
	/**
	 * When this flag is set, the high-frequency decay time automatically stays below a limit value that’s
derived from the setting of the property Air Absorption HF. This limit applies regardless of the
setting of the property Decay HF Ratio, and the limit doesn’t affect the value of Decay HF Ratio.
This limit, when on, maintains a natural sounding reverberation decay by allowing you to increase
the value of Decay Time without the risk of getting an unnaturally long decay time at high
frequencies. If this flag is set to AL_FALSE, high-frequency decay time isn’t automatically limited.
	 * @param passedValue float [AL_FALSE, AL_TRUE], def: AL_TRUE
	 */
	
	public void setDecayHFLimit(int passedValue){
		if (passedValue < EFX10.AL_REVERB_MIN_DECAY_HFLIMIT){
			addEffecti(EFX10.AL_REVERB_DECAY_HFLIMIT, EFX10.AL_REVERB_MIN_DECAY_HFLIMIT);
		} else if (passedValue > EFX10.AL_REVERB_MAX_DECAY_HFLIMIT){
			addEffecti(EFX10.AL_REVERB_DECAY_HFLIMIT, EFX10.AL_REVERB_MAX_DECAY_HFLIMIT);
		} else {
			addEffecti(EFX10.AL_REVERB_DECAY_HFLIMIT, passedValue);
		} 
	}
	
	public float getDensity(){
		return getEffectf(EFX10.AL_REVERB_DENSITY);
	}
	
	public float getDiffusion(){
		return getEffectf(EFX10.AL_REVERB_DIFFUSION);
	}
	
	public float getGain(){
		return getEffectf(EFX10.AL_REVERB_GAIN);
	}
	
	public float getGainHF(){
		return getEffectf(EFX10.AL_REVERB_GAINHF);
	}
	
	public float getDecayTime(){
		return getEffectf(EFX10.AL_REVERB_DECAY_TIME);
	}
	
	public float getDecayHFRation(){
		return getEffectf(EFX10.AL_REVERB_DECAY_HFRATIO);
	}
	
	public float getReflectionsGain(){
		return getEffectf(EFX10.AL_REVERB_REFLECTIONS_GAIN);
	}
	
	public float getReflectionsDelay(){
		return getEffectf(EFX10.AL_REVERB_REFLECTIONS_DELAY);
	}
	
	public float getLateReverbGain(){
		return getEffectf(EFX10.AL_REVERB_LATE_REVERB_GAIN);
	}
	
	public float getLateReverbDelay(){
		return getEffectf(EFX10.AL_REVERB_LATE_REVERB_DELAY);
	}
	
	public float getAirAbsorptionGainHF(){
		return getEffectf(EFX10.AL_REVERB_AIR_ABSORPTION_GAINHF);
	}
	
	public float getRoomRolloffFactor(){
		return getEffectf(EFX10.AL_REVERB_ROOM_ROLLOFF_FACTOR);
	}
	
	public int getDecayHFLimit(){
		return getEffecti(EFX10.AL_REVERB_DECAY_HFLIMIT);
	}
}