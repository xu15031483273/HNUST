/**
 * Created by Administrator on 2017/3/16.
 */

//退出登录
function fun_exit(){
    $(".div_radio1").hide();
    $.messager.defaults={ok:"确定",cancel:"取消",width:"300"};
    $.messager.confirm("退出确认", "是否退出平台，重新登录？", function(r){
        if (r){
            window.location.href="/login";
        }
        else{
            $(".div_radio1").show();
        }
    });
    $(".panel-tool-close").css("display","none");

}
//实时态势
function fun_ssts(obj){
    $(".div_center_top_middle_menuon").removeClass("div_center_top_middle_menuon");
    $("#IfrShow1").attr("src","/ssts");
    $(obj).addClass("div_center_top_middle_menuon");
}//实时态势

// 实时监控
function fun_ssjk(obj){
    $(".div_center_top_middle_menuon").removeClass("div_center_top_middle_menuon");
    $("#IfrShow1").attr("src","/hebei/now");
    $(obj).addClass("div_center_top_middle_menuon");
}// 实时监控

// 历史调阅
function fun_lsdy(obj){
    $(".div_center_top_middle_menuon").removeClass("div_center_top_middle_menuon");
    $("#IfrShow1").attr("src","/hebei/history");
    $(obj).addClass("div_center_top_middle_menuon");
}
// 视频巡逻
function fun_spxl(obj){
    $(".div_center_top_middle_menuon").removeClass("div_center_top_middle_menuon");
    $("#IfrShow1").attr("src","/spxl");
    $(obj).addClass("div_center_top_middle_menuon");
}
// 图像分析
function fun_txfx(obj){
    $(".div_center_top_middle_menuon").removeClass("div_center_top_middle_menuon");
    $("#IfrShow1").attr("src","/txfx");
    $(obj).addClass("div_center_top_middle_menuon");
}

// 设备管理
function fun_sbgl(obj){
    $(".div_center_top_middle_menuon").removeClass("div_center_top_middle_menuon");
    $("#IfrShow1").attr("src","/sbgl");
    $(obj).addClass("div_center_top_middle_menuon");
}
// 地图浏览
function fun_dtll(obj){
    //alert(123);
    $(".div_center_top_middle_menuon").removeClass("div_center_top_middle_menuon");
    $("#IfrShow1").attr("src","/dtll");
    $(obj).addClass("div_center_top_middle_menuon");
}

//q全屏
function fun_fullscreen(){
    $(".div_cent_top").toggle();
    $(".div_cent_bottom").css("height","100%");
}