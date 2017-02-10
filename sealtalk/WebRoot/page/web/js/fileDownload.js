/**
 * Created by zhu_jq on 2017/2/7.
 */
$(function(){
    $('.mr-chatview').delegate('#downLoadFile','click',function(){
        if(window.Electron){
            var url = $(this).prev().attr('src');
            var localPath = window.Electron.chkFileExists(url);
            console.log(localPath);
            //function fake_click(obj) {
            //    var ev = document.createEvent("MouseEvents");
            //    ev.initMouseEvent(
            //        "click", true, false, window, 0, 0, 0, 0, 0
            //        , false, false, false, false, 0, null
            //    );
            //    obj.dispatchEvent(ev);
            //}
            //function export_raw(name, data) {
            //    var urlObject = window.URL || window.webkitURL || window;
            //
            //    var export_blob = new Blob([data]);
            //
            //    var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
            //    save_link.href = urlObject.createObjectURL(export_blob);
            //    save_link.download = name;
            //    fake_click(save_link);
            //}
            //var sUrl='http://ocsys6mwy.bkt.clouddn.com/d7d9e74054a3139f89.jpg';
            //var oPop = window.open(sUrl,"","width=1, height=1, top=5000, left=5000");
            //for(; oPop.document.readyState != "complete"; )
            //{
            //    if (oPop.document.readyState == "complete")break;
            //}
            //oPop.document.execCommand("SaveAs");
            //oPop.close();
        }
    })
})