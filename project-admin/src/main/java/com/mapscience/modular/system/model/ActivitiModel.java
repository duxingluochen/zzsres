package com.mapscience.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 流程模型图-关联表单
 * </p>
 *
 * @author ${author}
 * @since 2019-03-05
 */
@TableName("t_activiti_model")
public class ActivitiModel extends Model<ActivitiModel> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("activiti_model_id")
    private String activitiModelId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 部署流程的ID
     */
    @TableField("deploy_id")
    private String deployId;
    /**
     * 类别id
     */
    @TableField("type_id")
    private String typeId;
    /**
     * 关联表单Id
     */
    @TableField("form_id")
    private String formId;
    /**
     * 流程模型的ID
     */
    @TableField("model_id")
    private String modelId;
    /**
     * 流程名
     */
    @TableField("activiti_model_name")
    private String activitiModelName;
    /**
     * 关联表单的名称
     */
    @TableField("form_name")
    private String formName;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 公司Id
     */
    @TableField("com_id")
    private String comId;
    /**
     * 公司Id
     */
    private Integer status;


    public String getActivitiModelId() {
        return activitiModelId;
    }

    public void setActivitiModelId(String activitiModelId) {
        this.activitiModelId = activitiModelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getActivitiModelName() {
        return activitiModelName;
    }

    public void setActivitiModelName(String activitiModelName) {
        this.activitiModelName = activitiModelName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.activitiModelId;
    }

    @Override
    public String toString() {
        return "ActivitiModel{" +
        "activitiModelId=" + activitiModelId +
        ", createTime=" + createTime +
        ", deployId=" + deployId +
        ", typeId=" + typeId +
        ", formId=" + formId +
        ", modelId=" + modelId +
        ", activitiModelName=" + activitiModelName +
        ", formName=" + formName +
        ", updateTime=" + updateTime +
        ", comId=" + comId +
        ", status=" + status +
        "}";
    }
}
