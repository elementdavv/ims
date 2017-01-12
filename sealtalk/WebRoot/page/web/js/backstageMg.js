/**
 * Created by gao_yn on 2017/1/9.
 */
$(document).ready(function(){
   $('#perInfo').on('click','li',function(){
        $('#perInfo li').removeClass('active');
       $(this).addClass('active');
       $('#infoDetailsBox>div').removeClass('active');
       $('#infoDetailsBox>div').eq($(this).index()).addClass('active');
   });
    $('#chatBox').on('click','#mr-record',function(){
        if($('#perContainer').hasClass('mesContainer-translateL')){
            $('#perContainer').removeClass('mesContainer-translateL');
            $(this).addClass('active');
            $('#personalData').css('display','none');
        }else{
            $('#perContainer').addClass('mesContainer-translateL');
            $(this).removeClass('active');
            $('#personalData').css('display','block');
        }
    });
});