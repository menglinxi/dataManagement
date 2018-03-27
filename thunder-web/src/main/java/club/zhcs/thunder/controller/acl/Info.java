package club.zhcs.thunder.controller.acl;

import java.util.List;

/**
 * @author zhangliang
 * @Date 2017/12/29
 */
public class Info {


    private Long gameId;
    private String gameName;
    private boolean gameStatus;
    private List<InfoC> channels;
    private String[] realGames;

    public String[] getRealGames() {
        return realGames;
    }

    public void setRealGames(String[] realGames) {
        this.realGames = realGames;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<InfoC> getChannels() {
        return channels;
    }

    public void setChannels(List<InfoC> channels) {
        this.channels = channels;
    }
}
