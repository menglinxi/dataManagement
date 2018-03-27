package club.zhcs.thunder.bean.acl;

import club.zhcs.thunder.bean.ThunderEntity;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * 
 * @author kerbores@gmail.com
 *
 */
@Table("t_channel")
@Comment("渠道表")
public class Channel extends ThunderEntity {

	@Column("audit_flag")
	@ColDefine(width = 2)
	private String auditFlag;

	@Column("delete_flag")
	@ColDefine(width = 1)
	private String deleteFlag;

	@Column("channel_name")
	@Comment("游戏名")
	private String channelName;

	@Column("create_date")
	@Comment("创建时间")
	private Date createDate ;

	@Column("update_date")
	@Comment("更新时间")
	private Date updateDate ;

	@Column("gameid")
	private String gameId ;
	@Column("userid")
	private String userId ;



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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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