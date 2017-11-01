$(function(){
  $('#tabs').tabs();
  $('#tab5').tabs();
  $('#tabs>ul li').click(function(){
     $("#tabs ul li").eq($(this).index()).addClass("clicked").siblings().removeClass("clicked");
  })
  $("#menu5 li").click(function(){
     $("#menu5 li").eq($(this).index()).addClass("mlicurrent").siblings().removeClass("mlicurrent");
  });
  $("#video-4 .vd-4").click(function(){
     $("#video-4 .vd-4").eq($(this).index()).addClass("vd-checked").siblings().removeClass("vd-checked");
  });
  $("#allfaci").SimpleTree({
		click:function(s){
			console.log($(s).attr("haschild"));
      var a = $(s).attr("haschild");
      console.log(a);
      // if(a){
      //   console.log("end");
      // }
		}
	});
  $("#facility-list").SimpleTree({
		click:function(s){
			console.log($(s).attr("haschild"));
      var a = $(s).attr("haschild");
      console.log(a);
      // if(a){
      //   console.log("end");
      // }
		}
	});

  $(".st_tree2").SimpleTree({
		click:function(s){
			console.log($(s).attr("haschild"));
      var a = $(s).attr("haschild");
      console.log(a);
      // if(a){
      //   console.log("end");
      // }
		}
	});
  // $("#one-screen").click(function(){
  //   $("#content4-1").css("display","none");
  //   $("#content4-2").css("display","block");
  // });
  // $("#four-screen2").click(function () {
  //     $("#content4-2").css("display","none");
  //     $("#content4-1").css("display","block");
  // });
    jQuery.showmenu = function(showbtnid,showboxid) {
        var showmenubtn = $(showbtnid);
        var showmenubox = $(showboxid);
        showmenubtn.click(function(e){
            var thish = $(this).width();
            var offset = $(this).offset();
            var tipx = offset.left-thish-1-10;
            var tipy = offset.top+-1;
            //alert(tipx);
            showmenubox.show().css("left",tipx).css("top",tipy);
            // t= setTimeout(function(){showmenubox.hide();},3000);
        });
        // showmenubox.mouseenter(function(){
        //     clearTimeout(t);
        // });
        showmenubox.mouseleave(function(){
            $(this).hide();
        });
    };
    $.showmenu("#showmenu","#menubox");


    $("#v_now").click(function () {
        $("#v_iframe").attr("src","/hebei/now")

    });
    $("#v_history").click(function () {
        $("#v_iframe").attr("src","/hebei/history")

    });
    $("#v_xunluo").click(function () {
        $("#v_iframe").attr("src","/hebei/xl")
    });
    $("#tabs>ul>li:nth-child(1)").click(function () {
        $("#map_1").attr("src","/map")
    });

    $("#tabs>ul>li:nth-child(2)").click(function () {
        $("#map_2").attr("src","/weather")
    });

    $("#tabs>ul>li:nth-child(3)").click(function () {
        $("#map_3").attr("src","/map3")
    });

    $("#target7>ul>li:nth-child(1)").click(function () {
        $("#driver_info").attr("src","/car")
    });

    $("#target7>ul>li:nth-child(6)").click(function () {
        $("#driver_info").attr("src","/com")
    });

    $("#target7>ul>li:nth-child(5)").click(function () {
        $("#driver_info").attr("src","/phone")
    });

    $("#target7>ul>li:nth-child(4)").click(function () {
        $("#driver_info").attr("src","/belt")
    });

    $("#target7>ul>li:nth-child(3)").click(function () {
        $("#driver_info").attr("src","/sun")
    });

    $("#target7>ul>li:nth-child(2)").click(function () {
        $("#driver_info").attr("src","/car")
    });




});


