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
@Table("t_context")
@Comment("游戏日流水数据")
public class Context extends ThunderEntity {


	@Column("ids")
	@Comment("旧数据Id保留用作对照")
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



	@Column("add_number")
	private String addNumber;
	@Column("add_percent")
	private String addPercent;
	@Column("channel_name")
	private String channelName;
	@Column("clientarppu")
	private String clientarppu;
	@Column("client_percent")
	private String clientPercent;
	@Column("excel_version")
	private String excelVersion;

	@Column("game_name")
	private String gameName;
	@Column("game_id")
	private long gameId;
	@Column("keep2")
	private String keep2;
	@Column("keep7")
	private String keep7;
	@Column("pay_money")
	private String payMoney;
	@Column("pay_percent")
	private String payPercent;
	@Column("import_time")
	private Date importTime;
	@Column("looktime")
	private String looktime;

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

	public String getAddNumber() {
		return addNumber;
	}

	public void setAddNumber(String addNumber) {
		this.addNumber = addNumber;
	}

	public String getAddPercent() {
		return addPercent;
	}

	public void setAddPercent(String addPercent) {
		this.addPercent = addPercent;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getClientarppu() {
		return clientarppu;
	}

	public void setClientarppu(String clientarppu) {
		this.clientarppu = clientarppu;
	}

	public String getClientPercent() {
		return clientPercent;
	}

	public void setClientPercent(String clientPercent) {
		this.clientPercent = clientPercent;
	}

	public String getExcelVersion() {
		return excelVersion;
	}

	public void setExcelVersion(String excelVersion) {
		this.excelVersion = excelVersion;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public String getKeep2() {
		return keep2;
	}

	public void setKeep2(String keep2) {
		this.keep2 = keep2;
	}

	public String getKeep7() {
		return keep7;
	}

	public void setKeep7(String keep7) {
		this.keep7 = keep7;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayPercent() {
		return payPercent;
	}

	public void setPayPercent(String payPercent) {
		this.payPercent = payPercent;
	}


	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public String getLooktime() {
		return looktime;
	}

	public void setLooktime(String looktime) {
		this.looktime = looktime;
	}
}