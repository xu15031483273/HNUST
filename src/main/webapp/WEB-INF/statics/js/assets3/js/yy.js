var url = 'http://13.1.3.9:6080/arcgis/rest/services/ZKJA/MapServer';
$(function(){
    $("body").on("click",".marker_total",function(e){
        var $tr=$(this);

        var data=eval("("+($(this).attr("data"))+")");
        content2=  '<table border="0" style="width:250px; " id="table_weather"  >'
            +'<tr><td style="width:100px">城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市： </td></td><td>'+data.glbmmc+'</td></tr>'
            +'<tr><td>温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td><td>'+data.wd+'℃</td></tr>'
            +'<tr><td>天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气：</td><td>'+data.wdesc+'</td></tr>'
            +'<tr><td>风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td><td>'+data.fx+'</td></tr>'
            +'<tr><td>风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td><td>'+data.fs+'kmph</td></tr>'
            +'<tr><td>湿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td><td>'+data.sd+'%</td></tr>'
            +'<tr><td>能&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;见&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td><td>'+data.njd+'km</td></tr>'
            +'<tr><td>降&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;水&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td><td>'+data.jsl+'mm</td></tr>'
            +'<tr><td>空气质量指数：</td><td>'+data.aqi+'</td></tr>'
            +'<tr><td>PM2.5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</td><td>'+data.pm25+'ug/m³</td></tr>'
            +'<tr><td>PM10&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</td><td>'+data.pm10+'ug/m³</td></tr>'
            +'<tr><td>更&nbsp;&nbsp;新&nbsp;&nbsp;时&nbsp;&nbsp;间&nbsp;：</td><td>'+data.bgsj+'</td></tr>'
        '</td></tr>'
        +'</table>' ;
        $tr.popover({
            'placement': 'top auto',
            'animation': true,
            'html': true,
            'content':content2
        });
        $tr.popover('show');
    });
    layers = [
        new ol.layer.Tile({
            projection: 'EPSG:4326',
            // extent: [113, 37, 119, 42],
            //extent: [60, 5, 150, 50],
            source: new ol.source.TileArcGISRest({
                url: url
            })
        }),

    ];
    map = new ol.Map({
        layers: layers,
        target: 'map',
        view: new ol.View({
            projection: 'EPSG:4326',
            center: [115.221000,38.206000],
            zoom: 7,
            minZoom:7,
            maxZoom:20
        }),
        controls: ol.control.defaults().extend([
            new ol.control.FullScreen(),
            // new ol.control.MousePosition({}),
            new ol.control.ZoomSlider({  }),
            new ol.control.ScaleLine({  }),
            new ol.control.OverviewMap({})
        ]),
    });

    // fun_loadWeather1of4(map);
    loadVideoLayer(map);
    fun_loadRoadEvent(map);
    loadWetherLayer(map);
    var pos = [116.33, 40.0];

    var popup = new ol.Overlay({
        // element: document.getElementById('popup')
        element:$(".c_popup").first()[0]
    });
    map.addOverlay(popup);

    map.on('click', function(evt) {
        var element = document.getElementById('popup');
        var coordinate = evt.coordinate;
        var feature = map.forEachFeatureAtPixel(evt.pixel,
            function(feature) {
                return feature;
            });
        if (feature) {
            var data=feature.get("data");
            var dType=feature.get("dType");
            var title="基本信息";
            if(dType=="weather"){
                title="天气信息";
                content1=  '<table border="0" style="width:250px">'
                    +'<tr><td style="width:100px">城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市： </td></td><td>'+data.glbmmc+'</td></tr>'
                    +'<tr><td>温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td><td>'+data.wd+'℃</td></tr>'
                    +'<tr><td>天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气：</td><td>'+data.wdesc+'</td></tr>'
                    +'<tr><td>风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td><td>'+data.fx+'</td></tr>'
                    +'<tr><td>风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td><td>'+data.fs+'</td></tr>'
                    +'<tr><td>湿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td><td>'+data.sd+'</td></tr>'
                    +'<tr><td>能&nbsp;&nbsp;&nbsp;见&nbsp;&nbsp;&nbsp;度：</td><td>'+data.njd+'</td></tr>'
                    +'<tr><td>降&nbsp;&nbsp;&nbsp;水&nbsp;&nbsp;&nbsp;量：</td><td>'+data.jsl+'</td></tr>'
                    +'<tr><td>空气质量指数：</td><td>'+data.aqi+'</td></tr>'
                    +'<tr><td>PM25：</td><td>'+data.pm25+'</td></tr>'
                    +'<tr><td>PM10：</td><td>'+data.pm10+'</td></tr>'
                    +'<tr><td>更新时间：</td><td>'+data.bgsj+'</td></tr>'
                '</td></tr>'
                +'</table>' ;
            }
            else if(dType=="event"){
                title="事件信息";
                var tokens=data.split("\t");
                content1=  '<table border="0" style="width:230px">'
                    +'<tr><td style="width:85px">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点： </td></td><td>'+tokens[1]+'</td></tr>'
                    +'<tr><td>事件描述：</td><td>'+tokens[7]+'</td></tr>'
                    +'<tr><td>原&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因：</td><td>'+tokens[6]+'</td></tr>'
                    +'<tr><td>风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td><td>'+tokens[16]+'</td></tr>'
                    +'<tr><td>风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td><td>'+tokens[14]+'</td></tr>'
                    +'<tr><td>经&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td><td>'+tokens[2]+'</td></tr>'
                    +'<tr><td>纬&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度： </td><td>'+tokens[3]+'</td></tr>'
                '</td></tr>'
                +'</table>' ;
            }
            else{ //
                //   hredf1="http://13.1.2.170:8200/showv?sipname="+encodeURI(data.name)+"&sip="+data.sip+"&678";
                content1=  '<table border="0" style="width:230px">'
                    +'<tr><td style="width:40px">城市: </td></td><td>'+data.city+'</td></tr>'
                    +'<tr><td>  名 称: </td><td>'+data.name+'</td></tr>'
                    +'<tr><td>  地 点: </td><td>'+data.address+'</td></tr>'
                    +'<tr><td>  道 路: </td><td>'+data.road+'</td></tr>'
                    +'<tr><td>  经 度: </td><td>'+data.localtion[0]+'</td></tr>'
                    +'<tr><td>  纬 度: </td><td>'+data.localtion[1]+'</td></tr>'
                    +'<tr><td  colspan="2">' +
                    '<div style="float:left;margin-left: 10px;"><a href="/hebei/now?xjmc='+encodeURI(data.name)+'&xjbh='+data.sip+'&678" >实时视频</a></div>' +
                    '<div style="float:left;margin-left: 10px;"><a href="/hebei/history?xjmc='+encodeURI(data.name)+'&xjbh='+data.sip+'&678" >回放视频</a></div>' +
                    '<div style="float:left;margin-left: 10px;" onclick="fun_committask(\''+data.name+'【'+data.sip+'】'+'\',\''+data.sip+'\')" ><a href="#"  >提交分析</a></div>' +
                    '</td></tr>'
                    +'</table>' ;
            }//显示摄像机信息

            $("#popup").remove();
            var $element=$('<div id="popup" class="c_popup" title="'+title+'"></div>');
            $("#DIV_hide").append($element);
            popup.setElement($element[0]);
            popup.setPosition(coordinate);

            $element.popover({
                'placement': 'top auto',
                'animation': true,
                'html': true,
                'content': content1
            });
            $element.popover('show');
        }else{
            $(element).popover('destroy');
        }
    });
});
//回车键查询
$(document).ready(function(e) {
    $(this).keydown(function (e){
        if(e.which == "13"){
            var focusActId = document.activeElement.id;
            if(focusActId == 'fun_chaxun_input'){
                $("#fun_chaxun").click();
            }
        }
    })
});//回车键查询
var zdJson={
    fxjg:{"0":"分析成功", "1":"图片下载失败","2":"未检测到车辆","3":"算法分析失败","4":"分析内部错误","99":"未知错误"}
    ,cpys:{"1":"蓝色","2":"黑色","3":"黄色","4":"白色","5":"绿色","6":"其他"}
    ,cbdllb:{"1":"奥迪","2":"大众","3":"宝马","4":"本田","5":"别克","6":"奔驰","7":"现代","8":"福特","9":"起亚","10":"马自达","11":"日产","12":"标致","13":"丰田","14":"雪佛兰","15":"雪铁龙","16":"斯柯达","17":"沃尔沃","18":"雷克萨斯","19":"雷诺","20":"三菱","21":"比亚迪","22":"路虎","23":"凯迪拉克","24":"斯巴鲁","25":"克莱斯勒","26":"皇冠","27":"菲亚特","28":"英菲尼迪","29":"迷你","30":"捷豹","31":"预留","32":"宾利","33":"吉普","34":"莲花","35":"欧宝","36":"玛莎拉蒂","37":"保时捷","38":"荣威","39":"铃木","40":"名爵","41":"奇瑞","42":"帝豪","43":"哈飞","44":"东南","45":"宝骏","46":"奔腾","47":"昌河","48":"大发","49":"东风","50":"福迪","51":"预留","52":"传祺","53":"海马","54":"中华","55":"汇众","56":"吉利","57":"江淮","58":"江铃","59":"金杯","60":"开瑞","61":"理念","62":"力帆","63":"南京","64":"启辰","65":"威麟","66":"五十铃","67":"夏利","68":"羊城","69":"一汽","70":"依维柯","71":"跃进","72":"长城","73":"五菱","74":"中兴","75":"讴歌","76":"北汽制造","77":"大宇","78":"吉奥","79":"华长城泰","80":"陆风","81":"瑞麒","82":"曙光（黄海）","83":"双环","84":"双龙","85":"华普","86":"众泰","87":"长安","88":"道奇","89":"哈弗","90":"纳智捷","91":"精灵","92":"福田","93":"中顺","94":"劳斯莱斯","95":"长丰","96":"广汽","97":"金旅","98":"预留","99":"林肯","100":"五征","101":"解放","102":"预留","103":"日野","104":"凯马","105":"中国重汽","106":"东风柳州","107":"北奔重卡","108":"陕汽重卡","109":"华菱","110":"预留","111":"红岩","112":"王牌","113":"曼","114":"日产柴","115":"徐工","116":"华德","117":"海格","118":"宇通","119":"青年","120":"江淮汽车","121":"上海申通","122":"中通","123":"亚星客车","124":"金龙","125":"三一","126":"南骏","127":"十通","128":"斯堪尼亚","129":"安凯中巴","130":"牡丹花都","131":"蜀都公交","132":"中巴车","133":"春兰","134":"预留","135":"飞驰","136":"联合","137":"神野","138":"唐骏","139":"轻骑微车","140":"黄海","141":"瑞沃","142":"野马","143":"新凯","144":"永源","145":"时风","146":"楚风","147":"三环","148":"百路佳","149":"北方尼奥普兰","150":"迪马","1000":"其他"}
    ,cxcflb:{"0":"未知","1":"大型客车","2":"大型货车","3":"中型客车","4":"小型客车","5":"小型货车"}
    ,cxxflb:{"0":"未知","101":"大型客车的其他子类型","201":"集装箱货车","202":"油罐车","203":"卡车","204":"吊车","205":"拖车"}
    ,csys:{"1":"白色","2":"银色","3":"黑色","4":"红色","5":"紫色","6":"蓝色","7":"黄色","8":"绿色","9":"褐色","10":"粉红色","11":"灰色","12":"其他"}
    ,cpjg:{"1":"单排","2":"双排","3":"其他"}
    ,zjsfdsj:{"1":"有","2":"未知","0":"没有"}
    ,zjsfjaqd:{"1":"有","2":"未知","0":"没有"}
    ,zjsffxzyb:{"1":"有","2":"未知","0":"没有"}
    ,fjsfjaqd:{"1":"有","2":"未知","0":"没有"}
    ,fjsffxzyb:{"1":"有","2":"未知","0":"没有"}
    ,rwzt:{"1":"分析数据加载中","2":"任务分析中","0":"任务分析失败","3":"任务分析完成"}
    ,rwsbcwm:{"0":"正确","1":"目录遍历失败","2":"目录没有图片数据","3":"数据库操作失败","99":"内部错误"}

}
var fxjgArr;
//加载列表
function loadList(){
    var pJson={};
    $(".p_c").each(function(){
        pJson[$(this).attr("name")]=$(this).val();
        // alert($(this).attr("name"));
        // alert($(this).val());

    });
    // alert(JSON.stringify(pJson));
    var $Tbody=$("#Tbody_Listku");
    $Tbody.empty();
    $.ajax({
        url:"/data/tpfx/dtll"
        ,type:"post"
        ,contentType:"application/json"
        ,dataType:"json"
        ,data:JSON.stringify(pJson)
        ,beforeSend:function(){}
        ,success:function(jData){
            if(jData.status=="0"){
                var trhtmlf='<tr JSONALL ><td class="js_tdfxjgxx" ><a id="a" _xjbz="{xjbz}" onclick="alertsd(this)">{xjmc}</a></td></tr>';
                var arr_html=[];
                $.each(jData.data,function(i,o){
                    o.fxjgstr=zdJson["fxjg"][o.fxjg];
                    if(i=="0"){
                        fxjgArr=o;
                        trhtmlf=trhtmlf.replace("JSONALL", m_fun_jsonkey(o));
                    }
                    var str=m_fun_formatstr(o,trhtmlf);
                    arr_html.push(str);
                });//$.each(jData.data,function(i,o){
                $("#w_fxjg").window("open");
                $Tbody.append(arr_html.join(''));
                t_fun_theadwidth($Tbody.parent());

            }
        }//,success:function(){
        ,error:function (event, XMLHttpRequest, ajaxOptions, thrownError) {
            alert("查询错误");
        }
    });//$.ajax({
}
// 查询按钮
function fun_chaxun(){
    loadList();
}
function alertsd(obj) {
    var xjbz1=$(obj).attr("_xjbz");
    $.ajax({
        type: "GET",
        // url: "http://13.1.2.170:8200/assets3/demo/video-demo.json",
        url: "/assets3/demo/list.json",
        dataType: "json",
        success: function(data){
            var obj = eval(data);
            var city_x;
            var city_y;
            for( var i = 0; i <obj.data.length; i++ ) {
                if(xjbz1==obj.data[i].sip) {
                    city_x = obj.data[i].localtion[0];
                    city_y = obj.data[i].localtion[1];
                }
            }
            map.getView().getZoom(8);
            var viewCenter=ol.proj.fromLonLat([city_x,city_y],'EPSG:4326','EPSG:900916')
            map.getView().setCenter(viewCenter);
            addIcon(xjbz1,viewCenter);

        }
    });

}
function addIcon(xjbz1,coord){
    $.ajax({
        type: "GET",
        url: "/assets3/demo/list.json",
        dataType: "json",
        success: function(data){
            var obj = eval(data);
            var features=[];
            for( var i = 0; i <obj.data.length; i++ ){
                if(xjbz1==obj.data[i].sip){
                    var city_x = obj.data[i].localtion[0];
                    var city_y = obj.data[i].localtion[1];
                    var img="/assets3/icon/ipc/rifile1.png";
                    var loc_geom = [city_x, city_y];
                    var iconFeature = new ol.Feature({
                        geometry: new ol.geom.Point(loc_geom),
                        data:obj.data[i]
                    });
                    var iconStyle = new ol.style.Style({
                        image: new ol.style.Icon({
                            anchor:[0.5,0.5],
                            imgSize:[100,100],
                            scale:1.5,
                            anchorXUnits:'fraction',
                            anchorYUnits:'fraction',
                            src:img
                        })
                    });
                    iconFeature.setStyle(iconStyle);
                    features.push(iconFeature);
                }

            }//for

            var vectorSource = new ol.source.Vector({
                features: features
            });
            var  vectorLayer = new ol.layer.Vector({
                source:vectorSource
            });
            var groublayers=map.getLayers();
            if (groublayers.length > 0) {
                for (var i = 0; i < groublayers.length; i++) {
                    map.removeLayer(groublayers[i]);}}
            map.addLayer(vectorLayer);
        }
    });
}
//-------------------------------------------------------------------------------------------- XP add
// 提交任务
function fun_committask(rwmc,sip){
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
//添加天气 1
function fun_loadWeather1of4(imap){
    $.ajax({
        type: "GET",
        url: "assets/data/condition-code.txt",
        dataType: "txt",
        error:function(e){
            if(e.readyState==4){
                fun_loadWeather2of4(e.responseText,imap);
            }
        }
        ,success: function(data){
            fun_loadWeather2of4(data,imap)
        }
    });
}
//加载天气 2
function fun_loadWeather2of4(data,imap){
    var records = data.split("$");
    for( var i = 0 ; i < records.length; i++ ){
        var tokens = records[i].split("\t");
        weather_meta[tokens[0]] = new Array();
        weather_meta[tokens[0]]["name"] = tokens[1];
        var featureStyle = new ol.style.Style({
            image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                anchor: [0.5, 0.5],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                opacity: 0.75,
                src: "assets/icon/weather/" + tokens[3].split("/")[4]
                //	src:  tokens[3]
            }))
        });
        weather_meta[tokens[0]]["style"] = featureStyle;

    }
    fun_loadWeather3of4(imap);
}
//加载天气 3
function fun_loadWeather3of4(map){
    $.ajax({
        type: "GET",
        url: "assets/data/2017051611.txt",
        dataType: "txt",
        error:function(e){
            if(e.readyState==4){
                fun_loadWeather4of4(e.responseText,map);
            }
        }
        ,success: function(data){
            fun_loadWeather4of4(data,map);
        }
    });
}
//加载天气 4
function fun_loadWeather4of4(data,map){
    var records = data.split("$");
    var features=[];
    for( var i = 0; i < records.length; i++ ){
        if( records[i].length < 10 )
            continue;
        var tokens = records[i].split("\t");
        var city_x = parseFloat(tokens[2]);
        var city_y = parseFloat(tokens[3]);
        var img="assets/icon/weather/"+tokens[5]+".png";
        // var loc_geom = ol.proj.transform([city_x,city_y],"EPSG:4326","EPSG:900913");
        var loc_geom = [city_x, city_y];
        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(loc_geom),
            data:records[i]
            ,dType:"weather"
        });
        var iconStyle = new ol.style.Style({
            image: new ol.style.Icon({
                anchor:[0.5,0.5],
                imgSize:[100,100],
                scale:1.0,
                anchorXUnits:'fraction',
                anchorYUnits:'fraction',
                src:img
            })
        });
        iconFeature.setStyle(iconStyle);
        features.push(iconFeature);

    }//for

    var vectorSource = new ol.source.Vector({
        features:features
    });
    vectorLayer_weather = new ol.layer.Vector({
        source:vectorSource
    });
    map.addLayer(vectorLayer_weather);
}
//关闭 或显示天气
function fun_toggleWeather(obj){
    var $t=$(obj);
    var t=false;
    if($t.val()=="打开天气情况"){
        $t.val("关闭天气情况");
        t=true;

        loadWetherLayer(map);
    }
    else{
        $t.val("打开天气情况");
        fun_close();
    }

  //  vectorLayer_weather.setVisible(t);

}
//施工管制
function fun_toggleEvent(obj){
    var $t=$(obj);
    var t=false;
    if($t.val()=="打开事件信息"){
        $t.val("关闭事件信息");
        t=true;
    }
    else{
        $t.val("打开事件信息");
    }
    map.addLayer(vectorLayerSj);
    vectorLayerSj.setVisible(t);
}
//施工管制
function fun_toggleVideo(obj){
    var $t=$(obj);
    var t=false;
    if($t.val()=="打开视频监控"){
        $t.val("关闭视频监控");
        t=true;
    }
    else{
        $t.val("打开视频监控");
    }
    vectorLayer.setVisible(t);
}
function fun_loadRoadEvent(map){
    $.ajax({
        type: "GET",
        url: "assets/data/2016122407.txt",

        success: function(data){
            var records = data.split("$");
            var features=[];
            for( var i = 0; i < records.length; i++ ){
                if( records[i].length < 10 )
                    continue;
                var tokens = records[i].split("\t");
                var city_x = parseFloat(tokens[2]);
                var city_y = parseFloat(tokens[3]);
                var img="assets/icon/marker/"+tokens[5]+".png";

                var loc_geom = [city_x, city_y];

                var iconFeature = new ol.Feature({
                    geometry: new ol.geom.Point(loc_geom),
                    data:records[i]
                    ,dType:"event"

                });
                var iconStyle = new ol.style.Style({
                    image: new ol.style.Icon({
                        anchor:[0.5,0.5],
                        imgSize:[32,32],
                        scale:1,
                        anchorXUnits:'fraction',
                        anchorYUnits:'fraction',
                        src:img
                    })
                });

                iconFeature.setStyle(iconStyle);
                features.push(iconFeature);
                /* var vectorSource = new ol.source.Vector({
                 features:[iconFeature]
                 });
                 var vectorLayer = new ol.layer.Vector({
                 source:vectorSource
                 });*/

            }//for

            var vectorSource = new ol.source.Vector({
                features:features
            });
            vectorLayerSj = new ol.layer.Vector({
                source:vectorSource
                //setVisible:false
            });
            //  map.addLayer(vectorLayerSj);

        },
        error:function(ex){
            if(ex.readyState=="4"){
                fun_addeventlayer(map,eval("("+ex.responseText+")"));

            }
        }
    });
}// loadenvet

