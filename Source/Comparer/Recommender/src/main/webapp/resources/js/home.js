$(document).ready(function(){
	var path = window.location.pathname;
    path = path.replace(/\/$/, "");
    path = decodeURIComponent(path);
    
    $(".nav a").each(function () {
    	$(this).closest('li').removeClass('active');
        var href = $(this).attr('href');
        if (path.substring(0, href.length) === href) {
            $(this).closest('li').addClass('active');
        }
    });
});