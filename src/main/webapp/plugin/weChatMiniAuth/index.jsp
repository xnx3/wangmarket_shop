<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="/wm/common/head.jsp">
    <jsp:param name="title" value="编辑"/>
</jsp:include>

        <div class="layui-btn-container" style="margin-top: 50px;margin-left: 50px">
            <button type="button" class="layui-btn" id="test3"><i class="layui-icon">&#xe681;</i>上传文件</button>
        </div>
<table class="layui-table iw_table">
    <thead>
    <tr>
        <th style="text-align: center;">已上传的的文件</th>
    </tr>
    </thead>
    <tbody id="app">
   <!--  <tr >
            <td style="width:58px;">en</td>
        </tr>
    -->
    </tbody>
</table>

<script type="application/javascript">
    $(function (){
        wm.post('/plugin/api/weChatMiniAuth/store/list.json', {},function(data){
            console.log('接口返回值:');
            console.log(data);
            var list = data.list
            for (let i = 0; i < list.length; i++) {
                $("#app").append("<tr > <td >" + list[i].path + "</td> </tr>")
            }
        });
    });
</script>
    <script>
        //自适应弹出层大小
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        //parent.layer.iframeAuto(index);
        layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#test3'
                ,accept: 'file'
                ,url: '/plugin/api/weChatMiniAuth/store/upload.do'
                // elem: '.uploadImg' //绑定元素
                ,size: 5  ////限制文件大小，单位 KB
                // ,field : 'file'
                ,exts : 'txt'
                ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    msg.loading('上传中...');
                }
                ,done: function(res){
                    iw.loadClose();
                    if(res.result == '1'){
                        parent.iw.msgSuccess("上传成功");
                        parent.layer.close(index);	//关闭当前窗口
                        location.reload();	//刷新父窗口列表
                    }else if(res.result == '0'){
                        parent.iw.msgFailure(res.info);
                    }else{
                        parent.iw.msgFailure("上传失败");
                    }
                }
                ,error: function(){
                    parent.iw.msgFailure("上传失败");
                }
            });
        });
    </script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>