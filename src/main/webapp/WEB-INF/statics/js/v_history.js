/**
 * Created by xx on 2017/4/10.
 */
var islogin = false;
var ispaused = false;
quicksd=5;
slowsd=5;
quick=1;
slow=1;
/*$(document).ready(function () {
 var obj = document.getElementById("TestMfcAtl");
 if( !islogin )
 obj.Init();
 var list = obj.GetDeviceList("");
 var num = list.split("\n").length;
 var tokens = list.split("\n");
 if( tokens.length > 0 )
 devList = [];
 $("#devList").empty();
 for(var i in tokens ){
 if (i==parseInt(num)-1){
 break;
 }
 var device = tokens[i];
 var device_sip = device.split("\t")[0];
 var device_name = device.split("\t")[1];
 var opt = document.createElement("option");
 opt.setAttribute("value", device_sip);
 opt.innerHTML = device_name;
 devList[device_sip] = device_name;
 document.getElementById("devList").appendChild(opt);
 };
 document.getElementById("devList").select(0);
 })*/
function PrefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}
function GetDate(timeStr){
    return timeStr.substr(0, 4) + "-" + timeStr.substr(4, 2) + "-" + timeStr.substr(6, 2);
}
function GetTime(timeStr){
    return timeStr.substr(8, 2) + ":" + timeStr.substr(10, 2) + ":" + timeStr.substr(12, 2);
}
$(function () {
    fun_init();
    var obj = document.getElementById("TestMfcAtl");
    obj.ChangeScreenNum(1);
    $("#searchbtn").click( function searchVodList(){
        var obj = document.getElementById("TestMfcAtl");
        if( !islogin ){
            obj.Init();
            islogin = true;
        }

        document.getElementById("progress-current").innerHTML = "00:00";
        document.getElementById("progress-total").innerHTML = "99:99";
        var szRefDevice = $("#devList").find('option:selected').val();
        var tStartTime1 = $("#tStartTime").val();
        var  tStartTime=tStartTime1.substr(0,4)+tStartTime1.substr(5,2)+tStartTime1.substr(8,2)+tStartTime1.substr(11,2)+tStartTime1.substr(14,2)+tStartTime1.substr(17,2);
        var tEndTime1 = $("#tEndTime").val();
        var  tEndTime=tEndTime1.substr(0,4)+tEndTime1.substr(5,2)+tEndTime1.substr(8,2)+tEndTime1.substr(11,2)+tEndTime1.substr(14,2)+tEndTime1.substr(17,2);
        var recordList = obj.GetRecordList(szRefDevice, tStartTime, tEndTime);
        document.getElementById("vodList").innerHTML = "";
        var tokens = recordList.split("\n");
        var vodId = 1;
        for( var j in tokens ){
            var items = tokens[j].split("\t");
            if( items.length != 4 )
                continue;
            var vodName = items[0];
            var vodSize = items[1];
            var vodStart = items[2];
            var vodEnd = items[3];

            var tr = document.createElement("tr");
            tr.setAttribute("tStart", vodStart);
            tr.setAttribute("tEnd", vodEnd);

            var vid = document.createElement("td"); vid.innerHTML = PrefixInteger(vodId, 3);
            tr.appendChild(vid);

            var vname = document.createElement("td"); vname.innerHTML = vodName.substr(0, 8) + "..";
            tr.appendChild(vname);

            var vstart = document.createElement("td"); vstart.innerHTML = GetDate(vodStart) + "<br/>" + GetTime(vodStart);
            tr.appendChild(vstart);

            var vend = document.createElement("td"); vend.innerHTML = GetDate(vodEnd) + "<br/>" + GetTime(vodEnd);
            tr.appendChild(vend);
            // + "<td>" + ( vodSize / 1024 ).toString() + "M</td>"
            vodId += 1;
            document.getElementById("vodList").appendChild(tr);
        }
    });
    $("#scr1-table").on("click","tr",function () {
        var obj = document.getElementById("TestMfcAtl");
        if( !islogin ) {
            obj.Init();
            islogin = true;
        }
        var szRefDevice = $("#devList").find('option:selected').val();
        var tStartTime = $(this).attr("tStart");
        var tEndTime = $(this).attr("tEnd");
        $("#pause").html("暂停");
        var d=null;
        // obj.PlayVod(szRefDevice, tStartTime, tEndTime);
        d=obj.PlayVod(szRefDevice, tStartTime, tEndTime);
        //alert(d);
        document.getElementById("progress-current").innerHTML = "00:00";
        document.getElementById("progress-total").innerHTML = "00:00";
    });
    $("#off").click(function (){
        var obj = document.getElementById("TestMfcAtl");
        if( !islogin ) {
            obj.Init();
            islogin = true;
        }
        obj.StopVod();
        $("#pause").html("暂停");

    });
    $("#pause").click(function (){
        var obj = document.getElementById("TestMfcAtl");
        if( !islogin ) {
            obj.Init();
            islogin = true;
        }
        if( !ispaused ) {
            $("#pause").html("播放");
            obj.ControlVod(3, 3);
            ispaused = true;
        }else {
            $("#pause").html("暂停");
            obj.ControlVod(4, 4);
            ispaused = false;
        }
    });
    $("#Btn_quick").click(function (){
        var obj = document.getElementById("TestMfcAtl");
        if( !islogin ) {
            obj.Init();
            islogin = true;
        }
        if(quicksd==9){
            quick=1;
        }
        slowsd=5;
        slow=1;
        if(quicksd<9){
            quicksd=quicksd+1;
            //quick=0;
            quick=quick*2;
        }
        // alert(quicksd)
        obj.ControlVod(7, quicksd);
        // obj.ControlVod(5, 5);
       $("#Btn_quick").text("快进X"+quick);
       $("#slow").text("慢放");
    });
    $("#slow").click(function (){
        var obj = document.getElementById("TestMfcAtl");
        if( !islogin ) {
            obj.Init();
            islogin = true;
        }
        if(slowsd==1){
            slow=1;
        }
        quicksd=5;
        quick=1;
        if(slowsd>1){
            slowsd=slowsd-1;
            slow=slow*2;
        }
        // alert(slowsd)
        // obj.ControlVod(6, 6);
       obj.ControlVod(7, slowsd);
       $("#slow").text("慢放X"+slow);
        $("#Btn_quick").text("快进");
    });
    $("#end").click(function (){
        var obj = document.getElementById("TestMfcAtl");
        if( !islogin ) {
            obj.Init();
            islogin = true;
        }
        quicksd=5;
        quick=1;
        slowsd=5;
        slow=1;
        $("#slow").text("慢放");
        $("#Btn_quick").text("快进");
        obj.ControlVod(7, 5);
    });
    $("#restart").click(function (){
        var obj = document.getElementById("TestMfcAtl");
        if( !islogin ) {
            obj.Init();
            islogin = true;
        }
        var tt = obj.ControlVod(16, 16);
        $("#slow").text("慢放");
        $("#Btn_quick").text("快进");
    });


});
// //回车键查询
// $(document).ready(function(e) {
//     $(this).keydown(function (e){
//         if(e.which == "13"){
//             var focusActId = document.activeElement.id;
//             if(focusActId == 'findname'){
//                 $("#findbtn").click();
//             }
//         }
//     })
// });//回车键查询
//初始化 参数
function fun_init(){
    var xjmc=$("#Hid_xjmc").val();
    var xjbh=$("#Hid_xjbh").val();
    if(xjbh!="null" && xjbh!=null && xjbh!="" && xjmc!=""){
        $("#findname").val(xjmc);
        $("#devList").append("<option value='"+xjbh+"'>"+xjmc+"</option>");
    }
    else{
        // fun_loadselect();
    }
}
//加载下拉框内容
function fun_loadselect(){
    var cphm=$("#findname").val();
    var pJson={cphm:cphm};
    var $devList=$("#devList");
    $devList.empty();
    $.ajax({
        url:"/data/tpfx/dtll"
        ,type:"post"
        ,contentType:"application/json"
        ,dataType:"json"
        ,data:JSON.stringify(pJson)
        ,beforeSend:function(){
            $devList.append("<option name='_nodata' value=''>检索中</option>");
        }
        ,success:function(jData){
            if(jData.status=="0"){
                if(jData.data.length>0){
                    var optionhtmlF='<option JSONALL value="{xjbz}">{xjmc}</option>';
                    $devList.empty();
                    var arr_html=[];
                    $.each(jData.data,function(i,o){

                        if(i=="0"){
                            optionhtmlF=optionhtmlF.replace("JSONALL", m_fun_jsonkey(o));
                        }
                        var str=m_fun_formatstr(o,optionhtmlF);
                        $devList.append(str);
                    });//$.each(jData.data,function(i,o){
                }// if(jData.data.length>0){
                else{
                    $devList.html("<option name='_nodata' value=''>暂无数据！</option>");
                }
            }//if(jData.status=="0"){
            else {
                $devList.html("<option name='_nodata' value=''>网络错误！</option>");
            }
        }//,success:function(){
        ,error:function (event, XMLHttpRequest, ajaxOptions, thrownError) {
            $devList.html("<option name='_nodata' value=''>检索失败！</option>");
        }
    });//$.ajax({
}

function fun_selectclick(){
    $("#devList option[name='_nodata']").remove();
}