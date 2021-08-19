<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="/wm/common/head.jsp">
    <jsp:param name="title" value="编辑"/>
</jsp:include>

        <div class="layui-btn-container" style="margin-top: 50px;margin-left: 50px">
            <button type="button" class="layui-btn" id="test3"><i class="layui-icon">&#xe681;</i>上传文件</button>
        </div>

</script>
    <script>
        layui.use('upload', function(){
            var upload = layui.upload;
            var fileName = '';
            //执行实例
            var uploadInst = upload.render({
                elem: '#test3' //绑定元素
                ,accept: 'file'
                ,url: '/plugin/api/rootTxtUpload/store/upload.do'
                ,size: 5  ////限制文件大小，单位 KB
                ,exts : 'txt'
                ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    msg.loading('上传中...');
                }
                ,choose: function(obj){
                    obj.preview(function(index, file, result) {
                        console.log(file.name)
                        fileName = file.name
                    })
                }
                ,done: function(res){
                    msg.close();
                    if(res.result == '1'){
                        // location.reload();	//刷新父窗口列表
                        msg.confirm({
                            text:'文件已上传成功，是否在新窗口中打开？',
                            buttons:{
                                打开:function(){
                                    window.open('/' + fileName)
                                },
                                取消:function(){
                                    msg.close();
                                }
                            }
                        });
                    }else if(res.result == '0'){
                        msg.failure(res.info);
                    }else{
                        msg.failure('上传失败');
                    }
                }
                ,error: function(){
                    msg.close();
                    msg.failure('上传失败');
                }
            });
        });
    </script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>