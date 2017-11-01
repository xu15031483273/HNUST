//target1
$.ajax({
        url: '/demo-1.json',
        type: 'get',
        dataType: 'json',
        error: function(error){ alert(error)},
        success: function(data) {
            $("#jsontable1").html("");
            var checked="",
                plate = "",
                pass_time = "",
                v_type = "",
                v_model="",
                v_color="",
                v_pic="",
                titlehtml=""
                allhtml = ""

            data.map(function(item) {
                //动态拼接
                checked='<td><input type="checkbox"></td>'
                plate = '<td class="name">'+item.plate+'</td>'
                pass_time='<td class="name">'+item.pass_time+'</td>'
                v_type = '<td class="name">'+item.v_type+'</td>'
                v_model ='<td class="name">'+item.v_model+'</td>'
                v_color ='<td class="name">'+item.v_color+'</td>'
                v_pic ='<td class="name">'+item.v_pic+'</td>'
                titlehtml='<tr> ' +
                    '<th><input type="checkbox">全选</th>'+
                  '<th>车牌号码</th>' +
                  '<th>通过时间</th>'+
                  '<th>车辆类型</th>' +
                  '<th>品牌型号</th>'+
                  '<th>车身颜色</th>'+
                  '<th>车辆图片</th>'+
                '</tr>'
                allhtml += '<tr>'+checked+plate+pass_time+v_type+v_model+v_color+v_pic+'</tr>'
            })
            $("#jsontable1").html(titlehtml+allhtml);

        }
    });

//target2
$.ajax({
        url: '/demo-1.json',
        type: 'get',
        dataType: 'json',
        error: function(error){
           alert(error)
         },
        success: function(data){
            $("#jsontable2-2").html("");

            var checked="",
                plate = "",
                pass_time = "",
                v_type = "",
                v_model="",
                v_color="",
                v_pic="",
                titlehtml=""
                allhtml = "";

            data.map(function(item) {
                //动态拼接
                checked='<td><input type="checkbox"></td>'
                plate = '<td class="name">'+item.plate+'</td>'
                pass_time='<td class="name">'+item.pass_time+'</td>'
                v_type = '<td class="name">'+item.v_type+'</td>'
                v_model ='<td class="name">'+item.v_model+'</td>'
                v_color ='<td class="name">'+item.v_color+'</td>'
                v_pic ='<td class="name">'+item.v_pic+'</td>'
                titlehtml='<tr>'+
                  '<th><input type="checkbox">全选</th>'+
                  '<th>车牌号码</th>'+
                  '<th>通过时间</th>'+
                  '<th>车辆类型</th>'+
                  '<th>品牌型号</th>'+
                  '<th>车身颜色</th>'+
                  '<th>车辆图片</th>'+
                '</tr>'
                allhtml += '<tr>'+checked+plate+pass_time+v_type+v_model+v_color+v_pic+'</tr>'
            })
            $("#jsontable2-1").html(titlehtml+allhtml);
        }
    });
//target3
$.ajax({
        url: '/demo-1.json',
        type: 'get',
        dataType: 'json',
        error: function(error) {alert(error)},
        success: function(data) {
            $("#jsontable3-1").html("");
            $("#jsontable5-1").html("");


             var   plate = "",
                pass_time = "",

                v_model="",

                titlehtml=""
                allhtml = "";

            data.map(function(item) {
                //动态拼接

                plate = '<td class="name">'+item.plate+'</td>'
                pass_time='<td class="name">'+item.pass_time+'</td>'
                v_model = '<td class="name">'+item.v_type+'</td>'


                titlehtml='<tr><th>车牌号</th><th>通过时间</th><th>卡口位置</th></tr>'
                allhtml += '<tr>'+plate+pass_time+v_model+'</tr>'
            })
            $("#jsontable3-1").html(titlehtml+allhtml);
            $("#jsontable5-1").html(titlehtml+allhtml);
        }
    });
    $.ajax({
            url: '/demo-1.json',
            type: 'get',
            dataType: 'json',
            error: function(error){ alert(error)},
            success: function(data) {
                $("#jsontable3-2").html("");
                $("#jsontable4").html("");
                $("#jsontable5-2").html("");


                 var   plate = "",
                    pass_time = "",

                    v_model="",

                    titlehtml=""
                    allhtml = "";

                data.map(function(item) {
                    //动态拼接

                    plate = '<td class="name">'+item.plate+'</td>'
                    pass_time='<td class="name">'+item.pass_time+'</td>'
                    v_model = '<td class="name">'+item.v_type+'</td>'


                    titlehtml='<tr><th>车牌号</th><th>通过时间</th><th>卡口位置</th></tr>'
                    allhtml += '<tr>'+plate+pass_time+v_model+'</tr>'
                })
                $("#jsontable3-2").html(titlehtml+allhtml);
                $("#jsontable4").html(titlehtml+allhtml);
                $("#jsontable5-2").html(titlehtml+allhtml);
            }
        });
