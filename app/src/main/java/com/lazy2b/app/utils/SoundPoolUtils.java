/**
 * 
 */
package com.lazy2b.app.utils;

import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * @项目名:LHBD_
 * @包 名:com.royaleu.ckbd.utils
 * @文件名:SoundPoolUtils.java
 * @创 建:2016-1-14下午2:52:22
 * @Copyright 2016, GDQL All Rights Reserved.
 */
public class SoundPoolUtils {
	/**
	 * @类名: SoundPoolUtils
	 * @描述: TODO.
	 * @功能: TODO.
	 * @author hw
	 */
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> map = null;

	public static void init() {
		try {
			soundPool = new SoundPool(10, AudioManager.STREAM_ALARM, 5);
			map = new HashMap<Integer, Integer>();
		} catch (Exception e) {
		}
	}

	public static void load(Context context, int rawId) {
		try {
			if (map.get(rawId) == null) {
				int soundId = soundPool.load(context, rawId, 1);
				map.put(rawId, soundId);
			}
		} catch (Exception e) {
		}
	}

	public static void playAlways(Context context, int rawId) {
		try {
			if (map.get(rawId) != null) {
				soundPool.play(map.get(rawId), 1, 1, 0, -1, 1);
			}
		} catch (Exception e) {
		}
	}

	public static void play(Context context, int rawId) {
		try {
			if (map.get(rawId) != null) {
				soundPool.play(map.get(rawId), 1, 1, 0, 0, 1);
			}
		} catch (Exception e) {
		}
	}

	public static void stop() {
		// soundPool.stop
	}

	public static void relese() {
		try {
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				soundPool.unload(map.get(key));
			}
			map.clear();
		} catch (Exception e) {
		}
	}
}
