$(function(){
  
  var $body = $('body');
  if($body.hasClass('sub')){
    $('header#header').load('../header.html');
    $('footer#footer').load('../footer.html');
  } else {
    $('header#header').load('./header.html');
    $('footer#footer').load('./footer.html');
  }
  
});