// target6-1
    $.ajax({
            url: '/demo-1.json',
            type: 'get',
            dataType: 'json',
            error: function(error){ alert(error)},
            success: function(data) {
                $("#jsontable6-1").html("");

               var  checked="",
                    plate = "",

                    v_type = "",
                    v_model="",
                    v_color="",
                    v_pic="",
                    titlehtml="",
                    allhtml = "";

                data.map(function(item) {
                    //动态拼接
                    checked='<td><input type="checkbox"></td>'
                    plate = '<td class="name">'+item.plate+'</td>'
                    v_type = '<td class="name">'+item.v_type+'</td>'
                    v_model ='<td class="name">'+item.v_model+'</td>'
                    v_color ='<td class="name">'+item.v_color+'</td>'
                    v_pic ='<td class="name">'+item.v_pic+'</td>'
                    titlehtml='<tr>'+
                      '<th><input type="checkbox">全选</th>'+
                      '<th>车牌号码</th>'+
                      '<th>车辆类型</th>'+
                      '<th>品牌型号</th>'+
                      '<th>车身颜色</th>'+
                      '<th>车辆图片</th>'+
                    '</tr>'
                    allhtml += '<tr>'+checked+plate+v_type+v_model+v_color+v_pic+'</tr>'
                });
                $("#jsontable6-1").html(titlehtml+allhtml);
            }
        });


//target6-2

$.ajax({
        url: '/demo-1.json',
        type: 'get',
        dataType: 'json',
        error: function(error) {alert(error);},
        success: function(data) {
            $("#jsontable6-2").html("");

            var checked="",
                plate = "",
                pass_time = "",
                v_type = "",
                v_model="",
                v_color="",
                v_pic="",
                titlehtml=""
                allhtml = "";

            data.map(function(item) {
                //动态拼接
                checked='<td><input type="checkbox"></td>'
                plate = '<td class="name">'+item.plate+'</td>'
                pass_time='<td class="name">'+item.pass_time+'</td>'
                v_type = '<td class="name">'+item.v_type+'</td>'
                v_model ='<td class="name">'+item.v_model+'</td>'
                v_color ='<td class="name">'+item.v_color+'</td>'
                v_pic ='<td class="name">'+item.v_pic+'</td>'
                titlehtml='<tr>'+
                  '<th><input type="checkbox">全选</th>'+
                  '<th>车牌号码</th>'+
                  '<th>通过时间</th>'+
                  '<th>车辆类型</th>'+
                  '<th>品牌型号</th>'+
                  '<th>车身颜色</th>'+
                  '<th>车辆图片</th>'+
                '</tr>'
                allhtml += '<tr>'+checked+plate+pass_time+v_type+v_model+v_color+v_pic+'</tr>'
            });
            $("#jsontable6-2").html(titlehtml+allhtml);
        }
    });

    //支队设备
    // $.ajax({
    //         url: 'demo-2.json',
    //         type: 'get',
    //         dataType: 'json',
    //         error: function(error) { alert(error)},
    //         success: function(data) {
    //             $("#branch_1").html("");
    //
    //             var lihtml=""
    //                 allhtml = "";
    //
    //             data.map(function(item) {
    //                 //动态拼接
    //
    //                 lihtml =`<li><a>${item.team}</a></li>`
    //
    //                 allhtml += `${lihtml}`
    //             });
    //             $("#branch_1").html(allhtml);
    //         }
    //     });

//所有设备

$.ajax({
        url: '/menu_2.json',
        type: 'get',
        dataType: 'json',
        error: function(error){ alert("testtree");},
        success: function(data) {
            alert("加载数据");
            $("#branch_5").html("");
            var html_1="",
                html_2="",
                html_3="",
                html_4="",
                html_5=""
                all=""
                //动态拼接
                for(var i=0;i<data.length;i++){
                for(var j =0;j<data[i].region.length;j++){
                html_2+='<li><a>'+data[i].region[j]+'</a></li>'
              }
                html_1='<li><a>'+data[i].city+'</a></li>'+
                        '<ul>'+html_2+'</ul>'
                    html_2=""
                 all+= html_1
            }
             $("#branch_5").html(all);

        }
    });



    // $.ajax({
    //         url: 'testtree.json',
    //         type: 'get',
    //         dataType: 'json',
    //         error:function (err) {
    //             alert("no file")
    //         },
    //         success: function (data) {
    //             $("#branch_5").html("");
    //             var html_1="",
    //                 html_2="",
    //                 html_3="",
    //                 html_4="",
    //                 html_5=""
    //             all=""
    //             console.log(data.length);
    //             //动态拼接
    //             for(var i=0;i<data.length;i++){
    //                 for(var j =0;j<data[i].region.length;j++){
    //                     html_2+=`<li><a>${data[i].region[j]}</a></li>`
    //                 }
    //                 html_1=`<li><a>${data[i].city}</a></li>
    //                     <ul>${html_2}</ul>`
    //                 html_2=""
    //                 all+= `${html_1}`
    //             }
    //             $("#branch_5").html(all);
    //         }
    //
    //     });
