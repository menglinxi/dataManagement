package club.zhcs.thunder.bean.acl;

import club.zhcs.thunder.bean.ThunderEntity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * 
 * @author kerbores@gmail.com
 *
 */
@Table("t_channel_game")
@Comment("用户被限定的游戏-渠道表")
public class ChannelGame extends ThunderEntity {


	@Column("idss")
	@Comment("数字类型游戏Id")
	private String Idss;

	@Column("audit_flag")
	private String auditFlag;

	@Column("delete_flag")
	private String deleteFlag;


	@Column("create_date")
	@Comment("创建时间")
	private Date createDate ;

	@Column("update_date")
	@Comment("更新时间")
	private Date updateDate ;

	@Column("channel_id")
	private long channelId;

	@Column("game_id")
	private String gameId;

	@Column("user_id")
	private String userId;

	public String getIdss() {
		return Idss;
	}

	public void setIdss(String idss) {
		Idss = idss;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}