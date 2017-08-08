package zlk.com.rxbuslearn.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;

import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/8/8 0008.
 * this is a xxx for
 */

public class AudioActivity extends AppCompatActivity {

    private SoundPool mSound;
    private HashMap<Integer, Integer> soundPoolMap;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        // 设置播放音效
        mPlayer = MediaPlayer.create(this, R.raw.sound_jh);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.release();
            }
        });
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mPlayer.release();
                return false;
            }
        });
//        float volume = getVolume();
//        mPlayer.setVolume(volume, volume);

        // 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        mSound = new SoundPool(4, AudioManager.STREAM_SYSTEM, 100);
        soundPoolMap = new HashMap<>();
        soundPoolMap.put(1, mSound.load(this, R.raw.sound_jh, 1));
        //可以在后面继续put音效文件

        findViewById(R.id.bt_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1, 0);
            }
        });
        findViewById(R.id.bt_play1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPlayer.isPlaying()) {
                    mPlayer.seekTo(0);
                    mPlayer.start();
                }
            }
        });

    }

    /**
     * soundPool播放
     *
     * @param sound 播放第一个
     * @param loop  是否循环
     */
    private void playSound(int sound, int loop) {
        float volume = getVolume();

        // 第一个参数是声效ID,第二个是左声道音量，第三个是右声道音量，第四个是流的优先级，最低为0
        // ，第五个是是否循环播放（0 = no loop, -1 = loop forever），第六个播放速度(1.0 =正常播放,范围0.5 - 2.0)
        mSound.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
    }

    private float getVolume() {
        AudioManager mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        // 获取系统声音的当前音量
        float currentVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        // 获取系统声音的最大音量
        float maxVolume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 获取当前音量的百分比
//        float volume = currentVolume / maxVolume;
        return currentVolume / maxVolume;
    }

    @Override
    protected void onDestroy() {
        if (mSound != null) {
            mSound.release();
            mSound = null;
        }
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        super.onDestroy();
    }
}
