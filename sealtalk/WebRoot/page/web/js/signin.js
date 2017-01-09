/**
 * Created by zhu_jq on 2017/1/4.
 */
window.onload = function(){
    $('.prevfun').click(function(){
        console.log(111);
    })

}
function fBackToSignin(){
    window.location.href = 'signin.html';
}

function fToNext(dom){
    console.log(dom);
    var sStep = $(dom).closest('.form-inline').attr('step');
    var aStep = sStep.split('-');
    var i = aStep[1]?aStep[1]:undefined;
    if(i){
        i++;
        i>=4?fBackToSignin():fToNextStep(i);
    }
}
function fToPrev(dom){
    console.log(dom);
    var sStep = $(dom).closest('.form-inline').attr('step');
    var aStep = sStep.split('-');
    var i = aStep[1]?aStep[1]:undefined;
    if(i){
        i--;
        i<=0?fBackToSignin():fToNextStep(i);
    }
}
function fToNextStep(i){
    $('.resetStep').addClass('chatHide');
    $('[step=resetStep-'+i+']').closest('.resetStep').removeClass('chatHide');
}
