
$(function(){

    $("input[tabindex='1']").focus();
    $(".div_cent_center_login_input").blur(function(){
        $(this).val($(this).val().replace(/\s/g,""))
    });
    $("#Btn_Login").click(function () {
        var ucode = $("#Input_UserCode").val();
        var upwd = $("#Input_UserPwd").val();
        if (ucode == "") {
            $("#Input_UserCode").focus();
            alert("请输入登录编号！");
            return;
        }
        if (upwd == "") {
            $("#Input_UserPwd").focus();
            alert("请输入登录密码！");
            return;
        }
        var upwd_md5 = $.md5(upwd);
        $("#userCode").val(ucode);
        $("#userPwd").val(upwd_md5);
        $.ajax({
            type:"post"
            ,url:"login_validate"
            ,dataType:"json"
            ,contentType:"application/json"
            ,data:JSON.stringify({uname:ucode,upwd:upwd_md5})
            ,beforeSend:function(){
                $(".div_center_login_btn").attr("disabled",false);
            }
            ,success:function(data){
                if(data.error=="0"){

                    window.location.href="/index";
/*                    var iHeight=500;
                    var iWidth=1100;
                    //获得窗口的垂直位置
                    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                    //获得窗口的水平位置
                    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                    window.open('/main', 'newwindow', 'top='+iTop+',left='+iLeft+',height='+iHeight+', width='+iWidth+'， toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no')
                    window.close();*/
                }
                else{
                    alert("登录失败，请检查您输入的信息是否正确！" );
                }
            }
        });
    });//$("#btn_login").click(function(){
    $("#Btn_Reset").click(function(){
        $("#Input_UserCode").val("");
        $("#Input_UserPwd").val("");
    });//$("#Btn_Reset").click(function(){
    $("body").keydown(function(){
        if(window.event.keyCode == 13){
            $("#Btn_Login").click();
        }
    });
});//$(function(){
