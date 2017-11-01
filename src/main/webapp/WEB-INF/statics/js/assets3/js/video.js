
function loadVideoLayer(map){
    $.ajax({
        type: "POST",
        url: "/json/getCamerJson",
        dataType: "json",
        success: function(data){
            var obj = eval(data);
            var features=[];
            for( var i = 0; i <obj.data.length; i++ ){
                var city_x = obj.data[i].localtion[0];
                var city_y = obj.data[i].localtion[1];

                var img="assets3/icon/ipc/rifile.png";
                var loc_geom = [city_x, city_y];
                var iconFeature = new ol.Feature({
                    geometry: new ol.geom.Point(loc_geom),
                    data:obj.data[i]
                    ,dType:"video"

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

            }//for

            var vectorSource = new ol.source.Vector({
                features: features
            });
            vectorLayer = new ol.layer.Vector({
                source:vectorSource
            });
            map.addLayer(vectorLayer);
        }
    });
}
function loadWetherLayer(map){
    $.ajax({
        type: "post",
        url: "/json/weather",
        dataType: "json",
        success: function(backData){
            var data=backData.data;
            var features=[];
            $.each(data,function(i,o){
                var city_x = parseFloat(data[i]["csx"]);
                var city_y = parseFloat(data[i]["csy"]);
                var img="assets/icon/weather/"+o.wcode+".png";
                var loc_geom = [city_x, city_y];
                var iconFeature = new ol.Feature({
                    geometry: new ol.geom.Point(loc_geom),
                    data:o
                    ,dType:"weather"

                });
                var iconStyle = new ol.style.Style({
                    image: new ol.style.Icon({
                        anchor:[1,1],
                        imgSize:[10,10],
                        scale:0.5,
                        anchorXUnits:'fraction',
                        anchorYUnits:'fraction',
                        src:img
                    })
                });
                iconFeature.setStyle(iconStyle);
                features.push(iconFeature);
                var img = document.createElement('img');
                img.setAttribute("id","marker"+i);
                img.setAttribute("data",JSON.stringify(data[i]));
                img.setAttribute("class","marker_total");
                img.setAttribute("display","block");
                img.setAttribute("src","assets/icon/weather2/"+o.wcode+".gif");
                img.setAttribute("height","30px");
                img.setAttribute("width","30px");
                var id_mark="marker"+i;
                $("#map").append(img);

                vectorLayer_weather = new ol.Overlay({
                    position: loc_geom,
                    positioning: 'bottom-center',
                    element: document.getElementById(id_mark),
                    stopEvent: false

                });
                map.addOverlay(vectorLayer_weather);

            });//   $.each(data,function(i,o){
            var vectorSource = new ol.source.Vector({
                features: features
            });
        },error :function(ex){
            alert(ex)
        }
    });
}

function fun_closelayer(){
$(".marker_total ~ div ").remove();
}
function fun_close(){
    $(".marker_total").remove();
}