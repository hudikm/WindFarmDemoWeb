(function($){
  $(function(){

    $('.sidenav').sidenav();
    $('select').formSelect();
    var elems = document.querySelectorAll('.modal');
    var instances = M.Modal.init(elems, options);
    // $('.dropdown-trigger').dropdown();
  }); // end of document ready

})(jQuery); // end of jQuery name space
