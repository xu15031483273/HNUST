/**
 * Created by xx on 2016/11/30.
 * 表格的点击样式，鼠标经过样式 滚动条标题宽度
 */
$(function(){
// 标题行不让选择
    $(".m-tableJsSelected,.m-tableJsHover,.m-tableJsAll,.m-table").on("selectstart","thead tr",function(){
        return false;
    });
// 鼠标经过
    $(".m-tableJsHover,.m-tableJsAll").on("mouseover","tbody tr",function(){
        $(this).find("td").css("border-bottom","1px solid #46d8ae");
        $(this).find("td:first").css("border-left","1px solid #46d8ae");
        $(this).find("td:last").css("border-right","1px solid #46d8ae");
        $(this).prev().find("td").css("border-bottom","1px solid #46d8ae");
        if($(this).prev().length==0){
            $(this).find("td").css("border-top","1px solid #46d8ae");
        }
    });
//鼠标离开
    $(".m-tableJsHover,.m-tableJsAll").on("mouseout","tbody tr",function(){
        $(this).find("td").css("border-color","#e5e5eb");
        $(this).prev().find("td").css("border-color","#e5e5eb");
    });
    //单机选中
    $(".m-tableJsSelected,.m-tableJsAll").on("click","tbody tr",function(){
        var $t=$(this);
        var $oldselected=$(".m_js_tr_selectedON:first");
        $oldselected.css( "color","");
        $oldselected.css({"background-color":""})
        if($oldselected.index() % 2==1){
            $oldselected.css({"background-color":"#fcfcfc"})
        }
        $oldselected.removeClass("m_js_tr_selectedON");
        $t.toggleClass("m_js_tr_selectedON");
        $t.css({"background-color":"#7be3c5","color":"white"})
    });
//多出的文字放到title
    $(".m-tableJsSelected,.m-tableJsHover,.m-tableJsAll,.m-table").on("mouseover","tbody tr td",function(){
        var $t=$(this);
        var html=$t.html();
        if($t.children().length==0){
            $t.attr("title",html);
        }
    });
});

// 计算tbody出现滚动条后的宽度 ；计算 每个单元格添加1px边框后的宽度
function fun_mTalbelWidthCalc(){
   $(".m-tableJsHover,.m-tableJsSelected,.m-tableJsAll,.m-table").each(function(){
       t_fun_theadwidth(this);
   });
}


$(".m-table,.m-tableJsSelected,.m-tableJsAll").ready(function(){
     fun_mTalbelWidthCalc();
});

// 计算每行的高度尽量占满整个TBODY
function fun_tbodytrheight(obj){
    var $t=$(obj);
    debugger;
    var trheight=Math.floor($t.height()/$t.find("tr").length)-1; //高度剪掉一个边框的高度
    tbodyTrMinHeight=20;
    tbodyTrMaxHeight=50;
    if(tbodyTrMaxHeight<trheight){
        trheight=tbodyTrMaxHeight
    }
    if(tbodyTrMinHeight>trheight){
        trheight=tbodyTrMinHeight;
    }
    $t.find("tr").css("height",trheight+"px");

}

/*-----公用参数-------*/
// 公用参数 tbody 出现纵向滚动条后，重新计算thead的高度。
function t_fun_theadwidth(table){
    $(table).find("thead").width($(table).find("tbody tr:first").width());
}

// 固定的TABLE高度 去掉THEAD高度，剩下的给TBODY 主要兼容IE
function t_fun_tbodyheight(tbody,num){
    if(num==null || num==undefined) num=0;
    var $table=tbody.parent();
    var h =$table.height()-$table.find("thead").height()-num;
    tbody.css("height",h);
}