function fun_addeventlayer(map,data){
    var records = data.split("$");
    var features=[];
    for( var i = 0; i < records.length; i++ ){
        if( records[i].length < 10 )
            continue;
        var tokens = records[i].split("\t");
        var city_x = parseFloat(tokens[2]);
        var city_y = parseFloat(tokens[3]);
        var img="assets/icon/marker/"+tokens[5]+".png";
        var loc_geom = ol.proj.transform([city_x,city_y],"EPSG:4326","EPSG:900913");
        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(loc_geom),
            data:records[i]
            ,dType:"event"

        });
        var iconStyle = new ol.style.Style({
            image: new ol.style.Icon({
                anchor:[0.5,0.5],
                imgSize:[32,32],
                scale:1,
                anchorXUnits:'fraction',
                anchorYUnits:'fraction',
                src:img
            })
        });
        iconFeature.setStyle(iconStyle);
        features.push(iconFeature);
        /* var vectorSource = new ol.source.Vector({
         features:[iconFeature]
         });
         var vectorLayer = new ol.layer.Vector({
         source:vectorSource
         });*/

    }//for

    var vectorSource = new ol.source.Vector({
        features:features
    });
    vectorLayerSj = new ol.layer.Vector({
        source:vectorSource
    });
    map.addLayer(vectorLayerSj);
}

