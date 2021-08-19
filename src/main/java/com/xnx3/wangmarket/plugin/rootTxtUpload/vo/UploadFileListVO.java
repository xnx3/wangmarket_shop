package com.xnx3.wangmarket.plugin.rootTxtUpload.vo;

import com.xnx3.BaseVO;
import com.xnx3.j2ee.util.AttachmentMode.bean.SubFileBean;
import java.util.List;

/**
 * @author xuehao
 * 查看指定路径下文件列表的vo
 */
public class UploadFileListVO extends BaseVO {
    public List<SubFileBean> list;

    public List<SubFileBean> getList() {
        return list;
    }

    public void setList(List<SubFileBean> list) {
        this.list = list;
    }
}
