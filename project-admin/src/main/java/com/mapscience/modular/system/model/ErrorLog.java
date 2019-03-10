package com.mapscience.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 接口错误报错
 * </p>
 *
 * @author ${author}
 * @since 2019-02-26
 */
@TableName("oa_error_log")
public class ErrorLog extends Model<ErrorLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @TableId("log_id")
    private String logId;
    /**
     * 系统报的错误
     */
    private String exc;
    /**
     * 报错提标
     */
    private String title;
    /**
     * 报错时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 请求数据
     */
    private String data;

    public ErrorLog() {
		super();
	}
    
	public ErrorLog(String exc, String title, Date createTime, String data) {
		super();
		this.exc = exc;
		this.title = title;
		this.createTime = createTime;
		this.data = data;
	}

	public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getExc() {
        return exc;
    }

    public void setExc(String exc) {
        this.exc = exc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    protected Serializable pkVal() {
        return this.logId;
    }

    @Override
    public String toString() {
        return "ErrorLog{" +
        "logId=" + logId +
        ", exc=" + exc +
        ", title=" + title +
        ", createTime=" + createTime +
        ", data=" + data +
        "}";
    }
}
