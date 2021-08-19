package com.xnx3.wangmarket.plugin.rootTxtUpload.vo;

import com.xnx3.BaseVO;
import com.xnx3.j2ee.util.AttachmentMode.bean.SubFileBean;

import java.util.List;

/**
 * @author xuehao
 * 查看路径下文件vo
 */
public class Upfilevo extends BaseVO {

    public List<SubFileBean> list;

    public List<SubFileBean> getList() {
        return list;
    }

    public void setList(List<SubFileBean> list) {
        this.list = list;
    }

}
