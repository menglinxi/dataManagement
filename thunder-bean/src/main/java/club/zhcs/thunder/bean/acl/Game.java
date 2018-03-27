package club.zhcs.thunder.bean.acl;

import club.zhcs.thunder.bean.ThunderEntity;
import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
 * 
 * @author kerbores@gmail.com
 *
 */
@Table("t_game")
@Comment("游戏表")
public class Game  extends ThunderEntity {


	@Column("idss")
	@Comment("数字类型游戏Id")
	private String Idss;

	@Column("audit_flag")
	private String auditFlag;

	@Column("delete_flag")
	private String deleteFlag;

	@Column("gamename")
	@Comment("游戏名")
	private String gameName;

	@Column("create_date")
	@Comment("创建时间")
	private Date createDate ;

	@Column("update_date")
	@Comment("更新时间")
	private Date updateDate ;


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

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
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
}