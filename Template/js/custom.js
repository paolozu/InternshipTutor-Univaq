$('#calendar').datepicker({
		});

!function ($) {
    $(document).on("click","ul.nav li.parent > a ", function(){          
        $(this).find('i').toggleClass("fa-minus");      
    }); 
    $(".sidebar span.icon").find('em:first').addClass("fa-plus");
}

$(document).on('click', '.panel-heading span.clickable', function(e){
    var $this = $(this);
	if(!$this.hasClass('panel-collapsed')) {
		$this.parents('.panel').find('.panel-body').slideUp();
		$this.addClass('panel-collapsed');
		$this.find('i').removeClass('fa-toggle-up').addClass('fa-toggle-down');
	} else {
		$this.parents('.panel').find('.panel-body').slideDown();
		$this.removeClass('panel-collapsed');
		$this.find('i').removeClass('fa-toggle-down').addClass('fa-toggle-up');
	}
})

// Theme Switcher
$(document).on('click', '.theme-btn', function(e){
	var name = $(e.target).attr('data-theme');
	window.localStorage.setItem('lumino-theme', name);
	setTheme(name);
})