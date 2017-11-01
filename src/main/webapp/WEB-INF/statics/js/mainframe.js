/**
 * Created by xx on 2017/3/20.
 */
$(document).ready(function(){
    $(".m_inputrq_text_js").click(function(){
       laydate();
    });

});//$(document).ready(function(){
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";

    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}
//替换参数
function m_fun_formatstr(dta, tmpl,kk){
    var format = {
        name: function(x) {
            return x
        }
    };
    if(!tmpl){return "";}
    return tmpl.replace(/{(\w+)}/g, function(m1, m2) {
        if (!m2) return "";
        /*return (dta[m2])?dta[m2]:(kk)?kk:m1;*/
        // return (format && format[m2]) ? format[m2](dta[m2]) : dta[m2];
        var jsonData=dta[m2];
        if(jsonData==undefined){
            jsonData=(!kk==undefined)?kk:m1;
        }
        return (format && format[m2]) ? format[m2](dta[m2]) : (dta[m2])?dta[m2]:jsonData;

    });
}

function m_fun_jsonkey(jsonstr){
    var arr_k=[];
    for(k in jsonstr){
        arr_k.push(' _'+k+'=\"{'+k+'}\"');
    }
    return arr_k.join('');
}

function m_alert(msg,fun,widthnum,titlemsg,okstr,tagstr){
    // 自带弹出
    var title=titlemsg?titlemsg:"信息提示";
    var ok=okstr?okstr:"确定";
    var width=widthnum?widthnum:"303";
    var tag=tagstr?tagstr:"info"; //error、question、info、warning
    $.messager.defaults={ok:ok,width:width};
    $.messager.alert(title,msg,tag,fun);
}

function m_confirm(msg,fun,widthnum,titlemsg,okstr,cancelstr) {
    var ok=okstr?okstr:"是";
    var cancel=cancelstr?cancelstr:"否";
    var title=titlemsg?titlemsg:"操作确认";
    var width=widthnum?widthnum:"350";
    $.messager.defaults={ok:ok,cancel:cancel,width:width};
    $.messager.confirm(title, msg, function(r){
        if (fun){
            fun(r);
        }
    });
}