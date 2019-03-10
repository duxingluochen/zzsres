package com.mapscience.modular.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mapscience.modular.system.edit.CommonUtils;
import com.mapscience.modular.system.edit.FormField;

/**
 * <p>
 * 自定义表单
 * </p>
 *
 * @author WCF
 * @since 2019-01-25
 */
@TableName("t_custom_form")
public class CustomForm extends Model<CustomForm> {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义表单Id
     */
    @TableId("form_id")
    protected String formId;
    /**
     * 解析获取的属性字符串
     */
    protected String dataJson;
    /**
     * 字段个数
     */
    protected Integer fields;
    
    /**
     * 商户Id
     */
    protected String merchantId;
    /**
     * 解析过的代码
     */
    protected String html;
    /**
     * 表单名
     */
    @TableField("form_name")
    protected String formName;
    /**
     * 没有解析的代码
     */
    @NotEmpty
    protected String template;
    /**
     * 状态 1是启用 2是禁用
     */
    protected Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    protected Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    protected Date updateTime;
    /**
     * 说明
     */
    protected String remark;


	public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Integer getFields() {
        return fields;
    }

    public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public void setFields(Integer fields) {
        this.fields = fields;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.formId;
    }

    public static CustomForm praseTemplate(String template) {
    	CustomForm customForm = new CustomForm();
		template = template.replace("<p>	</p>", "");
		String preg =  "(\\|-<span(((?!<span).)*leipiplugins=\"(radios|checkboxs|select)\".*?)>(.*?)<\\/span>-\\||<(img|input|textarea|select).*?(<\\/select>|<\\/textarea>|\\/>))";
		String _html = template.replace("{|-", "").replace("-|}", "");
		String _parse = _html ;
		List<FormField> fs = new ArrayList<>();
		String[] ms = CommonUtils.maches(template, preg);
		for(String m : ms) {
			FormField ff = FormField.parseText(m);
			if(ObjectUtils.isEmpty(ff)){
				return customForm;
			}
			fs.add(ff);
			if("listctrl".equals(ff.getLeipiplugins()) 
					|| "select".equals(ff.getLeipiplugins()) || "datepicker".equals(ff.getLeipiplugins())
					|| "radios".equals(ff.getLeipiplugins()) || "checkboxs".equals(ff.getLeipiplugins()) 
					|| "listctrls".equals(ff.getLeipiplugins()) ) {
				String content = ff.getEdit(ff.getValue());
				_html = _html.replace(ff.getContent(), content);
			}
			if("text".equals(ff.getLeipiplugins())) {
				String content = ff.getEdit(ff.getValue());
				_html = _html.replace(ff.getContent(), content);
			}
			if("textuper".equals(ff.getLeipiplugins())) {
				String content = ff.getEdit(ff.getValue());
				_html = _html.replace(ff.getContent(), content);
			}
			String _f_parse = ff.getView(ff.getValue());
			if(_f_parse != null)
				_parse = _parse.replace(ff.getContent(), _f_parse);
		}
		customForm.setDataJson(JSON.toJSONString(fs));
		customForm.setFields(fs.size());
		customForm.setHtml(_parse);
		customForm.setTemplate(template);
		int frist = _html.indexOf("<table");
		int last = _html.lastIndexOf("</table");
		_html=_html.substring(frist, last+8);
		customForm.setHtml(_html);
		return customForm ;
	}
    
    @Override
    public String toString() {
        return "CustomForm{" +
        "formId=" + formId +
        ", dataJson=" + dataJson +
        ", fields=" + fields +
        ", html=" + html +
        ", formName=" + formName +
        ", template=" + template +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
