/**
 * Created by gao_yn on 2017/1/21.
 */
    //if (window.navigator.geolocation) {
    //    var options = {
    //        enableHighAccuracy: true
    //    };
    //    window.navigator.geolocation.getCurrentPosition(handleSuccess, handleError, options);
    //} else {
    //    alert("浏览器不支持html5来获取地理位置信息");
    //}
    //
    //function handleSuccess(position){
    //    // 获取到当前位置经纬度  本例中是chrome浏览器取到的是google地图中的经纬度
    //    var lng = position.coords.longitude;
    //    var lat = position.coords.latitude;
    //    console.log(lng,lat);
    //    // 调用百度地图api显示
    //    var map = new AMap.Map('map', {
    //        center: [116.480983, 39.989628],
    //        zoom: 10
    //    });
    //    //var map = new BMap.Map("map");
    //    // var ggPoint = new BMap.Point(lng, lat);
    //    // 将google地图中的经纬度转化为百度地图的经纬度
    //    /* BMap.Convertor.translate(ggPoint, 2, function(point){
    //     var marker = new BMap.Marker(point);
    //     map.addOverlay(marker);
    //     map.centerAndZoom(point, 15);
    //     });*/
    //}
    //
    //function handleError(error){
    //
    //}

    setInterval(function (){
        var map, geolocation;
        //加载地图，调用浏览器定位服务
        map = new AMap.Map('map', {
            resizeEnable: true
        });
        map.plugin('AMap.Geolocation', function() {
            geolocation = new AMap.Geolocation({
                enableHighAccuracy: true,//是否使用高精度定位，默认:true
                timeout: 10000,          //超过10秒后停止定位，默认：无穷大
                buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                zoomToAccuracy: true,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                buttonPosition:'RB'
            });
            map.addControl(geolocation);
            geolocation.getCurrentPosition();
            AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
            AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
        });
        //解析定位结果
        function onComplete(data) {
            var str=['定位成功'];
            var latiude=data.position.getLng();//经度
            var longtiude=data.position.getLat();//纬度
            str.push('经度：' + data.position.getLng());
            str.push('纬度：' + data.position.getLat());
            if(data.accuracy){
                str.push('精度：' + data.accuracy + ' 米');
            }//如为IP精确定位结果则没有精度信息
            str.push('是否经过偏移：' + (data.isConverted ? '是' : '否'));
            var sData=window.localStorage.getItem("datas");
            var oData= JSON.parse(sData);
            var sId=oData.text.id;
            sendAjax('map!subLocation',{userid:sId,latitude:latiude,longtitude:longtiude},function(data){
                var aDatas=JSON.parse(data);

            });
            //document.getElementById('tip').innerHTML = str.join('<br>');
        }
        //解析定位错误信息
        function onError(data) {
            document.getElementById('tip').innerHTML = '定位失败';
        }
    },3000);