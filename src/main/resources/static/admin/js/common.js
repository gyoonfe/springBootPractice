var dataTbl;

jQuery.fn.serializeObject = function() { 
	var obj = null; 
  	try { 
		if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) { 
          var arr = this.serializeArray(); 
          if(arr){ obj = {}; 
          jQuery.each(arr, function() { 
              obj[this.name] = this.value; }); 
          } 
      	} 
  	}catch(e) { 
    	alert(e.message); 
  	}finally {} 
  	return obj; 
}



$(function() {

	$('#dataTable').DataTable({
		lengthChange: false
	});


/*
	$('.datatable tbody').on('click', 'tr td:not(:first-child)', function () {
		var data = dataTbl.row( this ).data();
		var url = window.location.origin + "/goodsInfo/detail?idx=" + data.goodsSq; 
		$(location).attr('href',url);
    } );
*/



	
});






$(function() {

/*
	$('.list li').each(function (index, item) { 
		// 인덱스는 말 그대로 인덱스 
		// item 은 해당 선택자인 객체를 나타냅니다. 
		$(item).addClass('li_0' + index); // item 과 this는 같아서 일반적으로 this를 많이 사용합니다. 
		// $(this).addClass('li_0' + index); 
	});

	$(".nav-link").on("click", function() {
	
		$(".nav-item").removeClass("active");
		$(this).parent().addClass("active");
		
	});
*/

});

