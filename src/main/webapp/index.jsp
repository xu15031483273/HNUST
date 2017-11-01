<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
    <script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery-3.1.1.min.js'></script>
    <title>测试</title>
</head>

<body>
<div style="float: right;margin-left: 0px;" class="m-btn-title-div" id="select_data">查询</div>
</body>
</html>
<script  type="text/javascript">

    $(document).ready(function(){
        $("body").on("click","#select_data",function() {
            $.ajax({
                type:"get"
                ,url:"/user/localLogin"
                ,dataType:"jsonp"
                ,data:{"ms":"aa"}
                ,success:function(data){
                    alert(JSON.stringify(data));
                }
                ,error:function(data){
                    alert("操作超时，请稍后进行重试！");
                    alert(JSON.stringify(data));
                }
            });
        });


    });

</script>
