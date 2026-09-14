// ---------- Target interface (what client expects) ----------
interface MediaPlayer {
    void play(String audioType, String fileName);
}

// ---------- Adaptee (incompatible interface, e.g. third-party lib) ----------
interface AdvancedMediaPlayer {
    void playVlc(String fileName);
    void playMp4(String fileName);
}

class VlcPlayer implements AdvancedMediaPlayer {
    @Override public void playVlc(String fileName) {
        System.out.println("Playing vlc file: " + fileName);
    }
    @Override public void playMp4(String fileName) {
        // this player doesn't support mp4, so do nothing
    }
}

class Mp4Player implements AdvancedMediaPlayer {
    @Override public void playVlc(String fileName) {
        // this player doesn't support vlc, so do nothing
    }
    @Override public void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}

// ---------- Adapter ----------
class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedPlayer;

    MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedPlayer = new VlcPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedPlayer.playVlc(fileName);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedPlayer.playMp4(fileName);
        }
    }
}

// ---------- Client-facing class ----------
class AudioPlayer implements MediaPlayer {
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            // natively supported, no adapter needed
            System.out.println("Playing mp3 file: " + fileName);
        } else if (audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")) {
            // delegate to adapter for unsupported formats
            MediaAdapter adapter = new MediaAdapter(audioType);
            adapter.play(audioType, fileName);
        } else {
            System.out.println("Invalid media type: " + audioType + " not supported");
        }
    }
}

// ---------- Client ----------
public class AdapterDesignPattern {
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();

        player.play("mp3", "song.mp3");   // Playing mp3 file: song.mp3
        player.play("vlc", "movie.vlc");  // Playing vlc file: movie.vlc
        player.play("mp4", "clip.mp4");   // Playing mp4 file: clip.mp4
        player.play("avi", "video.avi");  // Invalid media type: avi not supported
    }
}