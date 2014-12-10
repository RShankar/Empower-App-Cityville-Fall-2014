
package com.ade.cityville;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
	 
	private static SoundManager _instance;
	private static SoundPool mSoundPool;
	private static HashMap<Integer, Integer> mSoundPoolMap;
	private static AudioManager  mAudioManager;
	private static Context mContext;
	private static HashMap<Integer, Integer> mSoundPoolStreamMap;
	public static boolean muted = false;

 
	private SoundManager()
	{
	}
 
	/**
	 * Requests the instance of the Sound Manager and creates it
	 * if it does not exist.
	 *
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public SoundManager getInstance(Context theContext)
	{
	    if(_instance == null){
	      _instance = new SoundManager();
	      	// ljw: i moved these into the get instance to further steamline the implimentation
			mContext = theContext;
		    mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		    mSoundPoolMap = new HashMap<Integer, Integer>();
		    mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
		    mSoundPoolStreamMap = new HashMap<Integer, Integer>();

			mSoundPoolMap.put(1, mSoundPool.load(mContext, R.raw.button_16, 1));
			mSoundPoolMap.put(2, mSoundPool.load(mContext, R.raw.button_17, 1));
			mSoundPoolMap.put(3, mSoundPool.load(mContext, R.raw.button_20, 1));
			mSoundPoolMap.put(4, mSoundPool.load(mContext, R.raw.button, 1));
			mSoundPoolMap.put(5, mSoundPool.load(mContext, R.raw.button_3, 1));
			mSoundPoolMap.put(6, mSoundPool.load(mContext, R.raw.tiny_button_push, 1));
	    }
	    return _instance;
	 }
	/**
	 * Add a new Sound to the SoundPool
	 *
	 * @param Index - The Sound Index for Retrieval
	 * @param SoundID - The Android ID for the Sound asset.
	 */
	public static void addSound(int Index,int SoundID)
	{
		mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, 1));
	}
	/**
	 * Plays a Sound
	 *
	 * @param index - The Index of the Sound to be played
	 * @param speed - The Speed to play not, not currently used but included for compatibility
	 */
	public static void playSound(int index,float speed)
	{
		if(!muted){
		     float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		     streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		     try{
		    	int streamID = mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed);
				mSoundPoolStreamMap.put(index, streamID);
		     }catch(NullPointerException e){
		    	 Log.e("CityVille", "SoundPool could not play sound, null pointer");
		     }
		}
	}
 
	/**
	 * Stop a Sound
	 * @param index - index of the sound to be stopped
	 */
	public static void stopSound(int index)
	{
			mSoundPool.stop(mSoundPoolStreamMap.get(index));
	}
	/**
	 * pause a Sound
	 * @param index - index of the sound to be stopped
	 */
	public static void pauseSound(int index)
	{
			mSoundPool.pause(mSoundPoolStreamMap.get(index));
	}
	/**
	 * resume a Sound
	 * @param index - index of the sound to be stopped
	 */
	public static void resumeSound(int index)
	{
		if(!muted){
			mSoundPool.resume(mSoundPoolStreamMap.get(index));
		}
	}
 
	/**
	 * Deallocates the resources and Instance of SoundManager
	 */
	public static void cleanup()
	{
		mSoundPool.release();
		mSoundPool = null;
	    mSoundPoolMap.clear();
	    mAudioManager.unloadSoundEffects();
	    _instance = null;
 
	}
}
