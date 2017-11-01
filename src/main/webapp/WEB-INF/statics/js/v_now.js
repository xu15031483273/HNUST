/**
 * Created by xx on 2017/4/10.
 */
$(function(){
    //回车键
    // $(this).keydown(
    //     function (e) {
    //         if(e.which=="13"){
    //             if(document.activeElement.id=="device_input"){
    //                 $("#jiansuo").click();
    //             }
    //         }
    //     }
    // )

    $("#allfaci").scroll(function() {
        var scrollTop = $(this).scrollTop();
        var scrollHeight = $(this)[0].scrollHeight;
        if(scrollHeight-scrollTop<400){
            fun_GetDeviceList();
        }
    });
    // 提交任务
    function fun_committask(rwmc,sip){
        if(sip==null||sip==""){
            alert("提示：请先选择视频！")
            return;
        }
        var pJson={rwmc:rwmc,xjbz:sip};
        $.ajax({
            url:"/data/tpfx/savevideo"
            ,type:"post"
            ,contentType:"application/json"
            ,dataType:"json"
            ,data:JSON.stringify(pJson)
            ,beforeSend:function(){}
            ,success:function(jData){
                if(jData.status=="0"){
                    alert("提交完成！");
                }
            }//,success:function(){
        });//$.ajax({
    }//function fun_committask(taskname ,sip){

    $("#w_fangan").window({
        onOpen:function(){
            $("#video-4").hide();
            $(".ctrl4-bottom").hide();
        },onClose:function(){
            $("#video-4").show();
            $(".ctrl4-bottom").show();
        }
    });
    $("#v_spxl").click(function () {
        if(sip==null){
             sip="";
        }
        $.ajax({
            url:"/data/video/camera_xjbz"
            ,type:"post"
            ,contentType:"application/json"
            ,dataType:"json"
            ,data:JSON.stringify({v:sip})
            ,success:function(jData){
                if(jData.status==0){
                    var trhtmlf='<tr ALLKEY><td style="width: 300px">{xjmc}</td><td ><div class="m-btn-title-div js_div_add">添加</div></td></tr>';
                     var arr_html=[];
                     $.each(jData.data,function(i,o){
                     if(i=="0"){
                     trhtmlf=trhtmlf.replace("ALLKEY", m_fun_jsonkey(o));
                     }
                     var str=m_fun_formatstr(o,trhtmlf);
                     arr_html.push(str);
                     })//$.each(jData.data,function(i,o){
                     var $tbody=$("#Tbody01");
                     $tbody.empty();
                     $tbody.append(arr_html.join(''));
                     $("#w_fangan").attr("title","编辑方案");
                     $("#w_fangan").window("open");
                     t_fun_theadwidth($tbody.parent());
                }
            },complete:function(){
            }
        })//$.ajax({
    });
    $("#button_select").click(function () {
        var button_select_name=$("#yylbjs").val();
        $.ajax({
            url:"/data/video/camera_xjmc"
            ,type:"post"
            ,contentType:"application/json"
            ,dataType:"json"
            ,data:JSON.stringify({v:button_select_name})
            ,success:function(jData){
                if(jData.status==0){
                    var trhtmlf='<tr ALLKEY style="height: 35px;"><td style="height: 35px;width: 229px;">{xjmc}</td><td><div class="m-btn-title-div js_div_add">添加</div></td></tr>';
                    var arr_html=[];
                    $.each(jData.data,function(i,o){
                        if(i=="0"){
                            trhtmlf=trhtmlf.replace("ALLKEY", m_fun_jsonkey(o));
                        }
                        var str=m_fun_formatstr(o,trhtmlf);
                        arr_html.push(str);
                    })//$.each(jData.data,function(i,o){
                    var $tbody=$("#Tbody01");
                    $tbody.empty();
                    $tbody.append(arr_html.join(''));
                    $("#w_fangan").attr("title","编辑方案");
                    $("#w_fangan").window("open");
                    t_fun_theadwidth($tbody.parent());
                }
            },complete:function(){
            }
        })//$.ajax({
    });
    $("#Select_FA").change(function(){
        fun_loadXjList();

    });
    //方案中相机列表移除 2到1
    $("body").on("click",".js_div_del",function(){
        var $tr=$(this).parents("tr");
        var newtr='<tr _xjbz="{xjbz}" _xjmc="{xjmc}"><td style="width: 300px">{xjmc}</td><td><div class="m-btn-title-div js_div_add">添加</div></td></tr>';
        var oo={};
        oo.xjbz=$tr.attr("_xjbz");
        oo.xjmc=$tr.attr("_xjmc");
        var trhtml=m_fun_formatstr(oo,newtr);
        $(this).parents("tr").remove();
        $("#Tbody01").prepend(trhtml);
        t_fun_theadwidth($("#Tbody02").parent());
        t_fun_theadwidth($("#Tbody01").parent());

    })
    //方案中相机列表添加 1到2
    $("body").on("click",".js_div_add",function(){
        var $tr=$(this).parents("tr");
        // var newtr='<tr _xjbz="{xjbz}" _xjmc="{xjmc}"><td>{xjmc}</td><td><input type="text" class="m_input_text" name="sc" value="{sc}" placeholder="请输入时长" /></td><td><div class="m-btn-title-div js_div_del">移除</div></td></tr>'
        // var oo={};
        var newtr='<tr _xjbz="{xjbz}" _xjmc="{xjmc}">' +
            '<td style="width:280px;" title="{xjmc}">{xjmc}</td>' +
            '<td style="width:50px;"><input style="width: 50px" type="text" class="m_input_text" name="sc" value="{sc}" placeholder="请输入时长" /></td><td ><div class="m-btn-title-div js_div_del">移除</div></td></tr>'
        var oo={};
        oo.xjbz=$tr.attr("_xjbz");
        oo.xjmc=$tr.attr("_xjmc");
        oo.sc=30;
        var trhtml=m_fun_formatstr(oo,newtr);
        $(this).parents("tr").remove();
        $("#Tbody02").prepend(trhtml);
        t_fun_theadwidth($("#Tbody02").parent());
        t_fun_theadwidth($("#Tbody01").parent());

    })

    $("body").on("click","#v_qp",function(){
        var imgsrc=sip;
        var url="/showvideo?radnum="+Math.random()+"isdjjsdioosd0d03kddsdfsf23233232f&imgsrc="+imgsrc;
        window.open(url,'newwindow','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight+100)+ ',top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
        // window.open(url, 'newwindow', 'height=680, width=1380, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
    })

    //加载巡逻方案
    fun_loadFangAn();
});
function fullScreen() {

    var el = document.documentElement;

    var rfs = el.requestFullScreen || el.webkitRequestFullScreen ||

        el.mozRequestFullScreen || el.msRequestFullScreen;

    if(typeof rfs != "undefined" && rfs) {

        rfs.call(el);

    } else if(typeof window.ActiveXObject != "undefined") {

        //for IE，这里其实就是模拟了按下键盘的F11，使浏览器全屏

        var wscript = new ActiveXObject("WScript.Shell");

        if(wscript != null) {

            wscript.SendKeys("{F11}");

        }

    }
}

function fun_init(){
    var xjmc=$("#Hid_xjmc").val();
    var xjbh=$("#Hid_xjbh").val();
    if(xjbh!="null"&&xjbh!=null&&xjbh!=""&&xjmc!=""){
        var obj = document.getElementById("TestMfcAtl");
        var sip = xjbh;
        if( !islogin ) {
            obj.Init();
            islogin = true;
        }
        obj.PlayReal(sip);
        $("#device_input").val(xjmc);
        fun_loadtree();
    }
    else{
        // fun_loadselect();
    }



}
function fun_loadFangAn(){
    var pJson={v:"n"};
    var $select =$("#Select_FA");
    $.ajax({
        url:"/data/video/fangan"
        ,type:"post"
        ,contentType:"application/json"
        ,dataType:"json"
        ,data:JSON.stringify(pJson)
        ,error:function(){
            alet("网路不稳定，请稍后重试")
        }
        ,success:function(jData){
            if(jData.status=="0"){

                $select.empty();
                var optionf="<option value='{faid}'>{famc}</option>";
                var arr_option=[];
                $.each(jData.data,function(i,o){
                    var str=m_fun_formatstr(o,optionf);
                    arr_option.push(str);
                });
                //数组添加元素 最后位置push(), 数组前边位置unshift()
                arr_option.unshift("<option value='0'></option>");
                $select.append(arr_option.join(''));
            }
        }
    });//$.ajax({
} //function loadFangAn(){
//加载相机列表
function fun_loadXjList(){
    var faid =$("#Select_FA").val();
    if(faid=="")return ;
    var pJson={faid:faid};
    var $tbody=$("#Tbody02");
    $tbody.empty();
    $.ajax({
        url:"/data/video/glist"
        ,type:"post"
        ,contentType:"application/json"
        ,dataType:"json"
        ,data:JSON.stringify(pJson)
        ,success:function(jData){
            if(jData.status=="0"){
                $tbody.empty();
                var trhtmlf='<tr _xjbz="{xjbz}" _xjmc="{xjmc}">' +
                    '<td style="width:280px;" title="{xjmc}">{xjmc}</td>' +
                    '<td style="width:50px;"><input style="width: 50px" type="text" class="m_input_text" name="sc" value="{sc}" placeholder="请输入时长" /></td><td s><div class="m-btn-title-div js_div_del">移除</div></td></tr>'
                var arr_html=[];
                $.each(jData.data,function(i,o){
                    o.xh=i+1;
                    if(i==0){
                        trhtmlf=trhtmlf.replace("JSONALL", m_fun_jsonkey(o));
                    }
                    var str=m_fun_formatstr(o,trhtmlf);
                    arr_html.push(str);
                });
                $tbody.append(arr_html.join(''));
                t_fun_theadwidth($tbody.parent());
            }
        }
    });//$.ajax({
}
// 保存方案
function fun_save(){
    var famc=$("#Select_FA option:selected").text();

    var faid=$("#Select_FA").val();
    if(faid==0){alert("请选择方案名称"); return ;}
    var pJson={faid:faid,famc:famc,list:[]}
    $("#Tbody02 tr").each(function(){
        var pjj={};
        pjj.xjbz=$(this).attr("_xjbz");
        pjj.xjmc=$(this).attr("_xjmc");
        pjj.sc=$(this).find("input[name='sc']").val();
        pJson.list.push(pjj);
    })
    $.ajax({
        url:"/data/video/savefang"
        ,type:"post"
        ,contentType:"application/json"
        ,dataType:"json"
        ,data:JSON.stringify(pJson)
        ,success:function(jData){
            if(jData.status==0){
                fun_loadFangAn();
                $("#w_fangan").window("close");
            }
        }
        ,complete:function(){
            $("#w_fangan").window("close");
        }
    });//$.ajax({

}//function fun_save(){

function add_save(){
    var famc_add=$("#tp21").val();
    var pJson={faid:"",famc:famc_add,list:[]}
    $.ajax({
        url:"/data/video/savefang"
        ,type:"post"
        ,contentType:"application/json"
        ,dataType:"json"
        ,data:JSON.stringify(pJson)
        ,success:function(jData){
            if(jData.status==0){
                fun_loadFangAn();
                $("#add_fangan").window("close");
                fun_loadXjList();
            }
        }
        ,complete:function(){
            $("#add_fangan").window("close");
            fun_loadXjList();
        }
    });//$.ajax({

}//function fun_save(){
// 取消保存方案
function fun_faclose(){
    $("#w_fangan").window("close");
}
function add_faclose(){
    $("#add_fangan").window("close");
}


function fun_full(){

    var element1=document.getElementById("video-4");
    var element2=document.getElementById("TestMfcAtl");
    $("#video-4").empty();
    $("#TestMfcAtl").empty();
    $("#TestMfcAtl").remove();
    $("#ctrl4-right").toggle();
    $(".ctrl4-bottom").toggle();
    requestFullScreen(element2);/*
     $("#video-4").style.width=100;
     $("#video-4").style.height=100;*/
    var w=$(window.parent.document).width();
    var h=$(window.parent.document).height();

      $("#content4-1").find("div").each(function(){
          $(this).hide();
      })
    $("#video-4").show();
    // document.getElementById("video-4").style.width=100;
    // document.getElementById("#TestMfcAtl").style.width=100;
    $("#video-4").css({
        "width":w+"px"
        ,"height":h+"px"
        ,"position":"fixed"
        ,"background-color":"blue"
    });
    // $("#TestMfcAtl").css({
    //     "width":w+"px"
    //     ,"height":h+"px"
    //     // ,"position":"fixed"
    //     // ,"background-color":"red"
    // });
    $("#video-4> *").css({
        "width":w+"px"
        ,"height":h+"px"
        ,"position":"fixed"
        ,"background-color":"blue"
    });
    $("#video-4").html("<OBJECT ID=\"TestMfcAtl\"  width=\"100%\" height=\"90%\" classid=\"CLSID:0FE79807-755E-4402-B25F-9ADBC6423C63\" codebase=\"http://13.1.3.9:8090/ocx/CCFPlayerOcx.exe\" ></OBJECT>");



    // var obj = document.getElementById("TestMfcAtl");
    // if( !islogin )
    //     obj.Init();
    // obj.ChangeScreenNum(4);

}



