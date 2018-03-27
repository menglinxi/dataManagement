package club.zhcs.thunder.controller.acl;

/**
 * @author zhangliang
 * @Date 2017/12/29
 */
public class InfoC {

    private Long channelId;
    private String channelName;
    private boolean channelStatus;
    private Long channelAndGameId;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public boolean isChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(boolean channelStatus) {
        this.channelStatus = channelStatus;
    }

    public Long getChannelAndGameId() {
        return channelAndGameId;
    }

    public void setChannelAndGameId(Long channelAndGameId) {
        this.channelAndGameId = channelAndGameId;
    }
}
