package com.mapscience.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 奖惩表
 * </p>
 *
 * @author ${author}
 * @since 2019-03-11
 */
@TableName("t_rewards_penalties")
public class RewardsPenalties extends Model<RewardsPenalties> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String award;
    @TableField("award_time")
    private Date awardTime;
    private Integer categroy;
    @TableField("create_time")
    private Date createTime;
    private String memberid;
    @TableField("party_id")
    private String partyId;
    private String remark;
    private Integer type;
    @TableField("update_time")
    private Date updateTime;
    @TableField("member_id")
    private String memberId;
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public Integer getCategroy() {
        return categroy;
    }

    public void setCategroy(Integer categroy) {
        this.categroy = categroy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RewardsPenalties{" +
        "id=" + id +
        ", award=" + award +
        ", awardTime=" + awardTime +
        ", categroy=" + categroy +
        ", createTime=" + createTime +
        ", memberid=" + memberid +
        ", partyId=" + partyId +
        ", remark=" + remark +
        ", type=" + type +
        ", updateTime=" + updateTime +
        ", memberId=" + memberId +
        ", status=" + status +
        "}";
    }
}
